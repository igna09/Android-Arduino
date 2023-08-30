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
import com.example.carduino.shared.models.CarStatusViewModel;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.IntentUtilities;

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
        filter.addAction(context.getString(R.string.arduino_send_message));
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                assert message != null;
                Log.d("ArduinoWorker", "sending " + message);
                arduino.send(message.getBytes());
            }
        };
        appContext.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);

        /*CarStatusViewModel carStatusViewModel = new ViewModelProvider(ContextsSingleton.getInstance().getMainActivityContext()).get(CarStatusViewModel.class);
        carStatusViewModel.getLiveDataCarStatus().observe(ContextsSingleton.getInstance().getMainActivityContext(), o -> {
            Log.d("ArduinoWorker", carStatusViewModel.getCarStatus().toString());
        });*/

//        arduinoMessageViewModel = new ViewModelProvider(ContextsSingleton.getInstance().getMainActivityContext()).get(ArduinoMessageViewModel.class);
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
                    onArduinoMessage(("CANBUS;CAR_STATUS;INTERNAL_LUMINANCE-" + new Random().nextInt(5001) + ";;").getBytes());
                    onArduinoMessage(("CANBUS;CAR_STATUS;ENGINE_WATER_COOLING_TEMPERATURE-" + new Random().nextFloat() * 120 + ";;").getBytes());
                    onArduinoMessage(("CANBUS;READ_SETTING;auto_close_rearview_mirror-BOOLEAN-true-;;").getBytes());
                    onArduinoMessage(("CANBUS;READ_SETTING;test_integer-INTEGER-10-;;").getBytes());
                    onArduinoMessage(("CANBUS;READ_SETTING;test_float-FLOAT-12.3-;;").getBytes());
                    onArduinoMessage(("CANBUS;CAR_STATUS;ENGINE_RPM-" + new Random().nextInt(5001) + ";;").getBytes());

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

    /**
     * Every message received from Arduino MUST be in this format:
     * ArduinoActions;key;value;unit;
     * eg.: CANBUS;BRIGHTNESS;2700;lux;
     * @param bytes
     */
    @Override
    public void onArduinoMessage(byte[] bytes) {
        String message = new String(bytes);
        Log.d("ArduinoWorker", "arduino message: " + message);

        IntentUtilities.sendArduinoMessageBroadcast(message);
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
