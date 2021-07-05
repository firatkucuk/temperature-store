package com.qardio.temperaturestore.sensorquery.query;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@CompoundIndex(name = "IX_HOURLY_QUERY", def = "{'clientId': 1, 'time': 1}")
class HourlyData {

    @Id
    private String id;
    @Indexed
    private UUID   clientId;
    private long   time;
    private long   readingCount;
    private double total;
    private double average;
    private double min;
    private double max;
    @Version
    private Long   version;
}
