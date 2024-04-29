package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;

import com.example.carduino.settings.SettingsEnum;
import com.example.carduino.settings.settingfactory.SettingsFactory;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.SettingsSingleton;
import com.example.carduino.shared.singletons.SharedDataSingleton;
import com.example.carduino.shared.utilities.LoggerUtilities;

import java.lang.reflect.Field;

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

            SharedDataSingleton.getInstance().getLuminanceReadings().add(newValue.getValue());

            Integer sum = SharedDataSingleton.getInstance().getLuminanceReadings().getList().stream().reduce(0, Integer::sum);
            Integer avg = sum / SharedDataSingleton.getInstance().getLuminanceReadings().getList().size();

            if(SharedDataSingleton.getInstance().getMaxDisplayBrightness() == null) {
                SharedDataSingleton.getInstance().setMaxDisplayBrightness(getMaxBrightness(ContextsSingleton.getInstance().getApplicationContext(), 255));
            }


            if((Boolean) SettingsSingleton.getInstance().getSettings().get(SettingsEnum.AUTO_BRIGHTNESS.name()).getValue() && maxLuminance > minLuminance) { //no luminance readings yet
                Integer mappedValue = map(avg, minLuminance, maxLuminance, 0, SharedDataSingleton.getInstance().getMaxDisplayBrightness());
                Settings.System.putInt(ContextsSingleton.getInstance().getApplicationContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, mappedValue);

                LoggerUtilities.logMessage("InternalLuminanceCarStatusPropertyChangeListener", "avg: " + avg + ", min: " + minLuminance + ", max: " + maxLuminance + ", mapped: " + mappedValue);
            }
        }
    }

    public Integer getMaxBrightness(Context context, Integer defaultValue){

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(powerManager != null) {
            Field[] fields = powerManager.getClass().getDeclaredFields();
            for (Field field: fields) {

                //https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/core/java/android/os/PowerManager.java

                if(field.getName().equals("BRIGHTNESS_ON")) {
                    field.setAccessible(true);
                    try {
                        LoggerUtilities.logMessage("InternalLuminanceCarStatusPropertyChangeListener", "reading max brightness value " + (Integer) field.get(powerManager));
                        return (Integer) field.get(powerManager);
                    } catch (IllegalAccessException e) {
                        LoggerUtilities.logMessage("InternalLuminanceCarStatusPropertyChangeListener", "exception, returning default value");
                        LoggerUtilities.logException(e);
                        return defaultValue;
                    }
                }
            }
        }
        LoggerUtilities.logMessage("InternalLuminanceCarStatusPropertyChangeListener", "returning default value");
        return defaultValue;
    }

    public static Integer map(Integer x, Integer in_min, Integer in_max, Integer out_min, Integer out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
