package com.example.carduino.shared.utilities;

import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.ArduinoActions;

import java.util.Arrays;

public class ArduinoMessageUtilities {
    private static String[] parseMessage(String message, String delimeter) {
        String[] values = message.split(delimeter, -1);
        values = Arrays.stream(values).filter(s -> !s.isEmpty()).toArray(String[]::new);
        return values;
    }

    public static String[] parseArduinoMessage(String message) {
        return parseMessage(message, ";");
    }

    public static String[] parseCanbusSetting(String message) {
        return parseMessage(message, "-");
    }

    public static String[] parseCanbusCarStatus(String message) {
        return parseMessage(message, "-");
    }

    public static String stringifyArduinoSetting(Setting setting) {
        StringBuilder builder = new StringBuilder("");
        return builder.append(ArduinoActions.CANBUS)
                .append(";")
                .append("WRITE_SETTING")
                .append(";")
                .append(setting.getId())
                .append("-")
                .append(setting.getSettingEnum())
                .append("-")
                .append(setting.getValue().toString())
                .append("-")
                .append(";")
                .append(";")
                .toString();
    }
}
