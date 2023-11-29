package com.example.carduino.shared.singletons;

import com.example.carduino.shared.models.Settings;

public class SettingsSingleton {
    private static SettingsSingleton settingsSingleton;

    private Settings settings;

    private SettingsSingleton(){}

    public static SettingsSingleton getInstance()
    {
        if (settingsSingleton == null)
        {
            settingsSingleton = new SettingsSingleton();
        }
        return settingsSingleton;
    }

    public Settings getSettings() {
        if(settings == null) {
            this.settings = new Settings();
        }
        return settings;
    }
}
