package com.qardio.temperaturestore.sensorquery.query;

import lombok.Data;

@Data
class ClientHourlyData {

    private long   time;
    private long   readingCount;
    private double total;
    private double average;
    private double min;
    private double max;
}
