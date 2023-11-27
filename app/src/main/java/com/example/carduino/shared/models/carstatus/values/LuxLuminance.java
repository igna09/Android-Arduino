package com.example.carduino.shared.models.carstatus.values;

public class LuxLuminance extends ValueInteger {
    public LuxLuminance(String id, Integer luminance) {
        super(id, luminance, "lux");
    }
    public LuxLuminance() {
        super(null, null, "lux");
    }

    @Override
    public Integer parseValueFromString(String value) {
        return Integer.parseInt(value);
    }
}
