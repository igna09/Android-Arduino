package com.example.carduino.carstatus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carduino.R;
import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.ContextsSingleton;

import java.util.ArrayList;

public class CarstatusAdapter extends ArrayAdapter<Value> {
    Context context;

    public CarstatusAdapter(@NonNull Context context, ArrayList<Value> settingArrayList) {
        super(context, 0, settingArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Value carstatusValue = getItem(position);

        if (convertView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            convertView = LayoutInflater.from(ContextsSingleton.getInstance().getApplicationContext()).inflate(R.layout.carstatus_value, parent, false);
        }

        TextView carstatus_label = convertView.findViewById(R.id.carstatus_label);
        TextView carstatus_value = convertView.findViewById(R.id.carstatus_value);
        TextView carstatus_unit = convertView.findViewById(R.id.carstatus_unit);

        carstatus_label.setText(carstatusValue.getId());
        carstatus_value.setText(carstatusValue.getValue() != null ? carstatusValue.getValue().toString() : "-");
        carstatus_unit.setText(carstatusValue.getUnit());

        return convertView;
    }
}
