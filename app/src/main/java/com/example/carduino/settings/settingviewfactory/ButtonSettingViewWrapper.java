package com.example.carduino.settings.settingviewfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class ButtonSettingViewWrapper extends SettingViewWrapper<Boolean> {
    private CompoundButton.OnCheckedChangeListener listener;

    @Override
    public void generateView(Setting setting, String label) {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext().getForegroundActivity()).inflate(R.layout.button_setting, null);

        TextView booleanSettingLabel = view.findViewById(R.id.button_setting_label);
        booleanSettingLabel.setText(label);

        Button button = view.findViewById(R.id.button_setting_button);
        button.setOnClickListener(v -> {
            onAction(null);
        });

        setView(view);
    }

    @Override
    public void updateView(Boolean value) {

    }
}
