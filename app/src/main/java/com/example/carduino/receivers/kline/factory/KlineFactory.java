package com.example.carduino.receivers.kline.factory;

public class KlineFactory {
    public static KlineInterface getKlineInterface(String action) {
        try {
            return (KlineInterface) KlineActions.valueOf(action).getClazz().newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
