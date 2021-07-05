package com.qardio.temperaturestore.sensorcommand.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qardio.temperaturestore.common.Constants;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class SensorReading {

    @NotNull
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime time;
    @NotNull
    @NonNull
    private Double        temperature;
}
