package com.example.carduino.settings.settingviewfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class FloatSettingViewWrapper extends SettingViewWrapper<Float> {

    @Override
    public void generateView(Setting setting, String label) {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.float_setting, null);

        TextView floatSettingLabel = view.findViewById(R.id.float_setting_label);
        floatSettingLabel.setText(label);

        if(setting != null) {
            EditText editText = view.findViewById(R.id.float_setting_input);
            editText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    Float value = Float.valueOf(editText.getText().toString());
                    setting.setValue(value);
                    onAction(value);
                }
            });
        }

        setView(view);
    }

    @Override
    public void updateView(Float value) {
        EditText floatSettingInput = this.getView().findViewById(R.id.float_setting_input);
        floatSettingInput.setText(value != null ? value.toString() : "");
    }
}
