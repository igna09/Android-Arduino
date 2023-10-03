package com.example.carduino.carstatus.fragments;

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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.carduino.R;
import com.example.carduino.carstatus.adapters.CarstatusAdapter;
import com.example.carduino.settings.adapters.SettingAdapter;
import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.ArduinoMessageViewModel;
import com.example.carduino.shared.models.CarStatusViewModel;
import com.example.carduino.shared.models.SettingsViewModel;
import com.example.carduino.shared.models.carstatus.values.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Carstatus extends Fragment {
    private CarStatusViewModel carstatusViewModel;
    private Observer observer;
    private ListView carstatusListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carstatus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carstatusListView = view.findViewById(R.id.carstatus_list);

        carstatusViewModel = new ViewModelProvider(requireActivity()).get(CarStatusViewModel.class);
        observer = new Observer() {
            @Override
            public void onChanged(Object o) {
                display((HashMap<String, Value>) o);
            }
        };
        carstatusViewModel.getLiveDataCarStatus().observe(requireActivity(), observer);
    }

    public void display(HashMap<String, Value> values){
        if(values != null) {
            carstatusListView.setAdapter(new CarstatusAdapter(getActivity(), new ArrayList<>(values.values())));
        }
    }

    @Override
    public void onDestroy() {
        carstatusViewModel.getLiveDataCarStatus().removeObserver(observer);
        super.onDestroy();
    }
}
