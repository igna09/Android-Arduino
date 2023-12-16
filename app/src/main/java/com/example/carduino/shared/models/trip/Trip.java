package com.example.carduino.shared.models.trip;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.trip.tripvalue.FloatTripValue;
import com.example.carduino.shared.models.trip.tripvalue.IntegerTripValue;
import com.example.carduino.shared.models.trip.tripvalue.TripValue;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Trip {
    private Boolean started;
    private Date begin;
    private Map<CarStatusEnum, TripValue> tripValues;

    public Trip() {
        started = false;
        begin = null;
        tripValues = new HashMap<>();
    }

    public void startTrip() {
        started = true;
        begin = new Date();
    }

    public void stopTrip() {
        started = false;
    }

    public Boolean isStarted() {
        return started;
    }

    public void addTripValue(CarStatusEnum carStatusEnum, Object value) {
        if(!tripValues.containsKey(carStatusEnum)) {
            try {
                TripValue tripValue = (TripValue) carStatusEnum.getTripType().newInstance();
                tripValue.setTripValueEnum(carStatusEnum);
                tripValues.put(carStatusEnum, tripValue);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        tripValues.get(carStatusEnum).addValue(value);
    }

    public Map<CarStatusEnum, TripValue> getTripValues() {
        return tripValues;
    }
}
