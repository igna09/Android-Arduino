package com.example.carduino.settings.settingfactory;

import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class IntegerSetting extends Setting<Integer> {
    public IntegerSetting() {
    }

    public IntegerSetting(String id, String label) {
        super(id, label);
    }

    @Override
    public void setValueFromString(String value) {
        if(value != null) {
            this.setValue(Integer.parseInt(value));
        } else {
            this.setValue(null);
        }
    }
}
