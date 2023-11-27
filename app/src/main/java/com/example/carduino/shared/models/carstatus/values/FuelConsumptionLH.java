package com.example.carduino.shared.models.carstatus.values;

public class FuelConsumptionLH extends ValueFloat {
    public FuelConsumptionLH(String id, Float consumption) {
        super(id, consumption, "l/h");
    }

    public FuelConsumptionLH() {
        super(null, null, "l/h");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
