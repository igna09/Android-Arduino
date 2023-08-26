package com.example.carduino.shared.utilities;

import com.example.carduino.shared.models.Setting;
import com.example.carduino.shared.singletons.SharedDataSingleton;

public class SettingsUtilities {
    public static void updateSetting(Setting setting) {
        SharedDataSingleton.getInstance().getSettings().put(setting.getId(), setting);
    }
}
