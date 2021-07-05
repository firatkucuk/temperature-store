package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryService {

    @NonNull
    private final DailyDataRepository  dailyDataRepository;
    @NonNull
    private final HourlyDataRepository hourlyDataRepository;

    @NonNull
    private static double calculateRollingAverage(
        @NonNull final double average,
        @NonNull final long itemCount,
        @NonNull final double newItem
    ) {

        double newAverage = average;

        newAverage -= newAverage / itemCount;
        newAverage += newItem / itemCount;

        return newAverage;
    }

    @NonNull
    private static long toDailyEpoch(@NonNull final LocalDate date) {
        return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
    }

    @NonNull
    private static long toDailyEpoch(@NonNull final LocalDateTime time) {
        return toDailyEpoch(time.toLocalDate());
    }

    @NonNull
    private static long toHourlyEpoch(@NonNull final LocalDateTime time) {
        return time.withMinute(0).withSecond(0).toEpochSecond(ZoneOffset.UTC);
    }

    /**
     * Calculates average, min and max values for events then store into the DB
     * @param event ReadingStoredEvent coming from Kafka
     */
    public void storeAggregatedValues(@NonNull final ReadingStoredEvent event) {

        this.saveDailyData(event);
        this.saveHourlyData(event);
    }

    /**
     * Service method for getting day based data from database
     * @param clientId UUID identifier specific for client
     * @param startDate Start date
     * @param endDate End data
     * @return List of daily aggregated results which includes average, min and max
     */
    @NonNull
    List<ClientDailyData> getDailyReading(
        @NonNull final UUID clientId,
        @NonNull final LocalDate startDate,
        @NonNull final LocalDate endDate
    ) {
        return this.dailyDataRepository.findByClientIdAndDateBetween(
            clientId, toDailyEpoch(startDate), toDailyEpoch(endDate)
        );
    }

    /**
     * Service method for getting hour based data from database
     * @param clientId UUID identifier specific for client
     * @param startDateTime Start date time
     * @param endDateTime End data time
     * @return List of hourly aggregated results which includes average, min and max
     */
    @NonNull
    List<ClientHourlyData> getHourlyReading(
        @NonNull final UUID clientId,
        @NonNull final LocalDateTime startDateTime,
        @NonNull final LocalDateTime endDateTime
    ) {
        return this.hourlyDataRepository.findByClientIdAndTimeBetween(
            clientId, toHourlyEpoch(startDateTime), toHourlyEpoch(endDateTime)
        );
    }

    private void saveDailyData(@NonNull final ReadingStoredEvent event) {

        final long                dailyEpoch        = toDailyEpoch(event.getTime());
        final UUID                clientId          = event.getClientId();
        final Optional<DailyData> dailyDataOptional = this.dailyDataRepository.findByClientIdAndDate(clientId, dailyEpoch);

        final DailyData dailyData;

        if (dailyDataOptional.isEmpty()) {
            dailyData = new DailyData();
            dailyData.setClientId(clientId);
            dailyData.setDate(dailyEpoch);
            dailyData.setMax(event.getTemperature());
            dailyData.setMin(event.getTemperature());
            dailyData.setAverage(event.getTemperature());
            dailyData.setReadingCount(1);
        } else {
            dailyData = dailyDataOptional.get();
            dailyData.setReadingCount(dailyData.getReadingCount() + 1);
            dailyData.setMax(Double.max(event.getTemperature(), dailyData.getMax()));
            dailyData.setMin(Double.min(event.getTemperature(), dailyData.getMin()));
            dailyData.setAverage(calculateRollingAverage(dailyData.getAverage(), dailyData.getReadingCount(), event.getTemperature()));
        }

        this.dailyDataRepository.save(dailyData);
    }

    private void saveHourlyData(@NonNull final ReadingStoredEvent event) {

        final long                 hourlyEpoch        = toHourlyEpoch(event.getTime());
        final UUID                 clientId           = event.getClientId();
        final Optional<HourlyData> hourlyDataOptional = this.hourlyDataRepository.findByClientIdAndTime(clientId, hourlyEpoch);

        final HourlyData hourlyData;

        if (hourlyDataOptional.isEmpty()) {
            hourlyData = new HourlyData();
            hourlyData.setClientId(clientId);
            hourlyData.setTime(hourlyEpoch);
            hourlyData.setMax(event.getTemperature());
            hourlyData.setMin(event.getTemperature());
            hourlyData.setAverage(event.getTemperature());
            hourlyData.setReadingCount(1);
        } else {
            hourlyData = hourlyDataOptional.get();
            hourlyData.setReadingCount(hourlyData.getReadingCount() + 1);
            hourlyData.setMax(Double.max(event.getTemperature(), hourlyData.getMax()));
            hourlyData.setMin(Double.min(event.getTemperature(), hourlyData.getMin()));
            hourlyData.setAverage(calculateRollingAverage(hourlyData.getAverage(), hourlyData.getReadingCount(), event.getTemperature()));
        }

        this.hourlyDataRepository.save(hourlyData);
    }
}
