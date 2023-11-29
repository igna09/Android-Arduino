package com.example.carduino.settings.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.PropertyChangeListener;
import com.example.carduino.shared.singletons.SettingsSingleton;

/**
 * workflow:
 * android fragment settings asks for settings --> arduino can nodes responds for their settings --> android collects all settings in {@link com.example.carduino.shared.singletons.SharedDataSingleton} --> when opened {@link Settings} expose stored settings if any
 * automatic backlight sensor
 * Specchietto si abbassa con retro
 * Finestrino si apre con telecomando
 * Specchietti chiusura automatica (se selettore non in posizione chiusa)
 */
public class Settings extends Fragment {
    private PropertyChangeListener pcl;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout settingsList = (LinearLayout) view.findViewById(R.id.settings_list);

        SettingsSingleton.getInstance().getSettings().getSettings().values().forEach(setting -> {
            if(setting.getView() != null) {
                ViewGroup parent = (ViewGroup) setting.getView().getParent();
                if (parent != null) {
                    parent.removeView(setting.getView());
                }
                getActivity().runOnUiThread(() -> {
                    settingsList.addView(setting.getView());
                });
            }
        });

        pcl = new PropertyChangeListener<Setting>() {
            @Override
            public void onPropertyChange(String propertyName, Setting oldValue, Setting newValue) {
                if(newValue.getView() == null) {
                    View v = newValue.generateView();
                    getActivity().runOnUiThread(() -> {
                        settingsList.addView(v);
                    });
                }
                getActivity().runOnUiThread(() -> {
                    newValue.updateView();
                });
            }
        };
        SettingsSingleton.getInstance().getSettings().addPropertyChangeListener(pcl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SettingsSingleton.getInstance().getSettings().removePropertyChangeListener(pcl);
    }
}
