package com.example.carduino.shared.models.carstatus;

public class CmDistance extends Value<Float> {
    public CmDistance(String id, Float centimeters) {
        super(id, centimeters, "cm");
    }

    public CmDistance() {
        super(null, null, "cm");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
