package com.example.carduino.receivers.canbus.factory;

public class CanbusFactory {
    public static CanbusInterface getCanbusInterface(String action) {
        try {
            return (CanbusInterface) CanbusActions.valueOf(action).getClazz().newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
