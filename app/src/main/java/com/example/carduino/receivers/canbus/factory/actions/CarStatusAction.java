package com.example.carduino.receivers.canbus.factory.actions;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;

public class CarStatusAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(ArduinoMessage message) {
        Value value = CarStatusFactory.getCarStatusValue(message.getKey(), message.getValue());
        if(value != null) {
            CarStatusSingleton.getInstance().getCarStatus().putValue(value);
        }
    }
}
