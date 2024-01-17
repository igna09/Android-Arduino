package com.example.carduino.settings.settingviewfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.carduino.R;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class IntegerSettingViewWrapper extends SettingViewWrapper<Integer> {

    @Override
    public void generateView(Setting setting, String label) {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, null);

        TextView integerSettingLabel = view.findViewById(R.id.integer_setting_label);
        integerSettingLabel.setText(label);

        if(setting != null) {
            EditText editText = view.findViewById(R.id.integer_setting_input);
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    Integer value = Integer.valueOf(editText.getText().toString());
                    setting.setValue(value);
                    onAction(value);
                }
            });
        }

        setView(view);
    }

    @Override
    public void updateView(Integer value) {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, null);

        EditText integerSettingInput = view.findViewById(R.id.integer_setting_input);

        integerSettingInput.setText(value.toString());
    }
}
