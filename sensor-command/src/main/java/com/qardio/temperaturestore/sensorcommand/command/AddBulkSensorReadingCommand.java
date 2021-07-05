package com.qardio.temperaturestore.sensorcommand.command;

import java.util.List;
import javax.validation.Valid;
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
class AddBulkSensorReadingCommand {

    @Valid
    @NotNull
    @NonNull
    private List<SensorReading> readings;
}
