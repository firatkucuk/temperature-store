package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class KafkaTestConfiguration {

    @Bean
    KafkaTestProducer kafkaTestProducer(
        @Value("${temperature-store.events.topic-name}") final String topic,
        final KafkaTemplate<String, ReadingStoredEvent> kafkaTemplate
    ) {
        return new KafkaTestProducer(topic, kafkaTemplate);
    }
}
