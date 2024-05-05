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
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext().getForegroundActivity()).inflate(R.layout.integer_setting, null);

        if(setting != null) {
            EditText editText = view.findViewById(R.id.integer_setting_input);
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    try {
                        Integer value = Integer.valueOf(editText.getText().toString());
                        setting.setValue(value);
                        onAction(value);
                    } catch (NumberFormatException e) {

                    }
                }
            });
        }

        TextView integerSettingLabel = view.findViewById(R.id.integer_setting_label);
        integerSettingLabel.setText(label);

        setView(view);
    }

    @Override
    public void updateView(Integer value) {
        EditText integerSettingInput = this.getView().findViewById(R.id.integer_setting_input);
        integerSettingInput.setText(value != null ? value.toString() : "");
    }
}
