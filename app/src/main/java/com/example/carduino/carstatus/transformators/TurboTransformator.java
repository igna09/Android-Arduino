package com.example.carduino.carstatus.transformators;

public class TurboTransformator extends FloatTransformator {
    @Override
    public String transform(String value) {
        if(value != null) {
            Float v = Float.parseFloat(value);
            v = (v - 1000) / 1000;
            return super.transform(v.toString());
        } else {
            return "-";
        }
    }
}
