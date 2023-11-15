package com.example.carduino.receivers.canbus.factory.actions;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.singletons.CarStatusSingleton;

public class CarStatusAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(ArduinoMessage message) {
        CarStatusSingleton.getInstance().getCarStatus().putValue(CarStatusFactory.getCarStatusValue(message.getKey(), message.getValue()));
    }
}
