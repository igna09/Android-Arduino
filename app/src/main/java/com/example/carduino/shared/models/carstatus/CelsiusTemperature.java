package com.example.carduino.shared.models.carstatus;

public class CelsiusTemperature extends Value<Float> {
    public CelsiusTemperature(Float temperature) {
        super(temperature, "°C");
    }
}
