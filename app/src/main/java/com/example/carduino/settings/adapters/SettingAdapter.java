package com.example.carduino.settings.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carduino.R;
import com.example.carduino.settings.factory.Setting;

import java.util.ArrayList;

public class SettingAdapter extends ArrayAdapter<Setting> {
    Context context;

    public SettingAdapter(@NonNull Context context, ArrayList<Setting> settingArrayList) {
        super(context, 0, settingArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;

        Setting setting = getItem(position);
        return setting.getView(listitemView, parent);
    }
}
