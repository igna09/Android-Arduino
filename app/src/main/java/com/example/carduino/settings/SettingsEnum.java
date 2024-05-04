package com.example.carduino.settings;

import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.settings.settingfactory.BooleanSetting;
import com.example.carduino.settings.settingfactory.IntegerSetting;
import com.example.carduino.settings.settingviewfactory.BooleanSettingViewWrapper;
import com.example.carduino.settings.settingviewfactory.ButtonSettingViewWrapper;
import com.example.carduino.settings.settingviewfactory.IntegerSettingViewWrapper;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.SharedDataSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public enum SettingsEnum {
    AUTO_BRIGHTNESS("Auto brightness", BooleanSetting.class, SettingType.APP, BooleanSettingViewWrapper.class),
    MAX_BRIGHTNESS("Max brightness", IntegerSetting.class, SettingType.APP, IntegerSettingViewWrapper.class),
    ON_REVERSE_LOWER_MIRRORS("Lower mirrors on reverse", BooleanSetting.class, SettingType.ARDUINO, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "ON_REVERSE_LOWER_MIRRORS", (Boolean) value ? "true" : "false"));
    }),
    AUTO_CLOSE_REARVIEW_MIRRORS("Auto close mirrors on turn off", BooleanSetting.class, SettingType.ARDUINO, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "AUTO_CLOSE_REARVIEW_MIRRORS", (Boolean) value ? "true" : "false"));
    }),
    ADVANCED_MODE("Advanced mode", BooleanSetting.class, SettingType.APP, BooleanSettingViewWrapper.class, (value) -> {
        SharedDataSingleton.getInstance().setAdvancedMode((Boolean) value);
    }),
    SWC_PAIR("SWC pairing", BooleanSetting.class, SettingType.ARDUINO, ButtonSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "SWC_PAIR", "true"));
    }),
    OTA_MODE("Enter OTA mode", BooleanSetting.class, SettingType.ARDUINO, BooleanSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "OTA_MODE", (Boolean) value ? "true" : "false"));
    }),
    RESTART("Restart all nodes", BooleanSetting.class, SettingType.ARDUINO, ButtonSettingViewWrapper.class, (value) -> {
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, "RESTART", "true"));
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

    SettingsEnum(String label, Class settingValueType, SettingType settingType, Class settingViewType, SettingCallback settingCallback) {
        this.settingValueType = settingValueType;
        this.label = label;
        this.settingViewType = settingViewType;
        this.settingCallback = settingCallback;
        this.settingType = settingType;
    }

    SettingsEnum(String label, Class settingValueType, SettingType settingType) {
        this(label, settingValueType, settingType, null, null);
    }

    SettingsEnum(String label, Class settingValueType, SettingType settingType, Class settingViewType) {
        this(label, settingValueType, settingType, settingViewType, null);
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
}
