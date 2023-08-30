package com.example.carduino.shared.models.carstatus.values;

public class KmhSpeed extends Value<Integer> {
    public KmhSpeed(String id, Integer speed) {
        super(id, speed, "Km/h");
    }

    public KmhSpeed() {
        super(null, null, "Km/h");
    }

    @Override
    public Integer parseValueFromString(String value) {
        return Integer.parseInt(value);
    }
}
