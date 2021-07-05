package com.qardio.temperaturestore.sensorquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class SensorQueryApplication {

    public static void main(@NonNull final String[] args) {
        SpringApplication.run(SensorQueryApplication.class, args);
    }
}
