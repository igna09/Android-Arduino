package com.example.carduino.settings.factory;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;

public class IntegerSetting extends Setting {
    private Integer value;

    public IntegerSetting(String id, Integer value, String unit) {
        super(unit, id, SettingsEnum.INTEGER);
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public View getView(View view) {
        TextView integerSettingLabel = view.findViewById(R.id.integer_setting_label);
        EditText integerSettingInput = view.findViewById(R.id.integer_setting_input);

        integerSettingLabel.setText(getLabel());
        integerSettingInput.setText(getValue().toString());

        return view;
    }
}
