package com.example.carduino.receivers.canbus.factory;

import com.example.carduino.receivers.ReceiverInterface;

public class CanbusFactory {
    public static ReceiverInterface getCanbusInterface(String action) {
        try {
            return (ReceiverInterface) CanbusActions.valueOf(action).getClazz().newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
