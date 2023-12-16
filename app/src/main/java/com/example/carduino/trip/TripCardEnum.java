package com.example.carduino.trip;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.trip.transformators.CardTransformator;
import com.example.carduino.trip.transformators.FloatTransformator;

public enum TripCardEnum {
    SPEED("SPEED", CarStatusEnum.SPEED, 0, 0, new FloatTransformator("0.0")),
    FUEL_CONSUMPTION("FUEL CONSUMPTION", CarStatusEnum.FUEL_CONSUMPTION, 0, 1, new FloatTransformator("0.0"));

    String label;
    Integer row;
    Integer column;
    CarStatusEnum carstatusEnum;
    CardTransformator transformator;

    TripCardEnum(String label, CarStatusEnum carstatusEnum, Integer row, Integer column) {
        this.label = label;
        this.row = row;
        this.column = column;
        this.carstatusEnum = carstatusEnum;
    }

    TripCardEnum(String label, CarStatusEnum carstatusEnum, Integer row, Integer column, CardTransformator transformator) {
        this.label = label;
        this.row = row;
        this.column = column;
        this.carstatusEnum = carstatusEnum;
        this.transformator = transformator;
    }
}
