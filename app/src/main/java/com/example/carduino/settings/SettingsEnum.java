package com.example.carduino.settings;

import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.settings.settingfactory.BooleanSetting;
import com.example.carduino.settings.settingfactory.IntegerSetting;
import com.example.carduino.settings.settingviewfactory.BooleanSettingViewWrapper;
import com.example.carduino.settings.settingviewfactory.ButtonSettingViewWrapper;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public enum SettingsEnum {
    OTA_MODE("Enter ota mode", BooleanSetting.class, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "OTA_MODE", (Boolean) value ? "true" : "false"));
    }),
    RESTART("Restart all nodes", BooleanSetting.class, ButtonSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "RESTART", "true"));
    }),
    MAX_LUMINANCE("Maximum internal luminance", IntegerSetting.class),
    MIN_LUMINANCE("Minimum internal luminance", IntegerSetting.class),
    SWC_PAIR("SWC pairing", BooleanSetting.class, ButtonSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "SWC_PAIR", "true"));
    });

    private String label;
    private Class settingType;
    private Class settingViewType;
    private SettingCallback settingCallback;

    SettingsEnum(String label, Class settingType, Class settingViewType, SettingCallback settingCallback) {
        this.settingType = settingType;
        this.label = label;
        this.settingViewType = settingViewType;
        this.settingCallback = settingCallback;
    }

    SettingsEnum(String label, Class settingType) {
        this(label, settingType, null, null);
    }

    SettingsEnum(String label, Class settingType, Class settingViewType) {
        this(label, settingType, settingViewType, null);
    }

    public String getLabel() {
        return label;
    }

    public Class getSettingType() {
        return settingType;
    }

    public Class getSettingViewType() {
        return settingViewType;
    }

    public SettingCallback getSettingCallback() {
        return settingCallback;
    }
}
