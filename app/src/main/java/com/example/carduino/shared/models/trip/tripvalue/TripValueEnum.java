package com.example.carduino.shared.models.trip.tripvalue;

public enum TripValueEnum {
    FUEL_CONSUMPTION(FloatTripValue.class),
    SPEED(IntegerTripValue.class);

    private Class clazz;

    TripValueEnum(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }
}
