package com.example.carduino.settings.settingfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting() {
    }

    public BooleanSetting(String id, String label) {
        super(id, label);
    }

    @Override
    public void setValueFromString(String value) {
        if(value != null) {
            this.setValue(value.equals("true"));
        } else {
            this.setValue(false);
        }
    }
}
