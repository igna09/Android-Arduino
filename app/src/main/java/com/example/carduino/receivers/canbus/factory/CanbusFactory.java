package com.example.carduino.receivers.canbus.factory;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;

public class CanbusFactory {
    public static ArduinoMessageExecutorInterface getCanbusInterface(String action) {
        try {
            return (ArduinoMessageExecutorInterface) CanbusActions.valueOf(action).getClazz().newInstance();
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
