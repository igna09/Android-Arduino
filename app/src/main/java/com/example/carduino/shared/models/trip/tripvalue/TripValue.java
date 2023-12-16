package com.example.carduino.shared.models.trip.tripvalue;

import java.util.Date;

public abstract class TripValue<T> {
    private TripValueEnum tripValueEnum;
    private T average;
    private T max;
    private T sum;
    private Integer readings;
    private Date lastReading;

    public abstract void addValue(T value);

    public T getAverage() {
        return average;
    }

    public void setAverage(T average) {
        this.average = average;
    }

    public T getMax() {
        return max;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public Integer getReadings() {
        return readings;
    }

    public void setReadings(Integer readings) {
        this.readings = readings;
    }

    public Date getLastReading() {
        return lastReading;
    }

    public void setLastReading(Date lastReading) {
        this.lastReading = lastReading;
    }

    public T getSum() {
        return sum;
    }

    public void setSum(T sum) {
        this.sum = sum;
    }

    public TripValueEnum getTripValueEnum() {
        return tripValueEnum;
    }

    public void setTripValueEnum(TripValueEnum tripValueEnum) {
        this.tripValueEnum = tripValueEnum;
    }
}
