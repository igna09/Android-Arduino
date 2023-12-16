package com.example.carduino.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.carduino.shared.utilities.ArduinoServiceUtilities;
import com.example.carduino.shared.utilities.LoggerUtilities;
import com.example.carduino.services.ArduinoService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                LoggerUtilities.logMessage("BootReceiver::onReceive()", "received ACTION_BOOT_COMPLETED intent");
                if(!ArduinoServiceUtilities.foregroundServiceRunning(context)) {
                    ArduinoServiceUtilities.startArduinoService(context);
                }
            }
        } catch (Exception e) {
            LoggerUtilities.logException(e);
        }
    }
}
