package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.values.FuelConsumptionKmL;
import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.TripSingleton;

public class FuelConsumptionCarStatusPropertyChangeListener extends PropertyChangeListener<FuelConsumptionKmL> {
    @Override
    public void onPropertyChange(String propertyName, FuelConsumptionKmL oldValue, FuelConsumptionKmL newValue) {
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0 && newValue.getValue() < 50) { // avoiding abnormal values
            TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.FUEL_CONSUMPTION, newValue.getValue());
        }
    }
}
