package com.example.carduino.shared.models.carstatus.values;

import java.text.DecimalFormat;

public abstract class ValueInteger extends Value<Integer> {
    public ValueInteger(String id, Integer value, String unit) {
        super(id, value, unit);
    }
}
