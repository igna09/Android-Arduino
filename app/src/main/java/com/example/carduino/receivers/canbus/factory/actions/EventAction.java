package com.example.carduino.receivers.canbus.factory.actions;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.Event;
import com.example.carduino.shared.utilities.DialogUtilities;

public class EventAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(ArduinoMessage message) {
        Event event = Event.valueOf(message.getKey());

        switch (event) {
            case BLE_PAIRING_CODE:
                DialogUtilities.openDialogBLEPairingCode(message.getValue());
                break;
        }
    }
}
