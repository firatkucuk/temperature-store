package com.qardio.temperaturestore.sensorquery.event;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import com.qardio.temperaturestore.sensorquery.query.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener {

    @NonNull
    private final QueryService queryService;

    /**
     * Regisered Kafka consumer for getting ReadingStoredEvent events
     * @param event ReadingStoredEvent coming from Kafka
     */
    @KafkaListener(topics = "${temperature-store.events.topic-name}", groupId = "${temperature-store.events.group-id}")
    public void consume(@NonNull final ReadingStoredEvent event) {
        this.queryService.storeAggregatedValues(event);
    }
}
