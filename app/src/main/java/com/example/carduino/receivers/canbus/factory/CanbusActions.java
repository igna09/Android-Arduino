package com.example.carduino.receivers.canbus.factory;

import com.example.carduino.receivers.canbus.factory.actions.CarStatusAction;
import com.example.carduino.receivers.canbus.factory.actions.MediaControlAction;
import com.example.carduino.receivers.canbus.factory.actions.SettingAction;

import java.util.Arrays;

public enum CanbusActions {
    READ_SETTING(SettingAction.class, "READ_SETTING"),
    CAR_STATUS(CarStatusAction.class, "CAR_STATUS"),
    WRITE_SETTING("WRITE_SETTING"),
    MEDIA_CONTROL(MediaControlAction .class, "MEDIA_CONTROL"),
    GET_SETTINGS("GET_SETTINGS");

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
        return Arrays.stream(CanbusActions.values()).filter(canbusActions -> canbusActions.name().equals(id)).findFirst().orElse(null);
    }
}
