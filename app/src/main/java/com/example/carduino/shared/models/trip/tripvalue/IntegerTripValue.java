package com.example.carduino.shared.models.trip.tripvalue;

import java.util.Date;

public class IntegerTripValue extends TripValue<Integer> {
    public IntegerTripValue() {
        setAverage(0);
        setMax(0);
        setSum(0);
        setReadings(0);
    }

    @Override
    public void addValue(Integer value) {
        setLastReading(new Date());
        if(value > getMax()) {
            setMax(value);
        }
        setReadings(getReadings() + 1);
        setSum(getSum() + value);
        setAverage(getSum() / getReadings());
    }
}
