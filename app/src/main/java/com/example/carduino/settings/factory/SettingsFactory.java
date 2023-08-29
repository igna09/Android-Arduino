package com.example.carduino.settings.factory;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.R;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class SettingsFactory {
    public static Setting getSetting(String id, String settingType, Object value, String unit) {
        SettingsViewModel settingsViewModel = new ViewModelProvider(ContextsSingleton.getInstance().getMainActivityContext()).get(SettingsViewModel.class);

        switch (SettingsEnum.valueOf(settingType)) {
            case BOOLEAN:
            {
                Boolean booleanSetting;
                if (value instanceof String) {
                    booleanSetting = ((String) value).equals("true");
                } else {
                    booleanSetting = (Boolean) value;
                }

                BooleanSetting setting = new BooleanSetting(id, booleanSetting, unit);
                setting.setOnCheckedChangeListener((compoundButton, b) -> {
                    setting.setValue(b);
                    settingsViewModel.addSetting(setting);

                    Application appContext = ContextsSingleton.getInstance().getApplicationContext();
                    Intent intent = new Intent();
                    intent.setAction(appContext.getString(R.string.arduino_send_message));
                    intent.putExtra("message", ArduinoMessageUtilities.stringifyArduinoSetting(setting));
                    appContext.sendBroadcast(intent);
                });

                return setting;
            }
            case INTEGER: {
                Integer integerValue;
                if (value instanceof String) {
                    integerValue = Integer.valueOf((String) value);
                } else {
                    integerValue = (Integer) value;
                }
                IntegerSetting setting = new IntegerSetting(id, integerValue, unit);
                setting.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        setting.setValue(Integer.parseInt(((EditText) v).getText().toString()));
                        settingsViewModel.addSetting(setting);

                        Application appContext = ContextsSingleton.getInstance().getApplicationContext();
                        Intent intent = new Intent();
                        intent.setAction(appContext.getString(R.string.arduino_send_message));
                        intent.putExtra("message", ArduinoMessageUtilities.stringifyArduinoSetting(setting));
                        appContext.sendBroadcast(intent);
                    }
                });
                return setting;
            }
            case FLOAT: {
                Float floatValue;
                if (value instanceof String) {
                    floatValue = Float.valueOf((String) value);
                } else {
                    floatValue = (Float) value;
                }
                FloatSetting setting = new FloatSetting(id, floatValue, unit);
                setting.setOnFocusChangeListener((v, hasFocus) -> {
                    if(!hasFocus) {
                        setting.setValue(Float.parseFloat(((EditText) v).getText().toString()));
                        settingsViewModel.addSetting(setting);

                        Application appContext = ContextsSingleton.getInstance().getApplicationContext();
                        Intent intent = new Intent();
                        intent.setAction(appContext.getString(R.string.arduino_send_message));
                        intent.putExtra("message", ArduinoMessageUtilities.stringifyArduinoSetting(setting));
                        appContext.sendBroadcast(intent);
                    }
                });
                return setting;
            }
        }

        return null;
    }

    public static Setting getSetting(String id, String settingType, Object value) {
        return getSetting(id, settingType, value, null);
    }
}
