package com.example.carduino.shared.utilities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.example.carduino.services.ArduinoService;
import com.example.carduino.shared.singletons.ContextsSingleton;

public class ArduinoServiceUtilities {
    public static void startServiceIfNotStarted(Context context) {
        if(!foregroundServiceRunning(context)) {
            startArduinoService(context);
        }
    }

    public static void startArduinoService(Context context) {
        Intent serviceIntent = new Intent(context, ArduinoService.class);
        serviceIntent.setAction("START_FOREGROUND");
        context.startForegroundService(serviceIntent);
    }

    public static void stopForegroundService(Context context) {
        Intent stopIntent = new Intent(context, ArduinoService.class);
        stopIntent.setAction("STOP_FOREGROUND");
        context.startService(stopIntent);
    }

    public static boolean foregroundServiceRunning(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(ArduinoService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
