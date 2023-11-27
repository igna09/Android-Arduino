package com.example.carduino.shared.models.carstatus.values;

public class BarPressure extends Value<Float> {
    public BarPressure(String id, Float pressure) {
        super(id, pressure, "mbar");
    }

    public BarPressure() {
        super(null, null, "mbar");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
