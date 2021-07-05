package com.qardio.temperaturestore.sensorcommand.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(KafkaTestConfiguration.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:29092", "port=29092" })
class StoreOperationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTestConsumer consumer;

    @Test
    @DisplayName("when reading data arrives then it publishes ReadingStoredEvent")
    void testEventPublishingForIncomingData() throws Exception {

        final AddBulkSensorReadingCommand command = AddBulkSensorReadingCommand.builder()
            .readings(List.of(SensorReading.builder().temperature(20d).time(LocalDateTime.now()).build()))
            .build();

        this.mvc.perform(
            post("/api/store/{clientId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(command))
        ).andExpect(status().isOk());

        this.consumer.getLatch().await(5, TimeUnit.SECONDS);
        Assertions.assertEquals(0, this.consumer.getLatch().getCount());
    }
}
