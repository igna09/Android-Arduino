package com.example.carduino.settings;

import com.example.carduino.settings.settingfactory.BooleanSetting;
import com.example.carduino.settings.settingviewfactory.BooleanSettingViewWrapper;

public enum SettingsEnum {
    /**
     * TODO: add executor class to manage setting change value?
     */
    OTA_MODE("Enter ota mode", BooleanSetting.class, BooleanSettingViewWrapper.class),
    RESTART("Restart all nodes", BooleanSetting.class, BooleanSettingViewWrapper.class);

    private String label;
    private Class settingType;
    private Class settingViewType;

    SettingsEnum(String label, Class settingType, Class settingViewType) {
        this.settingType = settingType;
        this.label = label;
        this.settingViewType = settingViewType;
    }

    public String getLabel() {
        return label;
    }

    public Class getSettingType() {
        return settingType;
    }

    public Class getSettingViewType() {
        return settingViewType;
    }
}
