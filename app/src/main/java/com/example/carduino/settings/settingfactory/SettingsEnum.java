package com.example.carduino.settings.settingfactory;

public enum SettingsEnum {
    /**
     * TODO: add executor class to manage setting change value?
     */
    OTA_MODE("Enter ota mode", BooleanSetting.class);

    private String label;
    private Class type;

    SettingsEnum(String label, Class type) {
        this.type = type;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Class getType() {
        return type;
    }
}
