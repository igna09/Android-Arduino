package com.example.carduino.shared;

import android.app.Application;

import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.FileSystemSingleton;
import com.example.carduino.shared.singletons.LoggerSingleton;
import com.example.carduino.shared.singletons.SettingsSingleton;
import com.example.carduino.shared.singletons.SharedDataSingleton;
import com.example.carduino.shared.singletons.TripSingleton;

public class MyApplication extends Application {
    private boolean activityVisible;

    private ArduinoSingleton arduinoSingleton;
    private LoggerSingleton loggerSingleton;
    private ContextsSingleton contextsSingleton;
    private FileSystemSingleton fileSystemSingleton;
    private SettingsSingleton settingsSingleton;
    private SharedDataSingleton sharedDataSingleton;
    private TripSingleton tripSingleton;

    @Override
    public void onCreate() {
        super.onCreate();

        arduinoSingleton = ArduinoSingleton.getInstance();
        fileSystemSingleton = FileSystemSingleton.getInstance();
        loggerSingleton = LoggerSingleton.getInstance();
        contextsSingleton = ContextsSingleton.getInstance();
        settingsSingleton = SettingsSingleton.getInstance();
        sharedDataSingleton = SharedDataSingleton.getInstance();
        tripSingleton = TripSingleton.getInstance();

        contextsSingleton.setApplicationContext(this);
    }

    public boolean isActivityVisible() {
        return activityVisible;
    }

    public void activityResumed() {
        activityVisible = true;
    }

    public void activityPaused() {
        activityVisible = false;
    }
}
