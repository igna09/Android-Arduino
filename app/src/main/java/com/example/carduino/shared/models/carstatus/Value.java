package com.example.carduino.shared.models.carstatus;

public class Value<T> {
    private T value;
    private String unit;

    public Value() {
    }

    public Value(T value, String unit) {
        this.value = value;
        this.unit = unit;
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
}
