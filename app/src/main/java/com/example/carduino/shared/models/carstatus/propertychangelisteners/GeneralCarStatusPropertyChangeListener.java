package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import android.util.Log;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;

import java.beans.PropertyChangeEvent;

public class GeneralCarStatusPropertyChangeListener extends PropertyChangeListener<Value> {
    @Override
    public void onPropertyChange(String propertyName, Value oldValue, Value newValue) {
        Log.d("CarStatus", "Changed " + CarStatusEnum.valueOf(propertyName) + ", old value: " + getSafeValue(oldValue) + ", new value: " + getSafeValue(newValue));
        Log.d("CarStatus", CarStatusSingleton.getInstance().getCarStatus().toString());

        switch (CarStatusEnum.valueOf(propertyName)) {
            case SPEED:
                //buzzer if over speed limit
                break;
        }
    }

    private String getSafeValue(Value value) {
        return value.getValue() != null ? value.getValue().toString() : "";
    }
}
