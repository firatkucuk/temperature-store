package com.qardio.temperaturestore.sensorquery.query;

import com.qardio.temperaturestore.common.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
class QueryController {

    private final QueryService queryService;

    /**
     * Query REST API for reading aggregated data in day granularity
     * @param clientId UUID identifier specific for client
     * @param startDate Start date
     * @param endDate End data
     * @return List of daily aggregated results which includes average, min and max
     */
    @NonNull
    @GetMapping("/{clientId}/daily/{startDate}/{endDate}")
    List<ClientDailyData> getDailyReading(
        @PathVariable @NonNull final UUID clientId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable @NonNull final LocalDate startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable @NonNull final LocalDate endDate
    ) {

        return this.queryService.getDailyReading(clientId, startDate, endDate);
    }

    /**
     * Query REST API for reading aggregated data in hour granularity
     * @param clientId UUID identifier specific for client
     * @param startDateTime Start date time
     * @param endDateTime End data time
     * @return List of hourly aggregated results which includes average, min and max
     */
    @NonNull
    @GetMapping("/{clientId}/hourly/{startDateTime}/{endDateTime}")
    List<ClientHourlyData> getHourlyReading(
        @PathVariable final UUID clientId,
        @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) @PathVariable @NonNull final LocalDateTime startDateTime,
        @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) @PathVariable @NonNull final LocalDateTime endDateTime
    ) {

        return this.queryService.getHourlyReading(clientId, startDateTime, endDateTime);
    }
}
