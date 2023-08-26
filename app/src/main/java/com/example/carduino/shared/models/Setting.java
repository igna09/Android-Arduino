package com.example.carduino.shared.models;

import com.example.carduino.settings.factory.SettingsEnum;

public class Setting<T> {
    private T value;
    private String unit;
    private String id;
    private SettingsEnum settingEnum;

    public Setting(T value, String unit, String id, SettingsEnum settingEnum) {
        this.value = value;
        this.unit = unit;
        this.id = id;
        this.settingEnum = settingEnum;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SettingsEnum getSettingEnum() {
        return settingEnum;
    }

    public void setSettingEnum(SettingsEnum settingEnum) {
        this.settingEnum = settingEnum;
    }
}
