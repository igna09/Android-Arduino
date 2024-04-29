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
            SharedDataSingleton.getInstance().getAvgluminanceReadings().add(newValue.getValue());
            SharedDataSingleton.getInstance().getMinMaxluminanceReadings().add(newValue.getValue());

            Integer lastReadingsAvg = SharedDataSingleton.getInstance().getAvgluminanceReadings().getList().stream().reduce(0, Integer::sum) / SharedDataSingleton.getInstance().getAvgluminanceReadings().getList().size();
            Integer lastReadingsMax = SharedDataSingleton.getInstance().getMinMaxluminanceReadings().getList().stream().max(Integer::compareTo).get();
            Integer lastReadingsMin = SharedDataSingleton.getInstance().getMinMaxluminanceReadings().getList().stream().min(Integer::compareTo).get();

            if(SharedDataSingleton.getInstance().getMaxDisplayBrightness() == null) {
                SharedDataSingleton.getInstance().setMaxDisplayBrightness(getMaxBrightness(ContextsSingleton.getInstance().getApplicationContext(), 255));
            }


            if((Boolean) SettingsSingleton.getInstance().getSettings().get(SettingsEnum.AUTO_BRIGHTNESS.name()).getValue() && lastReadingsMax > lastReadingsMin) { //no luminance readings yet
                Integer mappedValue = map(lastReadingsAvg, lastReadingsMin, lastReadingsMax, 0, SharedDataSingleton.getInstance().getMaxDisplayBrightness());
                Settings.System.putInt(ContextsSingleton.getInstance().getApplicationContext().getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, mappedValue);

                LoggerUtilities.logMessage("InternalLuminanceCarStatusPropertyChangeListener", "avg: " + lastReadingsAvg + ", min: " + lastReadingsMin + ", max: " + lastReadingsMax + ", mapped: " + mappedValue);
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
