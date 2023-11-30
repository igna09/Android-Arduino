package com.example.carduino.settings.settingviewfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class IntegerSettingViewWrapper extends SettingViewWrapper<Integer> {

    @Override
    public void generateView(String label) {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, null);

        TextView integerSettingLabel = view.findViewById(R.id.integer_setting_label);
        integerSettingLabel.setText(label);

        setView(view);
    }

    @Override
    public void updateView(Integer value) {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, null);

        EditText integerSettingInput = view.findViewById(R.id.integer_setting_input);

        integerSettingInput.setText(value.toString());
    }
}
