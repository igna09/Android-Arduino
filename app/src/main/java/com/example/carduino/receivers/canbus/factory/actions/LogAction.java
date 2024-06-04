package com.example.carduino.receivers.canbus.factory.actions;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.settings.SettingsEnum;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.settings.settingfactory.SettingsFactory;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.LoggerSingleton;
import com.example.carduino.shared.singletons.SettingsSingleton;

import java.text.MessageFormat;

public class LogAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(ArduinoMessage message) {
        //TODO: write MessageLog (key=nodeId, value=logId@value) to log file
        String nodeId = message.getKey();
        String[] splittedValues = message.getValue().split("@");
        String logId = splittedValues[0];
        String value = splittedValues[1];

        LoggerSingleton.getInstance().log(MessageFormat.format("nodeId {0}, logId {1}, value {2}", nodeId, logId, value));
    }
}
