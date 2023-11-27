package com.example.carduino.shared.models.carstatus.values;

public class FuelConsumptionKmL extends Value<Float> {
    public FuelConsumptionKmL(String id, Float consumption) {
        super(id, consumption, "km/l");
    }

    public FuelConsumptionKmL() {
        super(null, null, "km/l");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
