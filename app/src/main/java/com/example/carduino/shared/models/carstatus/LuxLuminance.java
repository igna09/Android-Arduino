package com.example.carduino.shared.models.carstatus;

public class LuxLuminance extends Value<Integer> {
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
