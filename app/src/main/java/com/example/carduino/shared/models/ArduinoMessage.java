package com.example.carduino.shared.models;

public class ArduinoMessage {
    private ArduinoActions action;
    private String message;

    public ArduinoMessage(ArduinoActions action, String message) {
        this.action = action;
        this.message = message;
    }

    public ArduinoActions getAction() {
        return action;
    }

    public void setAction(ArduinoActions action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
