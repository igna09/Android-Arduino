package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import android.content.Intent;

import com.example.carduino.shared.MyApplication;
import com.example.carduino.shared.models.carstatus.values.FuelConsumptionKmL;
import com.example.carduino.shared.models.carstatus.values.Rpm;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.TripSingleton;
import com.example.carduino.shared.utilities.DialogUtilities;

public class RpmCarStatusPropertyChangeListener extends PropertyChangeListener<Rpm> {
    @Override
    public void onPropertyChange(String propertyName, Rpm oldValue, Rpm newValue) {
        if(!TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) { //Engine turned on
//            if(ContextsSingleton.getInstance().getMainActivityContext() != null && ContextsSingleton.getInstance().getApplicationContext().isActivityVisible() && TripSingleton.getInstance().tripBackupAvailable() && !DialogUtilities.isShowingDialog()) {
            if(TripSingleton.getInstance().tripBackupAvailable() && !DialogUtilities.isShowingDialog()) {
                DialogUtilities.showContinueTripSession();
            } else {
                TripSingleton.getInstance().startTrip();
            }
        }
    }
}
