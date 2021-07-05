package com.qardio.temperaturestore.sensorcommand.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreService storeService;

    @Test
    @DisplayName("When client sends an reading data with null readings, then server sends a bad request error")
    void testNonExistingReadings() throws Exception {

        this.mvc.perform(
            post("/api/store/{clientId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(new AddBulkSensorReadingCommand()))
        ).andExpect(status().isBadRequest());

        verify(this.storeService, never()).storeReading(any(), any());
    }

    @Test
    @DisplayName("When client sends invalid reading data, then server sends a bad request error")
    void testInvalidReadings() throws Exception {

        final AddBulkSensorReadingCommand command = AddBulkSensorReadingCommand.builder()
            .readings(List.of(new SensorReading()))
            .build();

        this.mvc.perform(
            post("/api/store/{clientId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(command))
        ).andExpect(status().isBadRequest());

        verify(this.storeService, never()).storeReading(any(), any());
    }

    @Test
    @DisplayName("When client sends valid reading data, then controller passed command to service layer")
    void testIncomingValidData() throws Exception {

        final AddBulkSensorReadingCommand command = AddBulkSensorReadingCommand.builder()
            .readings(List.of(SensorReading.builder().temperature(20d).time(LocalDateTime.now()).build()))
            .build();

        this.mvc.perform(
            post("/api/store/{clientId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsBytes(command))
        ).andExpect(status().isOk());

        verify(this.storeService, only()).storeReading(any(), any());
    }
}
