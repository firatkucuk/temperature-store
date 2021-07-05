package com.qardio.temperaturestore.sensorcommand.command;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
class StoreController {

    @NonNull
    private final StoreService storeService;

    /**
     * Stores sensors data incoming from client
     * @param clientId UUID identifier specific for client
     * @param command command Pojo which represents the bulk sensor data write operations
     */
    @PostMapping("/{clientId}")
    void storeSensorReading(
        @PathVariable @NonNull final UUID clientId,
        @RequestBody @Validated @NonNull final AddBulkSensorReadingCommand command
    ) {

        this.storeService.storeReading(clientId, command);
    }
}
