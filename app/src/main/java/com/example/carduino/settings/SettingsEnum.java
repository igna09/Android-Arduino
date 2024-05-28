package com.example.carduino.settings;

import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.settings.settingfactory.BooleanSetting;
import com.example.carduino.settings.settingfactory.IntegerSetting;
import com.example.carduino.settings.settingviewfactory.BooleanSettingViewWrapper;
import com.example.carduino.settings.settingviewfactory.ButtonSettingViewWrapper;
import com.example.carduino.settings.settingviewfactory.IntegerSettingViewWrapper;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.singletons.SharedDataSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

import java.util.Arrays;

public enum SettingsEnum {
    AUTO_BRIGHTNESS(null, "Auto brightness", BooleanSetting.class, SettingType.APP, BooleanSettingViewWrapper.class),
    MAX_BRIGHTNESS(null, "Max brightness", IntegerSetting.class, SettingType.APP, IntegerSettingViewWrapper.class),
    ON_REVERSE_LOWER_MIRRORS(0x04, "Lower mirrors on reverse", BooleanSetting.class, SettingType.ARDUINO, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, SettingsEnum.valueOf("ON_REVERSE_LOWER_MIRRORS").getId().toString(), (Boolean) value ? "true" : "false"));
    }),
    AUTO_CLOSE_REARVIEW_MIRRORS(0x00, "Auto close mirrors on turn off", BooleanSetting.class, SettingType.ARDUINO, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, SettingsEnum.valueOf("AUTO_CLOSE_REARVIEW_MIRRORS").getId().toString(), (Boolean) value ? "true" : "false"));
    }),
    ADVANCED_MODE(null, "Advanced mode", BooleanSetting.class, SettingType.APP, BooleanSettingViewWrapper.class, (value) -> {
        SharedDataSingleton.getInstance().setAdvancedMode((Boolean) value);
    }),
    SWC_PAIR(0x03, "SWC pairing", BooleanSetting.class, SettingType.ARDUINO, ButtonSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, SettingsEnum.valueOf("SWC_PAIR").getId().toString(), "true"));
    }),
    OTA_MODE(0x01, "Enter OTA mode", BooleanSetting.class, SettingType.ARDUINO, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, SettingsEnum.valueOf("OTA_MODE").getId().toString(), (Boolean) value ? "true" : "false"));
    }),
    RESTART(0x02, "Restart all nodes", BooleanSetting.class, SettingType.ARDUINO, ButtonSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, SettingsEnum.valueOf("RESTART").getId().toString(), "true"));
    });

    public enum SettingType {
        APP,
        ARDUINO
    }

    private String label;
    private Class settingValueType;
    private Class settingViewType;
    private SettingCallback settingCallback;
    private SettingType settingType;
    private Integer id;

    SettingsEnum(Integer id, String label, Class settingValueType, SettingType settingType, Class settingViewType, SettingCallback settingCallback) {
        this.id = id;
        this.settingValueType = settingValueType;
        this.label = label;
        this.settingViewType = settingViewType;
        this.settingCallback = settingCallback;
        this.settingType = settingType;
    }

    SettingsEnum(Integer id, String label, Class settingValueType, SettingType settingType) {
        this(id, label, settingValueType, settingType, null, null);
    }

    SettingsEnum(Integer id, String label, Class settingValueType, SettingType settingType, Class settingViewType) {
        this(id, label, settingValueType, settingType, settingViewType, null);
    }

    public String getLabel() {
        return label;
    }

    public Class getSettingValueType() {
        return settingValueType;
    }

    public Class getSettingViewType() {
        return settingViewType;
    }

    public SettingCallback getSettingCallback() {
        return settingCallback;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static Enum getEnumById(Integer id) {
        return Arrays.stream(SettingsEnum.values()).filter(carStatusEnum -> carStatusEnum.getId().equals(id)).findFirst().orElse(null);
    }
}
