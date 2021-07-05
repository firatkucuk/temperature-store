package com.qardio.temperaturestore.sensorcommand.event;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {

    private final String                                    topic;
    private final KafkaTemplate<String, ReadingStoredEvent> kafkaTemplate;

    public EventProducer(
        @Value("${temperature-store.events.topic-name}") @NonNull final String topic,
        @NonNull final KafkaTemplate<String, ReadingStoredEvent> kafkaTemplate
    ) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publishes the event to Kafka Event Store
     * @param event ReadingStoredEvent which includes clientId and sensor data
     */
    public void publishAndStore(@NonNull final ReadingStoredEvent event) {
        this.kafkaTemplate.send(this.topic, event);
    }
}
