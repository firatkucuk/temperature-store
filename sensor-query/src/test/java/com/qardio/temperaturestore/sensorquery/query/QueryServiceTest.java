package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QueryServiceTest {

    private QueryService queryService;

    @Mock
    private DailyDataRepository dailyDataRepository;

    @Mock
    private HourlyDataRepository hourlyDataRepository;

    @BeforeEach
    void initTest() {

        this.queryService = new QueryService(this.dailyDataRepository, this.hourlyDataRepository);
    }

    @Test
    @DisplayName("When controller requests daily data, then getDailyReading method fetches data from mongodb")
    void testGetDailyReading() {

        final UUID      clientId  = UUID.randomUUID();
        final LocalDate startDate = LocalDate.of(2021, 6, 1);
        final LocalDate endDate   = LocalDate.of(2021, 6, 15);

        this.queryService.getDailyReading(clientId, startDate, endDate);

        verify(this.dailyDataRepository, only()).findByClientIdAndDateBetween(eq(clientId), any(), any());
    }

    @Test
    @DisplayName("When controller requests hourly data, then getHourlyReading method fetches data from mongodb")
    void testGetHourlyReading() {

        final UUID          clientId      = UUID.randomUUID();
        final LocalDateTime startDateTime = LocalDateTime.now().minusDays(10).withNano(0);
        final LocalDateTime endDateTime   = LocalDateTime.now().withNano(0);

        this.queryService.getHourlyReading(clientId, startDateTime, endDateTime);

        verify(this.hourlyDataRepository, only()).findByClientIdAndTimeBetween(eq(clientId), any(), any());
    }

    @Test
    @DisplayName("When listener catches ReadingStoredEvent event, then storeAggregatedValues calculates and stores aggregated values")
    void testStoreAggregatedValues() {

        final ReadingStoredEvent event = ReadingStoredEvent.builder()
            .clientId(UUID.randomUUID())
            .temperature(10d)
            .time(LocalDateTime.now())
            .build();

        this.queryService.storeAggregatedValues(event);
    }
}
