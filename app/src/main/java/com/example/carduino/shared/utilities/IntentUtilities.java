package com.example.carduino.shared.utilities;

import android.content.Intent;

import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class IntentUtilities {
    /**
     * Every message received from Arduino MUST be in this format:
     * ArduinoActions;key;value;unit;
     * eg.: CAR_STATUS;BRIGHTNESS;2700
     */
    public static void sendArduinoMessageBroadcast(String message) {
        String[] splittedMessage = ArduinoMessageUtilities.parseArduinoMessage(message);

        if(splittedMessage.length == 3) {

            sendBroadcast(splittedMessage[0], splittedMessage[1], splittedMessage[2]);
        }
    }

    public static void sendBroadcast(String arduinoAction, String key, String value) {
        // TODO: remove this mock
        ArduinoActions action = ArduinoActions.CANBUS;
        ContextsSingleton.getInstance().getApplicationContext().sendBroadcast(createIntent(action.getIntentAction(), arduinoAction, key, value));
        if(action.getC() != null) {
            ContextsSingleton.getInstance().getApplicationContext().sendBroadcast(createIntent(action.getC(), action.getIntentAction(), arduinoAction, key, value));
        }
    }

    private static Intent createIntent(Class clazz, String intentAction, String arduinoAction, String key, String value) {
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
        return intent;
    }

    public static Intent createIntent(String intentAction, String arduinoAction, String key, String value) {
        return createIntent(null, intentAction, arduinoAction, key, value);
    }
}
