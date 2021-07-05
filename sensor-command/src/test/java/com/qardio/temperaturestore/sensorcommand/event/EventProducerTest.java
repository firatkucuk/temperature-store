package com.qardio.temperaturestore.sensorcommand.event;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventProducerTest {

    private static final String TOPIC = "test_readings";

    private EventProducer eventProducer;

    @Mock
    private KafkaTemplate<String, ReadingStoredEvent> kafkaTemplate;

    @BeforeEach
    void initTest() {

        this.eventProducer = new EventProducer(TOPIC, this.kafkaTemplate);
    }

    @Test
    @DisplayName("When controller sends a reading data, then storeReading method sends all readings to kafka")
    void testIncomingMultipleReadings() {

        final ReadingStoredEvent event = ReadingStoredEvent.builder()
            .clientId(UUID.randomUUID())
            .temperature(10d)
            .time(LocalDateTime.now())
            .build();

        this.eventProducer.publishAndStore(event);

        verify(this.kafkaTemplate, only()).send(any(), any());
    }
}
