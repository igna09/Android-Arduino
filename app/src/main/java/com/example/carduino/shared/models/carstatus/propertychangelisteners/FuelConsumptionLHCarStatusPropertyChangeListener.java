package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import android.provider.Settings;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.models.carstatus.values.FuelConsumptionLH;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class FuelConsumptionLHCarStatusPropertyChangeListener extends PropertyChangeListener<FuelConsumptionLH> {
    @Override
    public void onPropertyChange(String propertyName, FuelConsumptionLH oldValue, FuelConsumptionLH newValue) {
        Float fuelConsumptionLH = newValue.getValue();
        if(fuelConsumptionLH != 0) {
            Integer speedKmH = (Integer) CarStatusSingleton.getInstance().getCarStatus().getCarStatusValues().get(CarStatusEnum.SPEED.name()).getValue();
            CarStatusSingleton.getInstance().getCarStatus().putValue(CarStatusFactory.getCarStatusValue(CarStatusEnum.FUEL_CONSUMPTION_KM_L.name(), Float.valueOf(speedKmH / fuelConsumptionLH).toString()));
        } else {
            CarStatusSingleton.getInstance().getCarStatus().putValue(CarStatusFactory.getCarStatusValue(CarStatusEnum.FUEL_CONSUMPTION_KM_L.name(), "0"));
        }
    }
}
