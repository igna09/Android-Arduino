package com.example.carduino.settings.settingfactory;

public class SettingsFactory {
    public static Setting getSetting(String id, String value) {
        Setting setting;
        try {
            setting =  (Setting) SettingsEnum.valueOf(id).getType().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        setting.setId(id);
        setting.setLabel(SettingsEnum.valueOf(id).getLabel());
        setting.setValueFromString(value);
//        setting.generateView();

        return setting;
    }
}
