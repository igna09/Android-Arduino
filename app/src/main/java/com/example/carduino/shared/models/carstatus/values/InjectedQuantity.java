package com.example.carduino.shared.models.carstatus.values;

public class InjectedQuantity extends Value<Float> {
    public InjectedQuantity(String id, Float iq) {
        super(id, iq, "mg/stroke");
    }

    public InjectedQuantity() {
        super(null, null, "mg/stroke");
    }

    @Override
    public Float parseValueFromString(String value) {
        return Float.parseFloat(value);
    }
}
