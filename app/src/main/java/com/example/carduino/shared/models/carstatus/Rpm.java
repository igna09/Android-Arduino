package com.example.carduino.shared.models.carstatus;

public class Rpm extends Value<Integer> {
    public Rpm(String id, Integer rpm) {
        super(id, rpm, "rpm");
    }

    public Rpm() {
        super(null, null, "rpm");
    }

    @Override
    public Integer parseValueFromString(String value) {
        return Integer.parseInt(value);
    }
}
