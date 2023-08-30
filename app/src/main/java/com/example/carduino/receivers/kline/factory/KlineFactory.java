package com.example.carduino.receivers.kline.factory;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;

public class KlineFactory {
    public static ArduinoMessageExecutorInterface getKlineInterface(String action) {
        try {
            return (ArduinoMessageExecutorInterface) KlineActions.valueOf(action).getClazz().newInstance();
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
