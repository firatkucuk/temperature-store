package com.qardio.temperaturestore.sensorcommand.command;

import java.util.concurrent.CountDownLatch;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

public class KafkaTestConsumer {

    private final CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(topics = "${temperature-store.events.topic-name}", groupId = "${temperature-store.events.group-id}")
    public void receive(final ConsumerRecord<?, ?> consumerRecord) {
        this.latch.countDown();
    }

    CountDownLatch getLatch() {
        return this.latch;
    }
}
