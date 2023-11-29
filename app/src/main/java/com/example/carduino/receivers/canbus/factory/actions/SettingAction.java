package com.example.carduino.receivers.canbus.factory.actions;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.settings.settingfactory.SettingsFactory;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.shared.singletons.SettingsSingleton;

public class SettingAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(ArduinoMessage message) {
        Setting setting = SettingsFactory.getSetting(message.getKey(), message.getValue());
        SettingsSingleton.getInstance().getSettings().addSetting(setting);
    }
}
