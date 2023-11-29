package com.example.carduino.shared.models.carstatus.values;

import java.text.DecimalFormat;

public class ValueInteger extends Value<Integer> {
    public ValueInteger(String id, Integer value, String unit) {
        super(id, value, unit);
    }

    @Override
    public Integer parseValueFromString(String value) {
        return Integer.parseInt(value);
    }
}
