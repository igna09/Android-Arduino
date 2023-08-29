package com.example.carduino.settings.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.R;
import com.example.carduino.canbus.fragments.Canbus;
import com.example.carduino.homepage.adapters.HomepageGridButtonAdapter;
import com.example.carduino.homepage.models.HomepageGridButtonModel;
import com.example.carduino.settings.adapters.SettingAdapter;
import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.singletons.SharedDataSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * workflow:
 * android ({@link com.example.carduino.mainactivity.activities.MainActivity} asks for settings --> arduino can nodes responds for their settings --> android collects all settings in {@link com.example.carduino.shared.singletons.SharedDataSingleton} --> when opened {@link Settings} expose stored settings if any
 * automatic backlight sensor
 * Specchietto si abbassa con retro
 * Finestrino si apre con telecomando
 * Specchietti chiusura automatica (se selettore non in posizione chiusa)
 */
public class Settings extends Fragment {
    private SettingsViewModel settingsViewModel;
    private Observer observer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        observer = new Observer() {
            @Override
            public void onChanged(Object o) {
                updateList(view, (HashMap<String, Setting>) o);
            }
        };
        settingsViewModel.getSettings().observe(requireActivity(), observer);

        updateList(view, (Map) settingsViewModel.getSettings().getValue());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        settingsViewModel.getSettings().removeObserver(observer);
    }

    private void updateList(View view, Map settings) {
        if(settings != null) {
            ListView listView = view.findViewById(R.id.settings_list);

            SettingAdapter adapter = new SettingAdapter(getActivity(), new ArrayList<>(settings.values()));
            listView.setAdapter(adapter);
        }
    }
}
