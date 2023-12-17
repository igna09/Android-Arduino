package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.PermissionUtilities;

import java.beans.PropertyChangeEvent;

public class InternalLuminanceCarStatusPropertyChangeListener extends PropertyChangeListener<LuxLuminance> {
    @Override
    public void onPropertyChange(String propertyName, LuxLuminance oldValue, LuxLuminance newValue) {
        Settings.System.putInt(ContextsSingleton.getInstance().getApplicationContext().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,
                map(newValue.getValue(), 0, 5000, 0, 100));
    }

    public static Integer map(Integer x, Integer in_min, Integer in_max, Integer out_min, Integer out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
