package com.example.carduino.shared.utilities;

import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.ArduinoSingleton;

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

    public static void sendArduinoMessage(ArduinoMessage message) {
        String parsedMessage = message.getAction().getId() + ";" + message.getKey() + ";" + message.getValue() + ";";
        LoggerUtilities.logArduinoMessage("ArduinoService", "sending " + parsedMessage);
//        ArduinoSingleton.getInstance().getArduinoService().sendMessageToArduino(stringifyArduinoMessage(message));
        ArduinoSingleton.getInstance().getArduinoService().sendMessageToArduino(parsedMessage);
    }

    public static String stringifyArduinoMessage(ArduinoMessage message) {
        String builder = message.getAction().name() +
                ";" +
                message.getKey() +
                ";" +
                message.getValue() +
                ";";
        return builder;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
