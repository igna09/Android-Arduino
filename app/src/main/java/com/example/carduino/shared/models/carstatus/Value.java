package com.example.carduino.shared.models.carstatus;

import androidx.annotation.NonNull;

public abstract class Value<T> {
    private T value;
    private String unit;
    private String id;

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

    public abstract T parseValueFromString(String value);
}
