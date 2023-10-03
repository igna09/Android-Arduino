package com.example.carduino.shared.models.carstatus;

import com.example.carduino.shared.models.carstatus.propertychangelisteners.EngineWaterCoolingTemperatureCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.InternalLuminanceCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.CelsiusTemperature;
import com.example.carduino.shared.models.carstatus.values.CmDistance;
import com.example.carduino.shared.models.carstatus.values.Duration;
import com.example.carduino.shared.models.carstatus.values.KmhSpeed;
import com.example.carduino.shared.models.carstatus.values.KpaPressure;
import com.example.carduino.shared.models.carstatus.values.LuxLuminance;
import com.example.carduino.shared.models.carstatus.values.Rpm;

import java.util.Arrays;

public enum CarStatusEnum {
    EXTERNAL_TEMPERATURE("EXTERNAL_TEMPERATURE", "0x01", Category.CAR, CelsiusTemperature.class),
    INTERNAL_TEMPERATURE("INTERNAL_TEMPERATURE", "0x02", Category.CAR, CelsiusTemperature.class),
    SPEED("SPEED", "0x03", Category.CAR, KmhSpeed.class),
    INTERNAL_LUMINANCE("INTERNAL_LUMINANCE", "0x04", Category.CAR, LuxLuminance.class, InternalLuminanceCarStatusPropertyChangeListener.class),
    FRONT_DISTANCE("FRONT_DISTANCE", "0x05", Category.CAR, CmDistance.class),
    ENGINE_WATER_COOLING_TEMPERATURE("ENGINE_WATER_COOLING_TEMPERATURE", "0x06", Category.ENGINE, CelsiusTemperature.class/*, EngineWaterCoolingTemperatureCarStatusPropertyChangeListener.class*/),
    ENGINE_OIL_TEMPERATURE("ENGINE_OIL_TEMPERATURE", "0x07", Category.ENGINE, CelsiusTemperature.class),
    ENGINE_INTAKE_MANIFOLD_PRESSURE("ENGINE_INTAKE_MANIFOLD_PRESSURE", "0x08", Category.ENGINE, KpaPressure.class),
    ENGINE_RPM("ENGINE_RPM", "0x09", Category.ENGINE, Rpm.class),
    TRIP_DURATION("TRIP_DURATION", "0x0A", Category.TRIP, Duration.class),
    TRIP_AVERAGE_SPEED("TRIP_AVERAGE_SPEED", "0x0B", Category.TRIP, KmhSpeed.class),
    TRIP_MAX_SPEED("TRIP_MAX_SPEED", "0x0C", Category.TRIP, KmhSpeed.class);

    private enum Category {
        ENGINE,
        TRIP,
        CAR;
    }

    private String id;
    private Category category;
    private Class type;
    private Class propertyChangeListener;
    private String messageId;

    CarStatusEnum(String id, String messageId, Category category, Class type, Class propertyChangeListener) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = propertyChangeListener;
        this.messageId = messageId;
    }

    CarStatusEnum(String id, String messageId, Category category, Class type) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.propertyChangeListener = null;
        this.messageId = messageId;
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

    public String getMessageId() {
        return messageId;
    }

    public static CarStatusEnum getCarStatusEnumByMessageId(String messageId) {
        return Arrays.stream(CarStatusEnum.values()).filter(carStatusEnum -> carStatusEnum.getMessageId().equals(messageId)).findFirst().orElse(null);
    }

    public static CarStatusEnum getCarStatusEnumById(String id) {
        return Arrays.stream(CarStatusEnum.values()).filter(carStatusEnum -> carStatusEnum.getId().equals(id)).findFirst().orElse(null);
    }
}
