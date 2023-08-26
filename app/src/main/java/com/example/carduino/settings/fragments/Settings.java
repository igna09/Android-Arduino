package com.example.carduino.settings.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.R;
import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.ArduinoMessageViewModel;
import com.example.carduino.shared.models.Setting;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.SharedDataSingleton;

import java.util.HashMap;

/**
 * workflow:
 * android ({@link com.example.carduino.mainactivity.activities.MainActivity} asks for settings --> arduino can nodes responds for their settings --> android collects all settings in {@link com.example.carduino.shared.singletons.SharedDataSingleton} --> when opened {@link Settings} expose stored settings if any
 * automatic backlight sensor
 * Specchietto si abbassa con retro
 * Finestrino si apre con telecomando
 * Specchietti chiusura automatica (se selettore non in posizione chiusa)
 */
public class Settings extends Fragment {
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canbus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity().getApplicationContext();

        SettingsViewModel settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        settingsViewModel.getSettings().observe(requireActivity(), o -> {
            HashMap map = (HashMap<String, Setting>) o;
            //TODO: render settings
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
