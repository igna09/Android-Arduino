package com.example.carduino.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.carduino.R;
import com.example.carduino.arduinolistener.Constants;
import com.example.carduino.arduinolistener.CustomProber;
import com.example.carduino.arduinolistener.SerialListener;
import com.example.carduino.arduinolistener.SerialSocket;
import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;
import com.example.carduino.shared.utilities.LoggerUtilities;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.ArrayDeque;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

public class ArduinoService extends Service implements SerialListener/*ArduinoListener*/ {
//    private Arduino arduino;
    private CarStatusSingleton carstatusSingleton;
    private ArduinoSingleton arduinoSingleton;
    private ContextsSingleton contextsSingleton;

    class SerialBinder extends Binder {
        ArduinoService getService() { return ArduinoService.this; }
    }

    private enum QueueType {Connect, ConnectError, Read, IoError}

    private static class QueueItem {
        QueueType type;
        ArrayDeque<byte[]> datas;
        Exception e;

        QueueItem(QueueType type) { this.type=type; if(type==QueueType.Read) init(); }
        QueueItem(QueueType type, Exception e) { this.type=type; this.e=e; }
        QueueItem(QueueType type, ArrayDeque<byte[]> datas) { this.type=type; this.datas=datas; }

        void init() { datas = new ArrayDeque<>(); }
        void add(byte[] data) { datas.add(data); }
    }

    private final Handler mainLooper;
    private final IBinder binder;
    private final ArrayDeque<QueueItem> queue1, queue2;
    private final QueueItem lastRead;

    private SerialSocket socket;
    private SerialListener listener;
    private boolean connected;

    private final BroadcastReceiver broadcastReceiver;

