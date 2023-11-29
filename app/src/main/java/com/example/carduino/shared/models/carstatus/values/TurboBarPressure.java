package com.example.carduino.shared.models.carstatus.values;

public class TurboBarPressure extends BarPressure {
    public TurboBarPressure(String id, Float pressure) {
        super(id, pressure);
    }

    public TurboBarPressure() {
        super();
    }

    @Override
    public Float transormFunction(Float value) {
        if(value != null) {
            return (value - 1000) / 1000;
        } else {
            return super.transormFunction(value);
        }
    }
}
