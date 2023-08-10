package com.example.backgroundbrightness;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import me.aflak.arduino.Arduino;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Runs when bootup has completed.
            if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Our WorkManager will now run our task in a background thread.
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(ArduinoWorker.class)
                    .build();
            WorkManager.getInstance(context).enqueueUniqueWork("arduino_worker", ExistingWorkPolicy.KEEP, oneTimeWorkRequest);
        }
    }
}
