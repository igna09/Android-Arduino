package com.example.carduino.shared.singletons;

import com.example.carduino.settings.settingfactory.Setting;

import java.util.HashMap;
import java.util.Map;

public class SharedDataSingleton {
    private static SharedDataSingleton instance;
    private Map settings;

    private SharedDataSingleton(){}
    public static SharedDataSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new SharedDataSingleton();
        }
        return instance;
    }

    public Map getSettings() {
        return settings;
    }

    public void setSettings(Setting setting) {
        if(this.settings == null) {
            this.settings = new HashMap<String, Setting>();
        }

        this.settings.put(setting.getId(), setting);
    }

    public static void invalidate() {
        instance = null;
    }
}
