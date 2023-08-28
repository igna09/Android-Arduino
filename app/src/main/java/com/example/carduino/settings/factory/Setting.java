package com.example.carduino.settings.factory;

import android.view.View;

public abstract class Setting {
    private String unit;
    private String id;
    private SettingsEnum settingEnum;
    private String label;

    public Setting() {
    }

    public Setting(String unit, String id, SettingsEnum settingEnum) {
        this.unit = unit;
        this.id = id;
        this.settingEnum = settingEnum;
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

    public abstract View getView(View view);
}
