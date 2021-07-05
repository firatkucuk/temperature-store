package com.qardio.temperaturestore.common;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadingStoredEvent {

    private UUID          clientId;
    private LocalDateTime time;
    private Double        temperature;
}
