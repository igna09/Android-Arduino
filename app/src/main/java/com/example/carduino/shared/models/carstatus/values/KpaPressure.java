package com.example.carduino.shared.models.carstatus.values;

public class KpaPressure extends Value<Float> {
    public KpaPressure(String id, Float pressure) {
        super(id, pressure, "Kpa");
    }

    public KpaPressure() {
        super(null, null, "Kpa");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
