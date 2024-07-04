package com.example.carduino.receivers.canbus.factory.actions;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.settings.SettingsEnum;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.settings.settingfactory.SettingsFactory;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.FileSystemSingleton;
import com.example.carduino.shared.singletons.LoggerSingleton;
import com.example.carduino.shared.singletons.SettingsSingleton;
import com.example.carduino.shared.utilities.LoggerUtilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogAction implements ArduinoMessageExecutorInterface {
    private static final String LOGS_FOLDER_NAME = "LOGS";
    @Override
    public void execute(ArduinoMessage message) {
        String nodeId = message.getKey();
        String[] splittedValues = message.getValue().split("@");
        String logId = splittedValues[0];
        String value = splittedValues[1];

        FileSystemSingleton fileSystemSingleton = FileSystemSingleton.getInstance();
        File logsFolder = fileSystemSingleton.createOrGetFolder(fileSystemSingleton.getCarduinoRootFolder(), LOGS_FOLDER_NAME);
        try {
            StringBuilder builder = new StringBuilder();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            builder.append(df.format(new Date()));
            builder.append(" - ");
            builder.append(logId);
            builder.append(" - ");
            builder.append(value);
            builder.append('\n');

            File nodeLogsFile = fileSystemSingleton.createOrGetFile(logsFolder, nodeId + ".txt");
            fileSystemSingleton.writeToFile(nodeLogsFile, builder.toString(), true);
        } catch (IOException e) {
            LoggerUtilities.logException(e);
        }
    }
}
