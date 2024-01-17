package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.TripSingleton;
import com.example.carduino.shared.utilities.LoggerUtilities;

import java.util.Date;

public class SpeedCarStatusPropertyChangeListener extends PropertyChangeListener<KmhSpeed> {
    @Override
    public void onPropertyChange(String propertyName, KmhSpeed oldValue, KmhSpeed newValue) {
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.SPEED, newValue.getValue());
        }
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            if (TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE) == null) { //first insertion
                TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.DISTANCE, Float.valueOf(0));
            }
            if (TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).getLastReading() == null) {
                TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).setLastReading(new Date());
            }
            if(oldValue.getValue() > 0) { // excluding first speed after start to avoid wrong values when app doesn't remove singleton from memory at car shutdown
                Float avgSpeed = new Float((oldValue.getValue() + newValue.getValue()) / 2.0);
                Long nowTime = (new Date()).getTime();
                Float deltaT = ((Double) (Math.abs(nowTime - TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).getLastReading().getTime()) / 1000.0)).floatValue();
                Float distance = (((avgSpeed * deltaT) / 3600) * 1/*distCorrVal*/);
                LoggerUtilities.logMessage(
                        "SpeedCarStatusPropertyChangeListener",
                        "oldValue " + oldValue.getValue()
                                + ", newValue " + newValue.getValue()
                                + ", avgSpeed " + avgSpeed
                                + ", lastReading " + TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).getLastReading().getTime()
                                + ", nowTime " + nowTime
                                + ", deltaT " + deltaT
                                + ", distance((avgSpeed * deltaT) / 3600) " + distance
                        );
                TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.DISTANCE, distance);
            }
        }
    }
}
