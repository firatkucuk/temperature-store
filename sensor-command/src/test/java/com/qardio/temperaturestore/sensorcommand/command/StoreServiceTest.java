package com.qardio.temperaturestore.sensorcommand.command;

import com.qardio.temperaturestore.sensorcommand.event.EventProducer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    private StoreService storeService;

    @Mock
    private EventProducer eventProducer;

    @BeforeEach
    void initTest() {

        this.storeService = new StoreService(this.eventProducer);
    }

    @Test
    @DisplayName("When controller sends a reading data, then storeReading method sends all readings to kafka")
    void testIncomingMultipleReadings() {

        final AddBulkSensorReadingCommand command = AddBulkSensorReadingCommand.builder()
            .readings(List.of(
                SensorReading.builder().temperature(20d).time(LocalDateTime.now()).build(),
                SensorReading.builder().temperature(10d).time(LocalDateTime.now().minusHours(1)).build(),
                SensorReading.builder().temperature(12d).time(LocalDateTime.now().minusHours(2)).build()
                ))
            .build();

        this.storeService.storeReading(UUID.randomUUID(), command);

        verify(this.eventProducer, times(3)).publishAndStore(any());
    }
}
