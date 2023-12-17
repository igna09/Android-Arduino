package com.example.carduino.shared.models.carstatus;

import com.example.carduino.shared.models.carstatus.propertychangelisteners.FuelConsumptionCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.InternalLuminanceCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.RpmCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.SpeedCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.CelsiusTemperature;
import com.example.carduino.shared.models.carstatus.values.CmDistance;
import com.example.carduino.shared.models.carstatus.values.FuelConsumptionKmL;
import com.example.carduino.shared.models.carstatus.values.InjectedQuantity;
import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.carstatus.values.BarPressure;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.models.carstatus.values.Rpm;
import com.example.carduino.shared.models.carstatus.values.Voltage;
import com.example.carduino.shared.models.trip.tripvalue.FloatTripValue;
import com.example.carduino.shared.models.trip.tripvalue.IntegerTripValue;

import java.util.Arrays;

public enum CarStatusEnum {
    EXTERNAL_TEMPERATURE("EXTERNAL_TEMPERATURE", Category.CAR, CelsiusTemperature.class),
    INTERNAL_TEMPERATURE("INTERNAL_TEMPERATURE", Category.CAR, CelsiusTemperature.class),
    SPEED("SPEED", Category.CAR, KmhSpeed.class, SpeedCarStatusPropertyChangeListener.class, IntegerTripValue.class),
    INTERNAL_LUMINANCE("INTERNAL_LUMINANCE", Category.CAR, LuxLuminance.class/*, InternalLuminanceCarStatusPropertyChangeListener.class*/),
    FRONT_DISTANCE("FRONT_DISTANCE", Category.CAR, CmDistance.class),
    ENGINE_WATER_COOLING_TEMPERATURE("ENGINE_WATER_COOLING_TEMPERATURE", Category.ENGINE, CelsiusTemperature.class/*, EngineWaterCoolingTemperatureCarStatusPropertyChangeListener.class*/),
    ENGINE_OIL_TEMPERATURE("ENGINE_OIL_TEMPERATURE", Category.ENGINE, CelsiusTemperature.class),
    ENGINE_INTAKE_MANIFOLD_PRESSURE("ENGINE_INTAKE_MANIFOLD_PRESSURE", Category.ENGINE, BarPressure.class),
    ENGINE_RPM("ENGINE_RPM", Category.ENGINE, Rpm.class, RpmCarStatusPropertyChangeListener.class),
    //TRIP_DURATION("TRIP_DURATION", Category.TRIP, Duration.class),
    TRIP_AVERAGE_SPEED("TRIP_AVERAGE_SPEED", Category.TRIP, KmhSpeed.class),
    TRIP_MAX_SPEED("TRIP_MAX_SPEED", Category.TRIP, KmhSpeed.class),
    INJECTED_QUANTITY("INJECTED_QUANTITY", Category.CAR, InjectedQuantity.class),
    FUEL_CONSUMPTION("FUEL_CONSUMPTION", Category.CAR, FuelConsumptionKmL.class, FuelConsumptionCarStatusPropertyChangeListener.class, FloatTripValue.class),
    BATTERY_VOLTAGE("BATTERY_VOLTAGE", Category.CAR, Voltage.class);

    private enum Category {
        ENGINE,
        TRIP,
        CAR;
    }

    private String id;
    private Category category;
    private Class type;
    private Class propertyChangeListener;
    private Class tripType;

    CarStatusEnum(String id, Category category, Class type, Class propertyChangeListener, Class tripType) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = propertyChangeListener;
        this.tripType = tripType;
    }

    CarStatusEnum(String id, Category category, Class type, Class propertyChangeListener) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = propertyChangeListener;
        this.tripType = null;
    }

    CarStatusEnum(String id, Category category, Class type) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = null;
        this.tripType = null;
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

    public static CarStatusEnum getCarStatusEnumById(String id) {
        return Arrays.stream(CarStatusEnum.values()).filter(carStatusEnum -> carStatusEnum.getId().equals(id)).findFirst().orElse(null);
    }

    public Class getTripType() {
        return tripType;
    }
}
