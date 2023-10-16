package com.example.carduino.shared.models;

public enum MediaControl {
    NEXT("NEXT"),
    VOLUME_UP("VOLUME_UP"),
    VOLUME_DOWN("VOLUME_DOWN"),
    PLAY_PAUSE("PLAY_PAUSE");

    String id;

    MediaControl(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
