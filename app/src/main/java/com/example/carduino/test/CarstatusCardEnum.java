package com.example.carduino.test;

import com.example.carduino.shared.models.carstatus.CarStatusEnum;

public enum CarstatusCardEnum {
    SPEED("SPEED", CarStatusEnum.SPEED, 0, 0),
    RPM("RPM", CarStatusEnum.ENGINE_RPM, 0, 1),
    MANIFOLD_PRESSURE("MANIFOLD PRESSURE", CarStatusEnum.ENGINE_INTAKE_MANIFOLD_PRESSURE, 0, 2),
    COOLING_WATER("COOLING WATER", CarStatusEnum.ENGINE_WATER_COOLING_TEMPERATURE, 0, 3),
    FUEL_CONSUMPTION("FUEL CONSUMPTION", CarStatusEnum.FUEL_CONSUMPTION_KM_L, 1, 0),
    EXTERNAL_TEMPERATURE("EXTERNAL TEMPERATURE", CarStatusEnum.EXTERNAL_TEMPERATURE, 1, 2),
    INTERNAL_TEMPERATURE("INTERNAL TEMPERATURE", CarStatusEnum.INTERNAL_TEMPERATURE, 1, 3);

    String label;
    Integer row;
    Integer column;
    CarStatusEnum carstatusEnum;

    CarstatusCardEnum(String label, CarStatusEnum carstatusEnum, Integer row, Integer column) {
        this.label = label;
        this.row = row;
        this.column = column;
        this.carstatusEnum = carstatusEnum;
    }
}
