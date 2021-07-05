package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QueryController.class)
class QueryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QueryService queryService;

    @Test
    @DisplayName("When client request valid daily query data, then controller passed command to service layer")
    void testDailyDataQuery() throws Exception {

        final UUID      clientId  = UUID.randomUUID();
        final LocalDate startDate = LocalDate.of(2021, 6, 1);
        final LocalDate endDate   = LocalDate.of(2021, 6, 15);

        this.mvc.perform(
            get("/api/query/{clientId}/daily/{startDate}/{endDate}", clientId, startDate, endDate)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(this.queryService, only()).getDailyReading(eq(clientId), eq(startDate), eq(endDate));
    }

    @Test
    @DisplayName("When client request valid hourly query data, then controller passed command to service layer")
    void testHourlyDataQuery() throws Exception {

        final UUID          clientId      = UUID.randomUUID();
        final LocalDateTime startDateTime = LocalDateTime.now().minusDays(10).withNano(0);
        final LocalDateTime endDateTime   = LocalDateTime.now().withNano(0);

        this.mvc.perform(
            get(
                "/api/query/{clientId}/hourly/{startDateTime}/{endDateTime}",
                clientId.toString(),
                startDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)),
                endDateTime.format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT))
            )
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(this.queryService, only()).getHourlyReading(eq(clientId), eq(startDateTime), eq(endDateTime));
    }
}
