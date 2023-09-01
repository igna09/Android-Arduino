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
    private PropertyChangeSupport support;

    public CarStatus() {
        this.carStatusValues = new HashMap<>();
        Arrays.stream(CarStatusEnum.values()).forEach(carStatusEnum -> {
            try {
                this.carStatusValues.put(carStatusEnum.getId(), (Value) carStatusEnum.getType().newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });

        this.support = new PropertyChangeSupport(this);

        this.addPropertyChangeListener(new GeneralCarStatusPropertyChangeListener());
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
        support.firePropertyChange(value.getId(), this.carStatusValues.get(value.getId()), value);
        this.carStatusValues.put(value.getId(), value);
    }

    private void updateValue(Value value) {
        support.firePropertyChange(value.getId(), this.carStatusValues.get(value.getId()), value);
        this.carStatusValues.get(value.getId()).setValue(value.getValue());
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
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
