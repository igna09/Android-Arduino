package com.example.carduino.settings.factory;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;

public class FloatSetting extends Setting {
    private Float value;

    public FloatSetting(String id, Float value, String unit) {
        super(unit, id, SettingsEnum.FLOAT);
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public View getView(View view) {
        TextView floatSettingLabel = view.findViewById(R.id.float_setting_input);
        EditText floatSettingInput = view.findViewById(R.id.float_setting_label);

        floatSettingLabel.setText(getLabel());
        floatSettingInput.setText(getValue().toString());

        return view;
    }
}
