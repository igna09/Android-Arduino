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
        if(value != null) {
            this.setValue(Float.parseFloat(value));
        } else {
            this.setValue(null);
        }
    }
}
