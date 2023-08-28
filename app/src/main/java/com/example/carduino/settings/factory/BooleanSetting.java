package com.example.carduino.settings.factory;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;

public class BooleanSetting extends Setting {
    private Boolean value;

    public BooleanSetting(String id, Boolean value, String unit) {
        super(unit, id, SettingsEnum.BOOLEAN);
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public View getView(View view) {
        TextView booleanSettingLabel = view.findViewById(R.id.boolean_setting_label);
        SwitchCompat booleanSettingSwitch = view.findViewById(R.id.boolean_setting_switch);

        booleanSettingLabel.setText(getLabel());
        booleanSettingSwitch.setChecked(getValue());

        return view;
    }
}
