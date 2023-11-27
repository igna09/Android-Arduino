package com.example.carduino.shared.models.carstatus.values;

public class BarPressure extends ValueFloat {
    public BarPressure(String id, Float pressure) {
        super(id, pressure, "Bar");
    }

    public BarPressure() {
        super(null, null, "Bar");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }

    @Override
    public Float transormFunction(Float value) {
        if(value != null) {
            return (value - 1000) / 1000;
        } else {
            return super.transormFunction(value);
        }
    }
}
