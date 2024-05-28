package com.example.carduino.receivers.canbus.factory;

import com.example.carduino.receivers.canbus.factory.actions.CarStatusAction;
import com.example.carduino.receivers.canbus.factory.actions.MediaControlAction;
import com.example.carduino.receivers.canbus.factory.actions.SettingAction;
import com.example.carduino.settings.SettingsEnum;
import com.example.carduino.shared.models.MediaControl;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;

import java.util.Arrays;
import java.util.function.Function;

public enum CanbusActions {
    CAR_STATUS(0x00, CarStatusAction.class, CarStatusEnum::getEnumById),
    READ_SETTING(0x01, SettingAction.class, SettingsEnum::getEnumById),
    MEDIA_CONTROL(0x02, MediaControlAction .class, MediaControl::getEnumById),
    WRITE_SETTING(0x03),
//    ERROR(0x05),
    GET_SETTINGS(0x07);

    private Class clazz;
    private Integer id;
    Function<Integer, Enum> getActionEnumFromId;

    CanbusActions(Integer id, Class clazz, Function<Integer, Enum> getActionEnumFromId) {
        this.clazz = clazz;
        this.id = id;
        this.getActionEnumFromId = getActionEnumFromId;
    }

    CanbusActions(Integer id) {
        this(id, null, null);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Integer getId() {
        return id;
    }

    public Function<Integer, Enum> getGetActionEnumFromId() {
        return getActionEnumFromId;
    }

    public static Enum getEnumById(Integer id) {
        return Arrays.stream(CanbusActions.values()).filter(canbusActions -> canbusActions.getId().equals(id)).findFirst().orElse(null);
    }
}
