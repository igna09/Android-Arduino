package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.example.carduino.shared.models.carstatus.values.CelsiusTemperature;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.ContextsSingleton;

import java.beans.PropertyChangeEvent;

public class EngineWaterCoolingTemperatureCarStatusPropertyChangeListener extends PropertyChangeListener<CelsiusTemperature> {
    @Override
    public void onPropertyChange(String propertyName, CelsiusTemperature oldCelsiusTemperature, CelsiusTemperature newCelsiusTemperature) {
        Log.d(this.getClass().getSimpleName(), "old " + oldCelsiusTemperature.getValue() + " new " + newCelsiusTemperature.getValue());
    }
}
