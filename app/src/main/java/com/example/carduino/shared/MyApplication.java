package com.example.carduino.shared;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.FileSystemSingleton;
import com.example.carduino.shared.singletons.LoggerSingleton;
import com.example.carduino.shared.singletons.AppSwitchSingleton;
import com.example.carduino.shared.singletons.SettingsSingleton;
import com.example.carduino.shared.singletons.SharedDataSingleton;
import com.example.carduino.shared.singletons.TripSingleton;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    //should this be an array (using oncreate and onremove)?
    private Activity foregroundActivity;

    private ArduinoSingleton arduinoSingleton;
    private LoggerSingleton loggerSingleton;
    private ContextsSingleton contextsSingleton;
    private FileSystemSingleton fileSystemSingleton;
    private SettingsSingleton settingsSingleton;
    private SharedDataSingleton sharedDataSingleton;
    private TripSingleton tripSingleton;
    private AppSwitchSingleton appSwitchSingleton;

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
        appSwitchSingleton = AppSwitchSingleton.getInstance();

        contextsSingleton.setApplicationContext(this);

        registerActivityLifecycleCallbacks(this);
    }

    public Activity getForegroundActivity() {
        return foregroundActivity;
    }

    @Override
    public void onTrimMemory(int level) {
        String stringLevel = "";
        switch(level) {
            case TRIM_MEMORY_BACKGROUND:
                stringLevel = "TRIM_MEMORY_BACKGROUND";
                break;
            case TRIM_MEMORY_COMPLETE:
                stringLevel = "TRIM_MEMORY_COMPLETE";
                break;
            case TRIM_MEMORY_MODERATE:
                stringLevel = "TRIM_MEMORY_MODERATE";
                break;
            case TRIM_MEMORY_UI_HIDDEN:
                stringLevel = "TRIM_MEMORY_UI_HIDDEN";
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                stringLevel = "TRIM_MEMORY_RUNNING_LOW";
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                stringLevel = "TRIM_MEMORY_RUNNING_CRITICAL";
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                stringLevel = "TRIM_MEMORY_RUNNING_MODERATE";
                break;
        }
        LoggerSingleton.getInstance().log("MyApplication::onTrimMemory()" + " - " + stringLevel);
        super.onTrimMemory(level);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        this.foregroundActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        this.foregroundActivity = null;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
