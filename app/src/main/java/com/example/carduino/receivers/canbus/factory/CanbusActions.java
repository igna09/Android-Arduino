package com.example.carduino.receivers.canbus.factory;

public enum CanbusActions {
//    BRIGHTNESS(BrightnessAction.class, "BRIGHTNESS"),
    READ_SETTING(SettingAction.class, "READ_SETTING"),
    CAR_STATUS(CarStatusAction.class, "CAR_STATUS");
    //WRITE_SETTING("WRITE_SETTING");

    private Class clazz;
    private String name;

    CanbusActions(Class clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    CanbusActions(String name) {
        this(null, name);
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
