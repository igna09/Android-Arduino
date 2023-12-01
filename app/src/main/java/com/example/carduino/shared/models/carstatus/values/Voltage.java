package com.example.carduino.shared.models.carstatus.values;

public class Voltage extends ValueFloat {
    public Voltage(String id, Float consumption) {
        super(id, consumption, "V");
    }

    public Voltage() {
        super(null, null, "V");
    }
}
