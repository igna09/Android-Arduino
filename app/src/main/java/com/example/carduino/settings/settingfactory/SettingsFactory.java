package com.example.carduino.settings.settingfactory;

import com.example.carduino.settings.SettingsEnum;

public class SettingsFactory {
    public static Setting getSetting(String enumName, String value) {
        Setting setting;
        try {
            setting =  (Setting) SettingsEnum.valueOf(enumName).getSettingValueType().newInstance();
        } catch (IllegalAccessException | InstantiationException | IllegalArgumentException e) {
//            throw new RuntimeException(e);
            return null;
        }

        setting.setId(enumName);
        setting.setLabel(SettingsEnum.valueOf(enumName).getLabel());
        setting.setValueFromString(value);
//        setting.generateView();

        return setting;
    }
}
