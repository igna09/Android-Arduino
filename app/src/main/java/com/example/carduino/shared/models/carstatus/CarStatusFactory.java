package com.example.carduino.shared.models.carstatus;

import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

public class CarStatusFactory {
    public static Value getCarStatusValue(String id, String value) {
        CarStatusEnum carStatusEnum = CarStatusEnum.valueOf(id);
        try {
            Value v = (Value) carStatusEnum.getType().newInstance();
            v.setId(id);
            v.setValue(v.parseValueFromString(value));
            if(carStatusEnum.getPropertyChangeListener() != null) {
                v.setPropertyChangeListener(new PropertyChangeListenerProxy(carStatusEnum.getId(), (PropertyChangeListener) carStatusEnum.getPropertyChangeListener().newInstance()));
            }
            return v;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
