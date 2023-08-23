package com.example.carduino.shared.models;

import android.content.Intent;

public class ArduinoMessage {
    private ArduinoActions action;
    private String key;
    private String value;
    private String unit;

    public ArduinoMessage(ArduinoActions action, String key, String value, String unit) {
        this.action = action;
        this.key = key;
        this.value = value;
        this.unit = unit;
    }

    public ArduinoMessage(String key, String value, String unit) {
        this(null, key, value, unit);
    }

    public ArduinoMessage(Intent intent) {
        if(intent.getStringExtra("action") != null) {
            this.action = ArduinoActions.valueOf(intent.getStringExtra("action"));
        }
        this.key = intent.getStringExtra("key");
        this.value = intent.getStringExtra("value");
        this.unit = intent.getStringExtra("unit");
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArduinoActions getAction() {
        return action;
    }

    public void setAction(ArduinoActions action) {
        this.action = action;
    }
}
