package com.example.carduino.settings.settingviewfactory;

import android.view.View;

public abstract class SettingViewWrapper<T> {
    private View settingView;

    public SettingViewWrapper() {

    }

    public View getView() {
        return settingView;
    }

    public void setView(View settingView) {
        this.settingView = settingView;
    }

    public abstract void generateView(String label);
    public abstract void updateView(T value);
}
