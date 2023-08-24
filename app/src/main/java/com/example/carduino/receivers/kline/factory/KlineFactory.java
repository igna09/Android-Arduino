package com.example.carduino.receivers.kline.factory;

import com.example.carduino.receivers.ReceiverInterface;

public class KlineFactory {
    public static ReceiverInterface getKlineInterface(String action) {
        try {
            return (ReceiverInterface) KlineActions.valueOf(action).getClazz().newInstance();
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
