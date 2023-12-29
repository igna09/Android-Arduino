package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import android.util.Log;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.values.CelsiusTemperature;
import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.TripSingleton;
import com.example.carduino.shared.utilities.LoggerUtilities;

import java.util.Date;

public class SpeedCarStatusPropertyChangeListener extends PropertyChangeListener<KmhSpeed> {
    @Override
    public void onPropertyChange(String propertyName, KmhSpeed oldValue, KmhSpeed newValue) {
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            TripSingleton.getInstance().getTrip().addTripValue(CarStatusEnum.SPEED, newValue.getValue());
        }
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            Float avgSpeed = new Float((oldValue.getValue() + newValue.getValue()) / 2.0);
            Float deltaT = ((Double)(Math.abs((new Date()).getTime() - TripSingleton.getInstance().getTrip().getDistance().lastRead.getTime()) / 1000.0)).floatValue();
            Float distance = (((avgSpeed * deltaT) / 3600) * 1/*distCorrVal*/);
            LoggerUtilities.logMessage("SpeedCarStatusPropertyChangeListener", "Distance " + distance);
            TripSingleton.getInstance().getTrip().addTripValue(CarStatusEnum.DISTANCE, (((avgSpeed * deltaT) / 3600) * 1/*distCorrVal*/));
            TripSingleton.getInstance().getTrip().getDistance().lastRead = new Date();
        }
    }
}
