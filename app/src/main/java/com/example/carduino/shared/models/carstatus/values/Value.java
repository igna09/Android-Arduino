package com.example.carduino.shared.models.carstatus.values;

import androidx.annotation.NonNull;

import java.beans.PropertyChangeListener;

public abstract class Value<T> {
    private T value;
    private String unit;
    private String id;
    private PropertyChangeListener propertyChangeListener;

    public Value() {
    }

    public Value(String id, T value, String unit) {
        this.value = value;
        this.unit = unit;
        this.id = id;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PropertyChangeListener getPropertyChangeListener() {
        return propertyChangeListener;
    }

    public void setPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.propertyChangeListener = propertyChangeListener;
    }

    public abstract T parseValueFromString(String value);

    @NonNull
    @Override
    public String toString() {
        return "id: " + getId() + ", value: " + getValue() + ", unit: " + getUnit();
    }
}
