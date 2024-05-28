package com.example.carduino.shared.models.carstatus;

import com.example.carduino.shared.models.carstatus.values.Value;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

public class CarStatusFactory {
    public static Value getCarStatusValue(String id, String value) {
        CarStatusEnum carStatusEnum = (CarStatusEnum) CarStatusEnum.getEnumById(Integer.parseInt(id));
        if(carStatusEnum != null) {
            try {
                Value v = (Value) carStatusEnum.getType().newInstance();
                v.setId(carStatusEnum.name());
                v.setValue(v.parseValueFromString(value));
                if(carStatusEnum.getPropertyChangeListener() != null) {
                    v.setPropertyChangeListener(new PropertyChangeListenerProxy(carStatusEnum.name(), (PropertyChangeListener) carStatusEnum.getPropertyChangeListener().newInstance()));
                }
                return v;
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
