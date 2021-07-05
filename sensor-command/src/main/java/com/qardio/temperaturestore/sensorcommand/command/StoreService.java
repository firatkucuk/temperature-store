package com.qardio.temperaturestore.sensorcommand.command;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import com.qardio.temperaturestore.sensorcommand.event.EventProducer;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class StoreService {

    @NonNull
    private final EventProducer eventProducer;

    /**
     * Converts the incoming command object to events and publishes to Kafka
     * @param clientId UUID identifier specific for client
     * @param command command Pojo which represents the bulk sensor data write operations
     */
    void storeReading(@NonNull final UUID clientId, @NonNull final AddBulkSensorReadingCommand command) {

        for (final SensorReading reading : command.getReadings()) {

            final ReadingStoredEvent event = ReadingStoredEvent.builder()
                .clientId(clientId)
                .temperature(reading.getTemperature())
                .time(reading.getTime())
                .build();

            this.eventProducer.publishAndStore(event);
        }
    }
}
