package com.qardio.temperaturestore.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.NonNull;

@SpringBootApplication
public class GatewayApplication {

    public static void main(@NonNull final String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
