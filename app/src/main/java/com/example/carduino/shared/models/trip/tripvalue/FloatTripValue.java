package com.example.carduino.shared.models.trip.tripvalue;

import java.util.Date;

public class FloatTripValue extends TripValue<Float> {
    public FloatTripValue() {
        setAverage(Float.valueOf(0));
        setMax(Float.valueOf(0));
        setSum(Float.valueOf(0));
        setReadings(0);
    }

    @Override
    public void addValue(Float value) {
        setLastReading(new Date());
        if(value > getMax()) {
            setMax(value);
        }
        setReadings(getReadings() + 1);
        setSum(getSum() + value);
        setAverage(getSum() / getReadings());
    }
}
