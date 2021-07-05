package com.qardio.temperaturestore.sensorcommand.command;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class KafkaTestConfiguration {

    @Bean
    KafkaTestConsumer kafkaTestConsumer() {
        return new KafkaTestConsumer();
    }
}
