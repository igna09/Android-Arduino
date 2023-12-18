package com.example.carduino.shared.models.carstatus.values;

public class ValueBoolean extends Value<Boolean> {
    public ValueBoolean(String id, Boolean value, String unit) {
        super(id, value, unit);
    }
    public ValueBoolean() {
        super();
    }

    @Override
    public Boolean parseValueFromString(String value) {
        return Boolean.parseBoolean(value);
    }
}
