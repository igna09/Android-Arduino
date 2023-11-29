package com.example.carduino.shared.models.carstatus.values;

public class FuelConsumptionKmL extends ValueFloat {
    public FuelConsumptionKmL(String id, Float consumption) {
        super(id, consumption, "km/l");
    }

    public FuelConsumptionKmL() {
        super(null, null, "km/l");
    }
}
