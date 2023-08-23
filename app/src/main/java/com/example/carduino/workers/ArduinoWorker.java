package com.example.carduino.workers;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.R;
import com.example.carduino.shared.models.ArduinoMessageViewModel;
import com.example.carduino.shared.singletons.MainActivitySingleton;

import java.util.Random;

import me.aflak.arduino.Arduino;

public class ArduinoWorker extends Worker implements me.aflak.arduino.ArduinoListener {
    private NotificationManager notificationManager;
    private Arduino arduino;
    private Context appContext;
    private BroadcastReceiver receiver;
    private ArduinoMessageViewModel arduinoMessageViewModel;

    public ArduinoWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);

        notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        arduino = new Arduino(context);
        arduino.setArduinoListener(this);

        appContext = context;

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.backgroundbrightness.SEND_ARDUINO_MESSAGE");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                assert message != null;
                Log.d("ArduinoWorker", "sending " + message);
                getApplicationContext().sendBroadcast(createIntent(ArduinoActions.CANBUS, ArduinoActions.SEND.toString(), message));
                arduino.send(message.getBytes());
            }
        };
        appContext.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);

        arduinoMessageViewModel = new ViewModelProvider(MainActivitySingleton.getInstance().getMainActivityContext()).get(ArduinoMessageViewModel.class);
    }

    @Override
    public Result doWork() {
        setForegroundAsync(createForegroundInfo());

        try {
            while(true) {
                if (this.isStopped()) {
                    Log.d("ArduinoWorker", "killing");
                    return Result.success();
                } else {
                    //simulating arduino message receive
                    onArduinoMessage(("BRIGHTNESS;" + new Random().nextInt(101)).getBytes());

                    Thread.sleep(5000);
                }
            }
        } catch (InterruptedException e) {
            return Result.failure();
        }
    }

    @Override
    public void onStopped() {
        try {
            Log.d("ArduinoWorker", "arduino worker stopped");
            arduino.close();
            arduino.unsetArduinoListener();
            appContext.unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e("ArduinoWorker", e.getMessage());
        }
        super.onStopped();
    }

    @NonNull
    private ForegroundInfo createForegroundInfo() {
        String channelName = "Channel Name";
        String channelId = "Channel Id";
        NotificationChannel  channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        String title = "Carduino running in background"; //context.getString(R.string.notification_title);
        String cancel = "STOP"; //context.getString(R.string.cancel_download);
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

        String[] keyValue = message.split(";");
        if(keyValue.length < 2) {
//            this.arduinoMessageViewModel.addMessage(new ArduinoMessage(ArduinoActions.LOGGER, message));
//            getApplicationContext().sendBroadcast(createIntent(ArduinoActions.LOGGER, message));
        } else {
//            this.arduinoMessageViewModel.addMessage(new ArduinoMessage(ArduinoActions.valueOf(keyValue[0]), keyValue[1]));
            getApplicationContext().sendBroadcast(createIntent(ArduinoActions.valueOf(keyValue[0]), keyValue[1], keyValue[2]));
//            if(ArduinoActions.valueOf(keyValue[0]) != ArduinoActions.LOGGER) {
//                this.arduinoMessageViewModel.addMessage(new ArduinoMessage(ArduinoActions.LOGGER, keyValue[0] + " --- " + keyValue[1]));
//                getApplicationContext().sendBroadcast(createIntent(ArduinoActions.LOGGER, keyValue[0] + " --- " + keyValue[1]));
//            }
        }
    }

    private Intent createIntent(ArduinoActions action, String key, String value) {
        Intent intent;
        if(action.getC() != null) {
            intent = new Intent(appContext, action.getC());
        } else {
            intent = new Intent();
        }
        intent.setAction(action.getAction());
        intent.putExtra("key", key);
        intent.putExtra("value", value);
        return intent;
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
