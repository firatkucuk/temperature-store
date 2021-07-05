package com.qardio.temperaturestore.sensorquery.query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;

public interface DailyDataRepository extends MongoRepository<DailyData, String> {

    @NonNull
    Optional<DailyData> findByClientIdAndDate(@NonNull UUID clientId, @NonNull Long date);

    @NonNull
    List<ClientDailyData> findByClientIdAndDateBetween(
        @NonNull UUID clientId, @NonNull Long startDate, @NonNull Long endDate
    );
}
