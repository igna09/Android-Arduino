package com.example.carduino.shared.models.carstatus.values;

public class Consumption extends Value<Float> {
    public Consumption(String id, Float pressure) {
        super(id, pressure, "mg/h");
    }

    public Consumption() {
        super(null, null, "mg/h");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
