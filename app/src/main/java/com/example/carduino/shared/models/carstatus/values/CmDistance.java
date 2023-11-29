package com.example.carduino.shared.models.carstatus.values;

public class CmDistance extends ValueFloat {
    public CmDistance(String id, Float centimeters) {
        super(id, centimeters, "cm");
    }

    public CmDistance() {
        super(null, null, "cm");
    }
}
