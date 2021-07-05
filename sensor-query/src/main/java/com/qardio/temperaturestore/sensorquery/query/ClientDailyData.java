package com.qardio.temperaturestore.sensorquery.query;

import lombok.Data;

@Data
class ClientDailyData {

    private long   date;
    private long   readingCount;
    private double average;
    private double min;
    private double max;
}
