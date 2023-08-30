package com.example.carduino.shared.models.carstatus;

import com.example.carduino.shared.singletons.CarStatusSingleton;

public class CarStatusFactory {
    public static Value getCarStatusValue(String id, String value) {
        CarStatusEnum carStatusEnum = CarStatusEnum.valueOf(id);
        try {
            Value v = (Value) carStatusEnum.getType().newInstance();
            v.setId(id);
            v.setValue(v.parseValueFromString(value));
            return v;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
