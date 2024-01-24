package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Intent;
import android.provider.Settings;

import com.example.carduino.settings.SettingsEnum;
import com.example.carduino.settings.settingfactory.SettingsFactory;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.SettingsSingleton;
import com.example.carduino.shared.utilities.PermissionUtilities;

import java.beans.PropertyChangeEvent;

public class InternalLuminanceCarStatusPropertyChangeListener extends PropertyChangeListener<LuxLuminance> {
    @Override
    public void onPropertyChange(String propertyName, LuxLuminance oldValue, LuxLuminance newValue) {
        if(newValue.getValue() != null) {
            Integer maxLuminance = null;
            Integer minLuminance = null;

            if(!SettingsSingleton.getInstance().getSettings().containsKey(SettingsEnum.MIN_LUMINANCE.name())) {
                SettingsSingleton.getInstance().addSetting(SettingsFactory.getSetting(SettingsEnum.MIN_LUMINANCE.name(), "1000"));
            }
            minLuminance = (Integer) SettingsSingleton.getInstance().getSettings().get(SettingsEnum.MIN_LUMINANCE.name()).getValue();
            if(!SettingsSingleton.getInstance().getSettings().containsKey(SettingsEnum.MAX_LUMINANCE.name())) {
                SettingsSingleton.getInstance().addSetting(SettingsFactory.getSetting(SettingsEnum.MAX_LUMINANCE.name(), "0"));
            }
            maxLuminance = (Integer) SettingsSingleton.getInstance().getSettings().get(SettingsEnum.MAX_LUMINANCE.name()).getValue();

            if(newValue.getValue() < minLuminance) {
                minLuminance = newValue.getValue();
                SettingsSingleton.getInstance().getSettings().get(SettingsEnum.MIN_LUMINANCE.name()).setValue(minLuminance);
            }
            if(newValue.getValue() > maxLuminance) {
                maxLuminance = newValue.getValue();
                SettingsSingleton.getInstance().getSettings().get(SettingsEnum.MAX_LUMINANCE.name()).setValue(maxLuminance);
            }

            if(maxLuminance > minLuminance) {
                Settings.System.putInt(ContextsSingleton.getInstance().getApplicationContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS,
                        map(newValue.getValue(), minLuminance, maxLuminance, 0, 100));
            }
        }
    }

    public static Integer map(Integer x, Integer in_min, Integer in_max, Integer out_min, Integer out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
