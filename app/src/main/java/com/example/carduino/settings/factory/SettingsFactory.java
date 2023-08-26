package com.example.carduino.settings.factory;

import com.example.carduino.shared.models.Setting;

public class SettingsFactory {
    public static Setting<?> getSetting(String id, String settingType, Object value, String unit) {
        Setting setting;

        switch (SettingsEnum.valueOf(settingType)) {
            case BOOLEAN:
                Boolean booleanSetting;
                if(value instanceof String) {
                    booleanSetting = ((String) value).equals("true");
                } else {
                    booleanSetting = (Boolean) value;
                }
                return new Setting<Boolean>(booleanSetting, unit, id, SettingsEnum.BOOLEAN);
            case INTEGER:
                Integer integerValue;
                if(value instanceof String) {
                    integerValue = Integer.valueOf((String) value);
                } else {
                    integerValue = (Integer) value;
                }
                return new Setting<Integer>(integerValue, unit, id, SettingsEnum.INTEGER);
            case FLOAT:
                Float floatValue;
                if(value instanceof String) {
                    floatValue = Float.valueOf((String) value);
                } else {
                    floatValue = (Float) value;
                }
                return new Setting<Float>(floatValue, unit, id, SettingsEnum.FLOAT);
            case STRING:
                return new Setting<String>((String) value, unit, id, SettingsEnum.STRING);
        }

        return null;
    }

    public static Setting<?> getSetting(String id, String settingType, Object value) {
        return getSetting(id, settingType, value, null);
    }
}
