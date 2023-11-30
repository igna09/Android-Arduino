package com.example.carduino.settings.settingviewfactory;

import com.example.carduino.settings.SettingsEnum;

public class SettingViewFactory {
    public static SettingViewWrapper getSettingView(String id) {
        SettingViewWrapper settingViewWrapper;
        try {
            settingViewWrapper = (SettingViewWrapper) SettingsEnum.valueOf(id).getSettingViewType().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        return settingViewWrapper;
    }
}
