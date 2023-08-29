package com.example.carduino.settings.factory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.example.carduino.R;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class FloatSetting extends Setting<Float> {
    private View.OnFocusChangeListener onFocusChangeListener;

    public FloatSetting(String id, Float value, String unit) {
        super(unit, id, value, SettingsEnum.FLOAT);
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
            view = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.float_setting, parent, false);
        }

        TextView floatSettingLabel = view.findViewById(R.id.float_setting_label);
        EditText floatSettingInput = view.findViewById(R.id.float_setting_input);

        floatSettingLabel.setText(getLabel());
        floatSettingInput.setText(getValue().toString());

        floatSettingInput.setOnFocusChangeListener(this.onFocusChangeListener);

        return view;
    }
}
