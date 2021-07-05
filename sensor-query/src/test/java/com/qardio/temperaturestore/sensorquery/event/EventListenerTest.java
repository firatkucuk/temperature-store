package com.qardio.temperaturestore.sensorquery.event;

import com.qardio.temperaturestore.common.ReadingStoredEvent;
import com.qardio.temperaturestore.sensorquery.query.QueryService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventListenerTest {

    private static final String TOPIC = "test_readings";

    private EventListener eventListener;

    @Mock
    private QueryService queryService;

    @BeforeEach
    void initTest() {

        this.eventListener = new EventListener(this.queryService);
    }

    @Test
    @DisplayName("When listener fetches a ReadingStoredEvent event, then transfers it to service layer")
    void testIncomingEvents() {

        final ReadingStoredEvent event = ReadingStoredEvent.builder()
            .clientId(UUID.randomUUID())
            .temperature(10d)
            .time(LocalDateTime.now())
            .build();

        this.eventListener.consume(event);

        verify(this.queryService, only()).storeAggregatedValues(Mockito.eq(event));
    }
}
