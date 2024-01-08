package com.example.carduino.dialog;

public enum Duration {
    SHORT(5),
    LONG(10);

    private Integer duration;

    Duration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }
}
