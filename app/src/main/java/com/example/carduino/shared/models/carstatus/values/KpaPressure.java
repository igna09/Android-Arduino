package com.example.carduino.shared.models.carstatus.values;

public class KpaPressure extends Value<Integer> {
    public KpaPressure(String id, Integer pressure) {
        super(id, pressure, "Kpa");
    }

    public KpaPressure() {
        super(null, null, "Kpa");
    }

    @Override
    public Integer parseValueFromString(String value) {
        return Integer.parseInt(value);
    }
}
