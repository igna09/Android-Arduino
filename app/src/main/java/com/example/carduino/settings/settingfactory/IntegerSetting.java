package com.example.carduino.settings.settingfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class IntegerSetting extends Setting<Integer> {
    public IntegerSetting() {
    }

    public IntegerSetting(String id, String label) {
        super(id, label);
    }

    @Override
    public void setValueFromString(String value) {
        this.setValue(Integer.parseInt(value));
    }

    @Override
    public View generateView() {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, null);

        TextView integerSettingLabel = view.findViewById(R.id.integer_setting_label);
        EditText integerSettingInput = view.findViewById(R.id.integer_setting_input);

        integerSettingLabel.setText(getLabel());
        integerSettingInput.setText(getValue().toString());

        return view;
    }

    @Override
    public void updateView() {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, null);

        EditText integerSettingInput = view.findViewById(R.id.integer_setting_input);

        integerSettingInput.setText(getValue().toString());
    }
}
