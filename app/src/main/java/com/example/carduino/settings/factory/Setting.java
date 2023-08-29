package com.example.carduino.settings.factory;

import android.view.View;
import android.view.ViewGroup;

public abstract class Setting<T> {
    private String unit;
    private String id;
    private SettingsEnum settingEnum;
    private String label;
    private T value;

    public Setting() {
    }

    public Setting(String unit, String id, T value, SettingsEnum settingEnum) {
        this.unit = unit;
        this.id = id;
        this.settingEnum = settingEnum;
        this.value = value;
    }

    public Setting(String unit, String id, String label, T value, SettingsEnum settingEnum) {
        this.unit = unit;
        this.id = id;
        this.settingEnum = settingEnum;
        this.label = label;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public abstract View getView(View view, ViewGroup parent);
}
