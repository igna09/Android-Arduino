package com.example.carduino.receivers.canbus.factory;

import java.util.Arrays;

public enum CanbusActions {
    READ_SETTING(SettingAction.class, "READ_SETTING"),
    CAR_STATUS(CarStatusAction.class, "CAR_STATUS");
    //TODO: add media control action
    //WRITE_SETTING("WRITE_SETTING");

    private Class clazz;
    private String id;

    CanbusActions(Class clazz, String id) {
        this.clazz = clazz;
        this.id = id;
    }

    CanbusActions(String id) {
        this(null, id);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getid() {
        return id;
    }

    public static CanbusActions getCanbusActionFromid(String id) {
        return Arrays.stream(CanbusActions.values()).filter(canbusActions -> canbusActions.getid().equals(id)).findFirst().orElse(null);
    }
}
