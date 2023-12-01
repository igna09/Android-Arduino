package com.example.carduino.settings.settingfactory;

import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class FloatSetting extends Setting<Float> {
    public FloatSetting() {
    }

    public FloatSetting(String id, String label) {
        super(id, label);
    }

    @Override
    public void setValueFromString(String value) {
        this.setValue(Float.parseFloat(value));
    }

    @Override
    public void onValueChange(Float newValue) {
        super.onValueChange(newValue);
        ArduinoMessageUtilities.sendArduinoMessage(new ArduinoMessage(CanbusActions.WRITE_SETTING, getId(), getValue().toString()));
    }
}
