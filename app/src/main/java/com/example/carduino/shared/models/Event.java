package com.example.carduino.shared.models;

import com.example.carduino.shared.BaseEnum;

import java.util.Arrays;

public enum Event implements BaseEnum {
    ENABLE_NEW_BLE_PAIRING(0x0B),
    DISABLE_NEW_BLE_PAIRING(0x0C),
    RESET_WEBAPP(0x0E),
    BLE_PAIRING_CODE(0x0F);

    private Integer id;

    Event(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static BaseEnum getEnumById(Integer id) {
        return Arrays.stream(Event.values()).filter(mediaControlEnum -> mediaControlEnum.getId() != null && mediaControlEnum.getId().equals(id)).findFirst().orElse(null);
    }

    public static BaseEnum getEnumByName(String name) {
        return Event.valueOf(name);
    }
}
