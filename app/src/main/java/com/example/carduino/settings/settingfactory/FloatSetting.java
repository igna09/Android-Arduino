package com.example.carduino.settings.settingfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

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
    public View generateView() {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.float_setting, null);

        TextView floatSettingLabel = view.findViewById(R.id.float_setting_label);
        EditText floatSettingInput = view.findViewById(R.id.float_setting_input);

        floatSettingLabel.setText(getLabel());
        floatSettingInput.setText(getValue().toString());

        this.setView(view);

        return view;
    }

    @Override
    public void updateView() {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.float_setting, null);

        EditText floatSettingInput = view.findViewById(R.id.float_setting_input);

        floatSettingInput.setText(getValue().toString());
    }
}
