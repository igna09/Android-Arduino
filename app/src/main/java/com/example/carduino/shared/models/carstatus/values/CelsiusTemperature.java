package com.example.carduino.shared.models.carstatus.values;

public class CelsiusTemperature extends ValueFloat {
    public CelsiusTemperature(String id, Float temperature) {
        super(id, temperature, "°C");
    }

    public CelsiusTemperature() {
        super(null, null, "°C");
    }
}
