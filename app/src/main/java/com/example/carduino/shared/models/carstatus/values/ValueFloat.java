package com.example.carduino.shared.models.carstatus.values;

import java.text.DecimalFormat;

public abstract class ValueFloat extends Value<Float> {
    public ValueFloat(String id, Float value, String unit) {
        super(id, value, unit);
    }

    @Override
    public Float getValue() {
        DecimalFormat df = new DecimalFormat("0.00");
        if(super.getValue() != null) {
            return Float.parseFloat(df.format((Float) super.getValue()));
        } else {
            return super.getValue();
        }
    }
}
