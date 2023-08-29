package com.example.carduino.settings.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class IntegerSetting extends Setting<Integer> {
    private View.OnFocusChangeListener onFocusChangeListener;

    public IntegerSetting(String id, Integer value, String unit) {
        super(unit, id, value, SettingsEnum.INTEGER);
    }

    public View.OnFocusChangeListener getOnFocusChangeListener() {
        return onFocusChangeListener;
    }

    public void setOnFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public View getView(View view, ViewGroup parent) {
        if (view == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.integer_setting, parent, false);
        }

        TextView integerSettingLabel = view.findViewById(R.id.integer_setting_label);
        EditText integerSettingInput = view.findViewById(R.id.integer_setting_input);

        integerSettingLabel.setText(getLabel());
        integerSettingInput.setText(getValue().toString());

        integerSettingInput.setOnFocusChangeListener(this.onFocusChangeListener);

        return view;
    }
}
