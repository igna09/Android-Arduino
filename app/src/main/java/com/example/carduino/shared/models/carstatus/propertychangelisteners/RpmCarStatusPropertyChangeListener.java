package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.models.carstatus.values.Rpm;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.TripSingleton;
import com.example.carduino.shared.utilities.DialogUtilities;

public class RpmCarStatusPropertyChangeListener extends PropertyChangeListener<Rpm> {
    @Override
    public void onPropertyChange(String propertyName, Rpm oldValue, Rpm newValue) {
        if((oldValue.getValue() == null || oldValue.getValue() == 0) && newValue.getValue() > 0) { //Engine turned on
            Value value = CarStatusFactory.getCarStatusValue("ENGINE_STARTED", "true");
            if (value != null) {
                CarStatusSingleton.getInstance().getCarStatus().putValue(value);
            }
        }
        if(!TripSingleton.getInstance().getTrip().isStarted() && newValue.getValue() > 0) {
            if(TripSingleton.getInstance().tripBackupAvailable()) {
                if(ContextsSingleton.getInstance().getApplicationContext().isShowingApplication()) {
                    if(!DialogUtilities.isShowingDialog()) {
                        DialogUtilities.openContinueLastTrip();
                    }
                } else { // application not in foreground continuing last trip
                    try {
                        TripSingleton.getInstance().restoreTrip();
                        TripSingleton.getInstance().startTrip();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                TripSingleton.getInstance().startTrip();
            }
        }
    }
}
