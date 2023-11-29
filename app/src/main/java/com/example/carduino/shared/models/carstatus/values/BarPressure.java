package com.example.carduino.shared.models.carstatus.values;

public class BarPressure extends ValueFloat {
    public BarPressure(String id, Float pressure) {
        super(id, pressure, "Bar");
    }

    public BarPressure() {
        super(null, null, "Bar");
    }
}
