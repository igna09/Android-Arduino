package com.example.carduino.homepage.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.carduino.R;
import com.example.carduino.canbus.fragments.Canbus;
import com.example.carduino.carstatus.fragments.Carstatus;
import com.example.carduino.homepage.adapters.HomepageGridButtonAdapter;
import com.example.carduino.homepage.models.HomepageGridButtonModel;
import com.example.carduino.settings.fragments.Settings;

import java.util.ArrayList;

public class Homepage extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = view.findViewById(R.id.gridview);
        ArrayList<HomepageGridButtonModel> courseModelArrayList = new ArrayList<>();

        courseModelArrayList.add(new HomepageGridButtonModel("CAN bus", R.drawable.ic_launcher_foreground, Canbus.class));
        courseModelArrayList.add(new HomepageGridButtonModel("Settings", R.drawable.ic_launcher_foreground, Settings.class));
        courseModelArrayList.add(new HomepageGridButtonModel("Car Status", R.drawable.ic_launcher_foreground, Carstatus.class));

        HomepageGridButtonAdapter adapter = new HomepageGridButtonAdapter(getActivity(), courseModelArrayList);
        gridView.setAdapter(adapter);
    }
}