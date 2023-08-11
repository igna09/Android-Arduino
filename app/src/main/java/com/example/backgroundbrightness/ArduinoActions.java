package com.example.backgroundbrightness;

import com.example.backgroundbrightness.receivers.BrightnessReceiver;
import com.example.backgroundbrightness.workers.ArduinoWorker;

public enum ArduinoActions {
    LOGGER("LOGGER", null),
    BRIGHTNESS("BRIGHTNESS", BrightnessReceiver.class),
    SEND("SEND", null);

    private String action;
    private Class c;
    ArduinoActions(String action, Class c) {
        this.action = action;
        this.c = c;
    }

    public String getAction() {
        return action;
    }

    public Class getC() {
        return c;
    }
}