    private class ArduinoRunnable implements  Runnable {
        Integer counter = 0;
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
//                            Log.e("Service", "Service is running...");
//                            Logger.getInstance().log("Service is running...");
                try {
//                    long s = getIntegerRandomNumber(1, 10) * 1000;
//                    Log.d("sleep", "Sleeping for " + s);
//                    Thread.sleep(s);
//                    onArduinoMessage(("CAR_STATUS;SPEED;" + getIntegerRandomNumber(0, 200)).getBytes());
//                    onArduinoMessage(("CAR_STATUS;FUEL_CONSUMPTION;" + getFloatRandomNumber(0, 10)).getBytes());
//                    onArduinoMessage(("CAR_STATUS;ENGINE_INTAKE_MANIFOLD_PRESSURE;" + getFloatRandomNumber(1000, 2500)).getBytes());
//                    onArduinoMessage(("CAR_STATUS;BATTERY_VOLTAGE;" + getFloatRandomNumber(11, 14)).getBytes());
//                    onArduinoMessage(("READ_SETTING;OTA_MODE;true;").getBytes());
//                    if(counter >= 5)
//                        onArduinoMessage(("CAR_STATUS;ENGINE_RPM;" + getIntegerRandomNumber(900, 4000)).getBytes());
//                    if(counter % 5 == 0) {
//                        if(counter % 2 == 0) {
//                            onArduinoMessage("MEDIA_CONTROL;VOLUME_UP;0;".getBytes());
//                        } else {
//                            onArduinoMessage("MEDIA_CONTROL;VOLUME_DOWN;0;".getBytes());
//                        }
//                    }
                    Thread.sleep(1000);
                    counter++;
                } catch (InterruptedException e) {
                    LoggerUtilities.logException(e);
                    return;
                }
            }
            LoggerUtilities.logMessage("ArduinoService", "service stopped");
        }

        public Integer getIntegerRandomNumber(int min, int max) {
            return (int) ((Math.random() * (max - min)) + min);
        }

        public Float getFloatRandomNumber(int min, int max) {
            return (float) ((Math.random() * (max - min)) + min);
        }
    }

    private static Thread t;
    private static PowerManager.WakeLock wakeLock;
    private Integer deviceIdToConnect;

    public ArduinoService() {
        mainLooper = new Handler(Looper.getMainLooper());
        binder = new SerialBinder();
        queue1 = new ArrayDeque<>();
        queue2 = new ArrayDeque<>();
        lastRead = new QueueItem(QueueType.Read);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Constants.INTENT_ACTION_GRANT_USB.equals(intent.getAction())) {
                    Boolean granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                    connectDevice(deviceIdToConnect, granted);
                }
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * Api
     */
    public void connect(SerialSocket socket) throws IOException {
        socket.connect(this);
        this.socket = socket;
        connected = true;
    }

    public void disconnect() {
        connected = false; // ignore data,errors while disconnecting
//        cancelNotification();
        if(socket != null) {
            socket.disconnect();
            socket = null;
        }
    }

    public void write(byte[] data) throws IOException {
        if(!connected)
            throw new IOException("not connected");
        socket.write(data);
    }

    public void attach(SerialListener listener) {
        if(Looper.getMainLooper().getThread() != Thread.currentThread())
            throw new IllegalArgumentException("not in main thread");
        cancelNotification();
        // use synchronized() to prevent new items in queue2
        // new items will not be added to queue1 because mainLooper.post and attach() run in main thread
        synchronized (this) {
            this.listener = listener;
        }
        for(QueueItem item : queue1) {
            switch(item.type) {
                case Connect:       listener.onSerialConnect      (); break;
                case ConnectError:  listener.onSerialConnectError (item.e); break;
                case Read:          listener.onSerialRead         (item.datas); break;
                case IoError:       listener.onSerialIoError      (item.e); break;
            }
        }
        for(QueueItem item : queue2) {
            switch(item.type) {
                case Connect:       listener.onSerialConnect      (); break;
                case ConnectError:  listener.onSerialConnectError (item.e); break;
                case Read:          listener.onSerialRead         (item.datas); break;
                case IoError:       listener.onSerialIoError      (item.e); break;
            }
        }
        queue1.clear();
        queue2.clear();
    }

    public void detach() {
        if(connected) {}
//            createNotification();
        // items already in event queue (posted before detach() to mainLooper) will end up in queue1
        // items occurring later, will be moved directly to queue2
        // detach() and mainLooper.post run in the main thread, so all items are caught
        listener = null;
    }

    private void createNotification() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(Constants.NOTIFICATION_CHANNEL, "Background service", NotificationManager.IMPORTANCE_LOW);
            nc.setShowBadge(false);
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.createNotificationChannel(nc);
        }
        Intent disconnectIntent = new Intent()
                .setAction(Constants.INTENT_ACTION_DISCONNECT);
        Intent restartIntent = new Intent()
                .setClassName(this, Constants.INTENT_CLASS_MAIN_ACTIVITY)
                .setAction(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER);
        int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent disconnectPendingIntent = PendingIntent.getBroadcast(this, 1, disconnectIntent, flags);
        PendingIntent restartPendingIntent = PendingIntent.getActivity(this, 1, restartIntent,  flags);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(socket != null ? "Connected to "+socket.getName() : "Background Service")
                .setContentIntent(restartPendingIntent)
                .setOngoing(true)
                .addAction(new NotificationCompat.Action(R.drawable.ic_clear_white_24dp, "Disconnect", disconnectPendingIntent));
        // @drawable/ic_notification created with Android Studio -> New -> Image Asset using @color/colorPrimaryDark as background color
        // Android < API 21 does not support vectorDrawables in notifications, so both drawables used here, are created as .png instead of .xml
        Notification notification = builder.build();
        startForeground(Constants.NOTIFY_MANAGER_START_FOREGROUND_SERVICE, notification);*/
    }

    private void cancelNotification() {
//        stopForeground(true);
    }

    /**
     * SerialListener
     */
    public void onSerialConnect() {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialConnect();
                        } else {
                            queue1.add(new QueueItem(QueueType.Connect));
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.Connect));
                }
            }
        }
    }

    public void onSerialConnectError(Exception e) {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialConnectError(e);
                        } else {
                            queue1.add(new QueueItem(QueueType.ConnectError, e));
                            disconnect();
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.ConnectError, e));
                    disconnect();
                }
            }
        }
    }

    public void onSerialRead(ArrayDeque<byte[]> datas) { throw new UnsupportedOperationException(); }

    /**
     * reduce number of UI updates by merging data chunks.
     * Data can arrive at hundred chunks per second, but the UI can only
     * perform a dozen updates if receiveText already contains much text.
     *
     * On new data inform UI thread once (1).
     * While not consumed (2), add more data (3).
     */
    public void onSerialRead(byte[] data) {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    boolean first;
                    synchronized (lastRead) {
                        first = lastRead.datas.isEmpty(); // (1)
                        lastRead.add(data); // (3)
                    }
                    if(first) {
                        mainLooper.post(() -> {
                            ArrayDeque<byte[]> datas;
                            synchronized (lastRead) {
                                datas = lastRead.datas;
                                lastRead.init(); // (2)
                            }
                            if (listener != null) {
                                listener.onSerialRead(datas);
                            } else {
                                queue1.add(new QueueItem(QueueType.Read, datas));
                            }
                        });
                    }
                } else {
                    if(queue2.isEmpty() || queue2.getLast().type != QueueType.Read)
                        queue2.add(new QueueItem(QueueType.Read));
                    queue2.getLast().add(data);
                }

                onArduinoMessage(data);
            }
        }
    }

    public void onSerialIoError(Exception e) {
        if(connected) {
            synchronized (this) {
                if (listener != null) {
                    mainLooper.post(() -> {
                        if (listener != null) {
                            listener.onSerialIoError(e);
                        } else {
                            queue1.add(new QueueItem(QueueType.IoError, e));
                            disconnect();
                        }
                    });
                } else {
                    queue2.add(new QueueItem(QueueType.IoError, e));
                    disconnect();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        arduino = new Arduino(this);
//        arduino.setBaudRate(115200);
//        arduino.addVendorId(6790);
//        arduino.addVendorId(0); //just for local test
//        arduino.setArduinoListener(this);



        this.carstatusSingleton = CarStatusSingleton.getInstance();
        this.carstatusSingleton.getCarStatus();

        this.arduinoSingleton = ArduinoSingleton.getInstance();
        this.arduinoSingleton.setArduinoService(this);
        this.arduinoSingleton.getCircularArrayList();

        this.contextsSingleton = ContextsSingleton.getInstance();
        this.contextsSingleton.setServiceContext(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("STOP_FOREGROUND")) {
            LoggerUtilities.logMessage("service", "Stopping");

            t.interrupt();

            stopForeground(true);
            stopSelfResult(startId);

            if(ContextsSingleton.getInstance().getApplicationContext() != null) {
                ContextsSingleton.getInstance().getMainActivityContext().finishAffinity();
            }

            if(wakeLock.isHeld()){
                wakeLock.release();
            }

            return START_NOT_STICKY;
        } else if(intent == null || (intent != null && intent.getAction() == null || (intent.getAction().equals("START_FOREGROUND")))) {
            LoggerUtilities.logMessage("service", "Starting");

            t = new Thread(new ArduinoRunnable());
            t.start();

            final String CHANNELID = "Foreground Service ID";
            NotificationChannel channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );

            Intent stopIntent = new Intent(this, ArduinoService.class);
            stopIntent.setAction("STOP_FOREGROUND");

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNELID)
                    .setContentText("Service is running")
                    .setContentTitle("Service enabled")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    // Add the cancel action to the notification which can
                    // be used to cancel the worker
                    .addAction(android.R.drawable.ic_delete, "STOP", PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE));

            startForeground(1001, notification.build());

            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "Carduino::MyWakelockTag");
            wakeLock.acquire();

            registerReceiver(broadcastReceiver, new IntentFilter(Constants.INTENT_ACTION_GRANT_USB));

            super.onStartCommand(intent, flags, startId);
            return Service.START_STICKY_COMPATIBILITY;
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        LoggerUtilities.logMessage("service", "onDestroy()");
        if(t != null) {
            t.interrupt();
        }

        unregisterReceiver(broadcastReceiver);
//        arduino.close();
//        cancelNotification();
        disconnect();
        super.onDestroy();
    }

    /*@Override
    public void onArduinoAttached(UsbDevice device) {
        LoggerUtilities.logMessage("ArduinoService", "arduino attached");
        arduino.open(device);
    }

    @Override
    public void onArduinoDetached() {
        LoggerUtilities.logMessage("ArduinoService", "arduino detached");
    }

    *//**
     * Every message received from Arduino MUST be in this format:
     * CanbusActions;payload;
     * eg.: CAR_STATUS;BRIGHTNESS;2700;
//     * @param bytes
     *//*
    @Override
    */public void onArduinoMessage(byte[] bytes) {
        try {
            String message = new String(bytes);
            LoggerUtilities.logArduinoMessage("ArduinoService",  "receiving " + message);
            this.arduinoSingleton.getCircularArrayList().add(message);

            String[] splittedMessage = ArduinoMessageUtilities.parseArduinoMessage(message.trim());

            if (splittedMessage.length == 3) {
                boolean existsAction = true;
                try {
                    CanbusActions.valueOf(splittedMessage[0]);
                } catch (IllegalArgumentException e) {
                    existsAction = false;
                }
                if(existsAction) {
                    ArduinoMessage arduinoMessage = new ArduinoMessage(CanbusActions.valueOf(splittedMessage[0]), splittedMessage[1], splittedMessage[2]);
                    ArduinoMessageExecutorInterface action = null;
                    action = (ArduinoMessageExecutorInterface) arduinoMessage.getAction().getClazz().newInstance();
                    action.execute(arduinoMessage);
                }
            } else {
                LoggerUtilities.logArduinoMessage("ArduinoService", "malformed message");
            }
        } catch (Exception e) {
            LoggerUtilities.logException(e);
        }
    }/*

    @Override
    public void onArduinoOpened() {
        String str = "arduino opened...";
//        arduino.send(str.getBytes());
        LoggerUtilities.logMessage("ArduinoService", str);
    }

    @Override
    public void onUsbPermissionDenied() {
        LoggerUtilities.logMessage("ArduinoService",  "Permission denied. Attempting again in 3 sec...");

        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arduino.reopen();
            }
        }, 3000);
    }*/

    public void sendMessageToArduino(String message) {
        LoggerUtilities.logArduinoMessage("sending", message);
//        this.arduino.send(message.getBytes());
    }

    public void connectDevice(Integer deviceId, Boolean granted) {
        deviceIdToConnect = deviceId;
        Integer portNum = null;
        UsbDevice device = null;
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        for(UsbDevice v : usbManager.getDeviceList().values())
            if(v.getDeviceId() == deviceId) {
                device = v;
                portNum = 0;
            }
        if(device == null) {
            Log.d("CarduinoActivity", "connection failed: device not found");
            return;
        }

        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        if(driver == null) {
            driver = CustomProber.getCustomProber().probeDevice(device);
        }
        if(driver == null) {
            Log.d("CarduinoActivity", "connection failed: no driver for device");
            return;
        }

        if(driver.getPorts().size() < portNum) {
            Log.d("CarduinoActivity","connection failed: not enough ports at device");
            return;
        }
        UsbSerialPort usbSerialPort = driver.getPorts().get(portNum);

        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
        Boolean permissionGranted = null;
        if(usbConnection == null && permissionGranted == null && !usbManager.hasPermission(driver.getDevice())) {
            int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_MUTABLE : 0;
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Constants.INTENT_ACTION_GRANT_USB), flags);
            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
            return;
        }
        if(usbConnection == null) {
            if (!usbManager.hasPermission(driver.getDevice()))
                Log.d("CarduinoActivity", "connection failed: permission denied");
            else
                Log.d("CarduinoActivity", "connection failed: open failed");
            return;
        }

        try {
            usbSerialPort.open(usbConnection);
            try {
                usbSerialPort.setParameters(115200, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            } catch (UnsupportedOperationException e) {
                Log.d("CarduinoActivity", "Setting serial parameters failed: " + e.getMessage());
            }
            SerialSocket socket = new SerialSocket(getApplicationContext(), usbConnection, usbSerialPort);
            ArduinoSingleton.getInstance().getArduinoService().connect(socket);
            // usb connect is not asynchronous. connect-success and connect-error are returned immediately from socket.connect
            // for consistency to bluetooth/bluetooth-LE app use same SerialListener and SerialService classes
//            onSerialConnect();
        } catch (Exception e) {
            ArduinoSingleton.getInstance().getArduinoService().onSerialConnectError(e);
        }
    }
}
