package com.qardio.temperaturestore.sensorquery.query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

public interface HourlyDataRepository extends MongoRepository<HourlyData, String> {

    @NonNull
    Optional<HourlyData> findByClientIdAndTime(@NonNull UUID clientId, @NonNull Long hourlyEpoch);

    @NonNull
    List<ClientHourlyData> findByClientIdAndTimeBetween(
        @NonNull UUID clientId, @NonNull Long startDateTime, @NonNull Long endDateTime
    );
}
