package com.example.carduino.mainactivity.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.carduino.R;
import com.example.carduino.homepage.fragments.Homepage;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.workers.ArduinoService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextsSingleton.getInstance().setMainActivityContext(this);
        ContextsSingleton.getInstance().setApplicationContext(getApplication());

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new Homepage()).commit();

        if(!foregroundServiceRunning()) {
            Intent serviceIntent = new Intent(this, ArduinoService.class);
            startForegroundService(serviceIntent);
        }
    }

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(ArduinoService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}