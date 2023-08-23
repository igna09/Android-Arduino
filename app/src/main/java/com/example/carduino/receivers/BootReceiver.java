package com.example.carduino.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.carduino.workers.ArduinoWorker;

public class BootReceiver extends BroadcastReceiver {

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
