package com.example.carduino.shared.utilities;

import android.content.Intent;

import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class IntentUtilities {
    /**
     * Every message received from Arduino MUST be in this format:
     * ArduinoActions;key;value;unit;
     * eg.: CANBUS;BRIGHTNESS;2700;lux;
     */
    public static void sendArduinoMessageBroadcast(String message) {
//        String[] splittedMessage = message.split(";");
        String[] splittedMessage = ArduinoMessageUtilities.parseArduinoMessage(message);
        sendBroadcast(splittedMessage[0], splittedMessage[1], splittedMessage[2], splittedMessage[3]);
    }

    public static void sendBroadcast(String arduinoAction, String key, String value, String unit) {
        ArduinoActions action = ArduinoActions.valueOf(arduinoAction);
        ContextsSingleton.getInstance().getApplicationContext().sendBroadcast(createIntent(action.getIntentAction(), action.getAction(), key, value, unit));
        if(action.getC() != null) {
            ContextsSingleton.getInstance().getApplicationContext().sendBroadcast(createIntent(action.getC(), action.getIntentAction(), action.getAction(), key, value, unit));
        }
    }

    private static Intent createIntent(Class clazz, String intentAction, String arduinoAction, String key, String value, String unit) {
        Intent intent;
        if(clazz != null) {
            intent = new Intent(ContextsSingleton.getInstance().getApplicationContext(), clazz);
        } else {
            intent = new Intent();
        }
        intent.setAction(intentAction);
        intent.putExtra("action", arduinoAction);
        intent.putExtra("key", key);
        intent.putExtra("value", value);
        intent.putExtra("unit", unit);
        return intent;
    }

    private static Intent createIntent(Class clazz, String intentAction, String arduinoAction, String key, String value) {
        return createIntent(clazz, intentAction, arduinoAction, key, value, "");
    }

    private static Intent createIntent(String intentAction, String arduinoAction, String key, String value, String unit) {
        return createIntent(null, intentAction, arduinoAction, key, value, unit);
    }

    private static Intent createIntent(String intentAction, String arduinoAction, String key, String value) {
        return createIntent(null, intentAction, arduinoAction, key, value, null);
    }
}
