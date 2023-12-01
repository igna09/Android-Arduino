package com.example.carduino.shared.utilities;

import android.util.Log;

import com.example.carduino.shared.singletons.Logger;

public class LoggerUtilities {
    public static void logMessage(String message) {
        logMessage("CARDUINO_APP", message);
    }
    public static void logMessage(String label, String message) {
        Logger.getInstance().log(label + " - " + message);
        Log.d(label, message);
    }
    //TODO: remove
    public static void logArduinoMessage(String label, String message) {
        Logger.getInstance().log("arduino_message", label + " - " + message);
        Log.d(label, message);
    }
    public static void logException(Exception e) {
        Logger.getInstance().logException(e);
        e.printStackTrace();
    }
}
