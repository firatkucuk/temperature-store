package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(KafkaTestConfiguration.class)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:29092", "port=29092"})
class EventOperationIntegrationTest {

    @Autowired
    private KafkaTestProducer producer;

    @Test
    @DisplayName("when reading data arrives then it publishes ReadingStoredEvent")
    void testAggregatedDataStore() {

        final UUID clientId = UUID.randomUUID();

        final ReadingStoredEvent event1 = ReadingStoredEvent.builder()
            .clientId(clientId)
            .time(LocalDateTime.of(2021, 6, 1, 10, 11))
            .temperature(10d)
            .build();

        final ReadingStoredEvent event2 = ReadingStoredEvent.builder()
            .clientId(clientId)
            .time(LocalDateTime.of(2021, 6, 1, 10, 12))
            .temperature(20d)
            .build();

        final ReadingStoredEvent event3 = ReadingStoredEvent.builder()
            .clientId(clientId)
            .time(LocalDateTime.of(2021, 6, 1, 11, 5))
            .temperature(30d)
            .build();

        this.producer.send(event1);
        this.producer.send(event2);
        this.producer.send(event3);
    }
}
