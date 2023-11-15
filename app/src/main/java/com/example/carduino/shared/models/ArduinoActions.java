package com.example.carduino.shared.models;

import android.content.IntentFilter;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public enum ArduinoActions {
    SEND("SEND", null),
    CANBUS("CANBUS", null);

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
        return ContextsSingleton.getInstance().getApplicationContext().getString(R.string.arduino_received_message_root) + getAction();
    }

    public IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(getIntentAction());
        return filter;
    }
}
