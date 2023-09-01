package com.example.carduino.shared.models.carstatus.propertychangelisteners;

import java.beans.PropertyChangeEvent;

public abstract class PropertyChangeListener<T> implements java.beans.PropertyChangeListener {
    public T getValue(Object value) {
        return (T) value;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.onPropertyChange(evt.getPropertyName(), getValue(evt.getOldValue()), getValue(evt.getNewValue()));
    }

    public abstract void onPropertyChange(String propertyName, T oldValue, T newValue);
}
