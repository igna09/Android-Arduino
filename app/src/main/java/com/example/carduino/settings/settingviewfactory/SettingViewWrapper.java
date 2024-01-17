package com.example.carduino.settings.settingviewfactory;

import android.view.View;

import com.example.carduino.settings.SettingCallback;
import com.example.carduino.settings.settingfactory.Setting;

public abstract class SettingViewWrapper<T> {
    private View settingView;
    private SettingCallback<T> callback;

    public SettingViewWrapper() {

    }

    public View getView() {
        return settingView;
    }

    public SettingCallback<T> getCallback() {
        return callback;
    }

    public void setCallback(SettingCallback<T> callback) {
        this.callback = callback;
    }

    public void setView(View settingView) {
        this.settingView = settingView;
    }

    public abstract void generateView(Setting setting, String label);
    public abstract void updateView(T value);
    public void onAction(T value) {
        if(callback != null) {
            callback.voidValueOperator(value);
        }
    };
}
