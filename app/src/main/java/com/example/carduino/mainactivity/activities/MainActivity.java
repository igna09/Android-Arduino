package com.example.carduino.mainactivity.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.carduino.R;
import com.example.carduino.homepage.fragments.Homepage;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.Logger;
import com.example.carduino.shared.utilities.PermissionUtilities;
import com.example.carduino.workers.ArduinoService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContextsSingleton.getInstance().setMainActivityContext(this);
        ContextsSingleton.getInstance().setApplicationContext(getApplication());

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new Homepage()).commit();

        PermissionUtilities.requestMissingPermissions();
        if(!foregroundServiceRunning()) {
            Logger.getInstance().log("service not running");
            if(PermissionUtilities.haveAllPermissions()) {
                Logger.getInstance().log("application does have all permissions, start service");
                startArduinoService();
            } else {
                Logger.getInstance().log("application does not have all permissions, requesting them");
                PermissionUtilities.requestMissingPermissions();
            }
        }
    }

    void startArduinoService() {
        Intent serviceIntent = new Intent(this, ArduinoService.class);
        serviceIntent.setAction("START_FOREGROUND");
        startForegroundService(serviceIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(PermissionUtilities.haveAllPermissions()) {
            startArduinoService();
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

    public void stopForegroundService() {
        Intent stopIntent = new Intent(MainActivity.this, ArduinoService.class);
        stopIntent.setAction("STOP_FOREGROUND");
        startService(stopIntent);
    }
}