package com.example.carduino.shared.singletons;

import com.example.carduino.settings.fragments.Settings;
import com.example.carduino.shared.models.Setting;

import java.util.HashMap;
import java.util.Map;

public class SharedDataSingleton {
    private static SharedDataSingleton sharedDataSingleton;
    private Map settings;

    private SharedDataSingleton(){}
    public static SharedDataSingleton getInstance()
    {
        if (sharedDataSingleton == null)
        {
            sharedDataSingleton = new SharedDataSingleton();
        }
        return sharedDataSingleton;
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
}
