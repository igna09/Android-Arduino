package com.example.carduino.shared.models.carstatus.values;

public class KmhSpeed extends ValueInteger {
    public KmhSpeed(String id, Integer speed) {
        super(id, speed, "Km/h");
    }

    public KmhSpeed() {
        super(null, null, "Km/h");
    }
}
