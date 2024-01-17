package com.example.carduino.settings;

import com.example.carduino.settings.settingfactory.Setting;

public interface SettingCallback<T> {
    void voidValueOperator(T value);
}
