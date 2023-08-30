package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.singletons.ContextsSingleton;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LuxLuminanceCarStatusPropertyChangeListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Application applicationContext = ContextsSingleton.getInstance().getApplicationContext();
        if(!Settings.System.canWrite(applicationContext.getApplicationContext())) {
            Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(applicationContext.getApplicationContext(), settingsIntent, null);
        } else {
            Settings.System.putInt(applicationContext.getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    map(((LuxLuminance) evt.getNewValue()).getValue(), 0, 5000, 0, 100));
        }
    }

    public static Integer map(Integer x, Integer in_min, Integer in_max, Integer out_min, Integer out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
