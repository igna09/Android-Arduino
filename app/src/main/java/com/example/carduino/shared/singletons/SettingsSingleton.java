package com.example.carduino.shared.singletons;

import com.example.carduino.settings.settingviewfactory.SettingViewWrapper;
import com.example.carduino.shared.models.Settings;

import java.util.HashMap;
import java.util.Map;

public class SettingsSingleton {
    private static SettingsSingleton instance;

    private Settings settings;
    private Map<String, SettingViewWrapper> settingViews;

    private SettingsSingleton(){}

    public static SettingsSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new SettingsSingleton();
        }
        return instance;
    }

    public Settings getSettings() {
        if(settings == null) {
            this.settings = new Settings();
        }
        return settings;
    }

    public Map<String, SettingViewWrapper> getSettingViews() {
        if(settingViews == null) {
            this.settingViews = new HashMap<>();
        }
        return settingViews;
    }

    public static void invalidate() {
        instance = null;
    }
}
