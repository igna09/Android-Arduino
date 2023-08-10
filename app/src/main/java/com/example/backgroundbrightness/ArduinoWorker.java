package com.example.backgroundbrightness;

import static android.content.Context.NOTIFICATION_SERVICE;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ForegroundInfo;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Random;

import me.aflak.arduino.Arduino;

public class ArduinoWorker extends Worker implements me.aflak.arduino.ArduinoListener {
    private NotificationManager notificationManager;
    private Arduino arduino;
    private Context appContext;

    public ArduinoWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);

        notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        arduino = new Arduino(context);
        arduino.setArduinoListener(this);

        appContext = context;
    }

    @Override
    public Result doWork() {
        setForegroundAsync(createForegroundInfo("in corso"));

        try {
            while(true) {
                if (isStopped()) {
                    Log.d("ArduinoWorker", "killing");
                    return Result.success();
                } else {
                    //simulating arduino message receive
                    onArduinoMessage(Integer.toString(new Random().nextInt(101)).getBytes());

                    Thread.sleep(5000);
                }
            }
        } catch (InterruptedException e) {
            return Result.failure();
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.d("ArduinoWorker", "arduino worker stopped");
        arduino.close();
        arduino.unsetArduinoListener();
    }

    @NonNull
    private ForegroundInfo createForegroundInfo(@NonNull String progress) {
        String channelName = "Channel Name";
        String channelId = "Channel Id";
        NotificationChannel  channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        String title = "Notification Title"; //context.getString(R.string.notification_title);
        String cancel = "Cancel"; //context.getString(R.string.cancel_download);
        // This PendingIntent can be used to cancel the worker
        PendingIntent intent = WorkManager.getInstance(appContext)
                .createCancelPendingIntent(getId());

        Notification notification = new NotificationCompat.Builder(appContext, channel.getId())
                .setContentTitle(title)
                .setTicker(title)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true)
                // Add the cancel action to the notification which can
                // be used to cancel the worker
                .addAction(android.R.drawable.ic_delete, cancel, intent)
                .build();

        return new ForegroundInfo(0, notification);
    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        Log.d("ArduinoWorker", "arduino attached");
        arduino.open(device);
    }

    @Override
    public void onArduinoDetached() {
        Log.d("ArduinoWorker", "arduino detached");
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {
        String message = new String(bytes);
        Log.d("ArduinoWorker", "arduino message: " + message);

        if(!Settings.System.canWrite(getApplicationContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(getApplicationContext(), intent, null);
        } else {
            Settings.System.putInt(appContext.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS, Integer.parseInt(message));
        }

        setProgressAsync(new Data.Builder().putString("OUTPUT", message).build());
    }

    @Override
    public void onArduinoOpened() {
        String str = "arduino opened...";
        arduino.send(str.getBytes());
        Log.d("ArduinoWorker", str);
    }

    @Override
    public void onUsbPermissionDenied() {
        Log.d("ArduinoWorker", "Permission denied. Attempting again in 3 sec...");

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arduino.reopen();
            }
        }, 3000);
    }
}
