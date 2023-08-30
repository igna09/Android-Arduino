package com.example.carduino.shared.models.carstatus;

import com.example.carduino.shared.models.carstatus.propertychangelisteners.InternalLuminanceCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.CelsiusTemperature;
import com.example.carduino.shared.models.carstatus.values.CmDistance;
import com.example.carduino.shared.models.carstatus.values.Duration;
import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.carstatus.values.KpaPressure;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.models.carstatus.values.Rpm;

public enum CarStatusEnum {
    EXTERNAL_TEMPERATURE("EXTERNAL_TEMPERATURE", Category.CAR, CelsiusTemperature.class),
    INTERNAL_TEMPERATURE("INTERNAL_TEMPERATURE", Category.CAR, CelsiusTemperature.class),
    SPEED("SPEED", Category.CAR, KmhSpeed.class),
    INTERNAL_LUMINANCE("INTERNAL_LUMINANCE", Category.CAR, LuxLuminance.class, InternalLuminanceCarStatusPropertyChangeListener.class),
    FRONT_DISTANCE("FRONT_DISTANCE", Category.CAR, CmDistance.class),
    ENGINE_WATER_COOLING_TEMPERATURE("ENGINE_WATER_COOLING_TEMPERATURE", Category.ENGINE, CelsiusTemperature.class),
    ENGINE_OIL_TEMPERATURE("ENGINE_OIL_TEMPERATURE", Category.ENGINE, CelsiusTemperature.class),
    ENGINE_INTAKE_MANIFOLD_PRESSURE("ENGINE_INTAKE_MANIFOLD_PRESSURE", Category.ENGINE, KpaPressure.class),
    ENGINE_RPM("ENGINE_RPM", Category.ENGINE, Rpm.class),
    TRIP_DURATION("TRIP_DURATION", Category.TRIP, Duration.class),
    TRIP_AVERAGE_SPEED("TRIP_AVERAGE_SPEED", Category.TRIP, KmhSpeed.class),
    TRIP_MAX_SPEED("TRIP_MAX_SPEED", Category.TRIP, KmhSpeed.class);

    private enum Category {
        ENGINE,
        TRIP,
        CAR;
    }

    private String id;
    private Category category;
    private Class type;
    private Class propertyChangeListener;

    CarStatusEnum(String id, Category category, Class type, Class propertyChangeListener) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = propertyChangeListener;
    }

    CarStatusEnum(String id, Category category, Class type) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = null;
    }

    public String getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public Class getType() {
        return type;
    }

    public Class getPropertyChangeListener() {
        return propertyChangeListener;
    }
}
