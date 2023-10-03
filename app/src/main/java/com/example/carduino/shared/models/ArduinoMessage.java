package com.example.carduino.shared.models;

import android.content.Intent;

import com.example.carduino.receivers.canbus.factory.CanbusActions;

public class ArduinoMessage {
    private CanbusActions action;
    private String key;
    private String value;

    public ArduinoMessage(CanbusActions action, String key, String value) {
        this.action = action;
        this.key = key;
        this.value = value;
    }

    public ArduinoMessage(String key, String value) {
        this(null, key, value);
    }

    public ArduinoMessage(Intent intent) {
        this.action = CanbusActions.valueOf(intent.getStringExtra("action"));
        this.key = intent.getStringExtra("key");
        this.value = intent.getStringExtra("value");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CanbusActions getAction() {
        return action;
    }

    public void setAction(CanbusActions action) {
        this.action = action;
    }
}
