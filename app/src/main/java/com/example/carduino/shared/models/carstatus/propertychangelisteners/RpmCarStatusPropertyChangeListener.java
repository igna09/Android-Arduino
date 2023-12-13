package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import com.example.carduino.shared.models.carstatus.values.FuelConsumptionKmL;
import com.example.carduino.shared.models.carstatus.values.Rpm;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.TripSingleton;

public class RpmCarStatusPropertyChangeListener extends PropertyChangeListener<Rpm> {
    @Override
    public void onPropertyChange(String propertyName, Rpm oldValue, Rpm newValue) {
        if(!TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            TripSingleton.getInstance().getTrip().startTrip();
        }
    }
}
