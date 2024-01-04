package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.TripSingleton;

import java.util.Date;

public class SpeedCarStatusPropertyChangeListener extends PropertyChangeListener<KmhSpeed> {
    @Override
    public void onPropertyChange(String propertyName, KmhSpeed oldValue, KmhSpeed newValue) {
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.SPEED, newValue.getValue());
        }
        if(TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            Float avgSpeed = new Float((oldValue.getValue() + newValue.getValue()) / 2.0);
            if (TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE) == null) { //first insertion
                TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.DISTANCE, Float.valueOf(0));
            }
            if (TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).getLastReading() == null) {
                TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).setLastReading(new Date());
            }
            Float deltaT = ((Double) (Math.abs((new Date()).getTime() - TripSingleton.getInstance().getTrip().getTripValues().get(TripValueEnum.DISTANCE).getLastReading().getTime()) / 1000.0)).floatValue();
            Float distance = (((avgSpeed * deltaT) / 3600) * 1/*distCorrVal*/);
//            LoggerUtilities.logMessage("SpeedCarStatusPropertyChangeListener", "Distance " + distance);
            TripSingleton.getInstance().getTrip().addTripValue(TripValueEnum.DISTANCE, distance);
        }
    }
}
