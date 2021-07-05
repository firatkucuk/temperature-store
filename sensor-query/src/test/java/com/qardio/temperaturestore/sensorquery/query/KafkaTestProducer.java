package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaTestProducer {

    private final String                                    topic;
    private final KafkaTemplate<String, ReadingStoredEvent> kafkaTemplate;

    public KafkaTestProducer(final String topic, final KafkaTemplate<String, ReadingStoredEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    void send(final ReadingStoredEvent event) {
        this.kafkaTemplate.send(this.topic, event);
    }
}
