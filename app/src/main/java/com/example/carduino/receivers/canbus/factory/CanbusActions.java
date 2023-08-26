package com.example.carduino.receivers.canbus.factory;

public enum CanbusActions {
    BRIGHTNESS(BrightnessAction.class, "BRIGHTNESS"),
    SETTING(SettingAction.class, "SETTING");

    private Class clazz;
    private String name;

    CanbusActions(Class clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
