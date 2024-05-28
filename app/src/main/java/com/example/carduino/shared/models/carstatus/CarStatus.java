package com.example.carduino.shared.models.carstatus;

import androidx.annotation.NonNull;

import com.example.carduino.shared.models.carstatus.propertychangelisteners.GeneralCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.Value;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;

public class CarStatus {
    private HashMap <String, Value> carStatusValues;
    public PropertyChangeSupport support;
    private PropertyChangeListener propertyChangeListener;

    public CarStatus() {
        this.carStatusValues = new HashMap<>();
        Arrays.stream(CarStatusEnum.values()).forEach(carStatusEnum -> {
            try {
                Value v = (Value) carStatusEnum.getType().newInstance();
                v.setId(carStatusEnum.name());
                this.carStatusValues.put(carStatusEnum.name(), v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });

        this.support = new PropertyChangeSupport(this);

        this.propertyChangeListener = new GeneralCarStatusPropertyChangeListener();
//        this.addPropertyChangeListener(this.propertyChangeListener);
    }

    public void putValue(Value value) {
        if(this.carStatusValues.containsKey(value.getId()) && this.carStatusValues.get(value.getId()).getPropertyChangeListener() != null) {
            updateValue(value);
        } else {
            addValue(value);
        }
    }

    private void addValue(Value value) {
        if(value.getPropertyChangeListener() != null) {
            this.addPropertyChangeListener(value.getPropertyChangeListener());
        }
        Value oldValue = null;
        try {
            oldValue = value.getClass().newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        oldValue.setId(this.carStatusValues.get(value.getId()).getId());
        oldValue.setValueWithoutTransform(this.carStatusValues.get(value.getId()).getValue());
        oldValue.setUnit(this.carStatusValues.get(value.getId()).getUnit());
        oldValue.setPropertyChangeListener(this.carStatusValues.get(value.getId()).getPropertyChangeListener());
        this.carStatusValues.put(value.getId(), value);
        support.firePropertyChange(value.getId(), oldValue, value);
    }

    private void updateValue(Value value) {
        Value oldValue = null;
        try {
            oldValue = value.getClass().newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        oldValue.setId(this.carStatusValues.get(value.getId()).getId());
        oldValue.setValueWithoutTransform(this.carStatusValues.get(value.getId()).getValue());
        oldValue.setUnit(this.carStatusValues.get(value.getId()).getUnit());
        oldValue.setPropertyChangeListener(this.carStatusValues.get(value.getId()).getPropertyChangeListener());
        this.carStatusValues.get(value.getId()).setValueWithoutTransform(value.getValue());
        support.firePropertyChange(value.getId(), oldValue, value);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public HashMap<String, Value> getCarStatusValues() {
        return carStatusValues;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("{");
        builder.append("\n");
        this.carStatusValues.values().forEach(value -> {
            builder.append("    ");
            builder.append(value.toString());
            builder.append("\n");
        });
        builder.append("}");
        return builder.toString();
    }
}
