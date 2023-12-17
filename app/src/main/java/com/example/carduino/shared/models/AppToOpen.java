package com.example.carduino.shared.models;

public enum AppToOpen {
    ANDROID_AUTO("com.syu.carlink"),
    CARDUINO("com.example.carduino");

    private String packageName;

    AppToOpen(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}
