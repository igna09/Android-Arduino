package com.example.carduino.settings.settingfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting() {
    }

    public BooleanSetting(String id, String label) {
        super(id, label);
    }

    @Override
    public void setValueFromString(String value) {
        this.setValue(value.equals("true"));
    }

    @Override
    public View generateView() {
        View view = LayoutInflater.from(ContextsSingleton.getInstance().getMainActivityContext()).inflate(R.layout.boolean_setting, null);

        TextView booleanSettingLabel = view.findViewById(R.id.boolean_setting_label);
        SwitchCompat booleanSettingSwitch = view.findViewById(R.id.boolean_setting_switch);

        if(this.getLabel() == null) {
            booleanSettingLabel.setText(this.getId());
        } else {
            booleanSettingLabel.setText(getLabel());
        }
        booleanSettingSwitch.setChecked(getValue() != null && getValue());

        this.setView(view);

        return view;
    }

    @Override
    public void updateView() {
        SwitchCompat booleanSettingSwitch = this.getView().findViewById(R.id.boolean_setting_switch);
        booleanSettingSwitch.setChecked(getValue());
    }
}
