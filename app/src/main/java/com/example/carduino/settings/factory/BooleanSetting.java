package com.example.carduino.settings.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class BooleanSetting extends Setting<Boolean> {
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

    public BooleanSetting(String id, Boolean value, String unit, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super(unit, id, value, SettingsEnum.BOOLEAN);
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public BooleanSetting(String id, Boolean value, String unit, String label, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super(unit, id, label, value, SettingsEnum.BOOLEAN);
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public BooleanSetting(String id, Boolean value, String unit) {
        this(id, value, unit, (CompoundButton.OnCheckedChangeListener) null);
    }

    public BooleanSetting(String id, Boolean value, String unit, String label) {
        this(id, value,unit,label, (CompoundButton.OnCheckedChangeListener) null);
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public View getView(View view, ViewGroup parent) {
        if (view == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            view = LayoutInflater.from(ContextsSingleton.getInstance().getMainActivityContext()).inflate(R.layout.boolean_setting, parent, false);
        }

        TextView booleanSettingLabel = view.findViewById(R.id.boolean_setting_label);
        SwitchCompat booleanSettingSwitch = view.findViewById(R.id.boolean_setting_switch);

        if(this.getLabel() == null) {
            booleanSettingLabel.setText("");
        } else {
            booleanSettingLabel.setText(getLabel());
        }
        booleanSettingSwitch.setChecked(getValue());

        booleanSettingSwitch.setOnCheckedChangeListener(this.onCheckedChangeListener);

        return view;
    }
}
