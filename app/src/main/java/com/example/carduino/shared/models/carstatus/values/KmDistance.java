package com.example.carduino.shared.models.carstatus.values;

public class KmDistance extends ValueFloat {
    public KmDistance(String id, Float centimeters) {
        super(id, centimeters, "Km");
    }

    public KmDistance() {
        super(null, null, "Km");
    }
}
