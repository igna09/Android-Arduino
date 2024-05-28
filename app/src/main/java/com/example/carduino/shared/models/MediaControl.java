package com.example.carduino.shared.models;

import java.util.Arrays;

public enum MediaControl {
    VOLUME_UP(0x00),
    VOLUME_DOWN(0x01),
    PLAY_PAUSE(0x02),
    NEXT(0x02),
    LONG_PRESS(0x04);

    private Integer id;

    MediaControl(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Enum getEnumById(Integer id) {
        return Arrays.stream(MediaControl.values()).filter(mediaControlEnum -> mediaControlEnum.getId().equals(id)).findFirst().orElse(null);
    }
}
