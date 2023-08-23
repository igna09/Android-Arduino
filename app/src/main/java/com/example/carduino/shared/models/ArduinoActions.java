package com.example.carduino.shared.models;

import android.content.IntentFilter;

import com.example.carduino.receivers.canbus.CanbusReceiver;
import com.example.carduino.receivers.kline.KlineReceiver;

public enum ArduinoActions {
//    LOGGER("LOGGER", null),
//    BRIGHTNESS("BRIGHTNESS", CanbusReceiver.class),
    SEND("SEND", null),
    CANBUS("CANBUS", CanbusReceiver.class),
    KLINE("KLINE", KlineReceiver.class);

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

    public String getIntentAction() {
        return "com.example.backgroundbrightness.RECEIVED_ARDUINO_MESSAGE_" + getAction();
    }

    public IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(getIntentAction());
        return filter;
    }
}
