package com.example.carduino.shared.models.carstatus.values;

import java.text.DecimalFormat;

public class ValueFloat extends Value<Float> {
    public ValueFloat(String id, Float value, String unit) {
        super(id, value, unit);
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
