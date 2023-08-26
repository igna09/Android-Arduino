package com.example.carduino.receivers.canbus.factory;

import android.content.Context;

import com.example.carduino.receivers.ReceiverInterface;
import com.example.carduino.settings.factory.SettingsFactory;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.Setting;
import com.example.carduino.shared.utilities.SettingsUtilities;

public class SettingAction implements ReceiverInterface {
    @Override
    public void execute(Context context, ArduinoMessage message) {
        String[] settingValues = message.getValue().split(";");
        Setting setting = SettingsFactory.getSetting(settingValues[0], settingValues[1], settingValues[2], settingValues[3]);
        SettingsUtilities.updateSetting(setting);
    }
}
