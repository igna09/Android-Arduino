package com.example.carduino.shared.models.carstatus;

public class CelsiusTemperature extends Value<Float> {
    public CelsiusTemperature(String id, Float temperature) {
        super(id, temperature, "°C");
    }

    public CelsiusTemperature() {
        super(null, null, "°C");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
