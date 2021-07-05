package com.qardio.temperaturestore.sensorcommand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class SensorCommandApplication {

    public static void main(@NonNull final String[] args) {
        SpringApplication.run(SensorCommandApplication.class, args);
    }
}
