package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import android.util.Log;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.values.CelsiusTemperature;
import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.TripSingleton;

public class SpeedCarStatusPropertyChangeListener extends PropertyChangeListener<KmhSpeed> {
    @Override
    public void onPropertyChange(String propertyName, KmhSpeed oldValue, KmhSpeed newValue) {
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            TripSingleton.getInstance().getTrip().addTripValue(CarStatusEnum.SPEED, newValue.getValue());
        }
    }
}
