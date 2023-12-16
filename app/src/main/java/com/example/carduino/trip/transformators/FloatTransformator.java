package com.example.carduino.trip.transformators;

import java.text.DecimalFormat;

public class FloatTransformator implements CardTransformator {
    private String format;

    public FloatTransformator() {
        format = "0.00";
    }

    public FloatTransformator(String format) {
        this.format = format;
    }

    @Override
    public String transform(String value) {
        if(value != null) {
            Float v = Float.parseFloat(value);
            DecimalFormat df = new DecimalFormat(format);
            return df.format(v);
        } else {
            return "-";
        }
    }
}
