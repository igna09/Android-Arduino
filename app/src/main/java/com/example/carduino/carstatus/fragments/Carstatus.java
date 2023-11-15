package com.example.carduino.carstatus.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;
import com.example.carduino.carstatus.adapters.CarstatusAdapter;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.PropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.Value;
import com.example.carduino.shared.singletons.CarStatusSingleton;

import java.util.ArrayList;
import java.util.Map;

public class Carstatus extends Fragment {
    private ListView carstatusListView;
    PropertyChangeListener pcl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carstatus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carstatusListView = view.findViewById(R.id.carstatus_list);

        this.pcl = new PropertyChangeListener<Value>() {
            @Override
            public void onPropertyChange (String propertyName, Value oldValue, Value newValue){
                getActivity().runOnUiThread(() -> {
                    updateView();
                });
            }
        };
        CarStatusSingleton.getInstance().getCarStatus().addPropertyChangeListener(pcl);

        updateView();
    }

    public void updateView(){
        Map values = CarStatusSingleton.getInstance().getCarStatus().getCarStatusValues();
        if(values != null) {
            carstatusListView.setAdapter(new CarstatusAdapter(getActivity(), new ArrayList<>(values.values())));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarStatusSingleton.getInstance().getCarStatus().removePropertyChangeListener(pcl);
    }
}
