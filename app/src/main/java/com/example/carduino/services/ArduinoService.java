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

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.carduino.R;
import com.example.carduino.arduinolistener.Constants;
import com.example.carduino.arduinolistener.CustomProber;
import com.example.carduino.arduinolistener.SerialListener;
import com.example.carduino.arduinolistener.SerialSocket;
import com.example.carduino.arduinolistener.TextUtil;
import com.example.carduino.carduino.CarduinoActivity;
import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.receivers.canbus.factory.CanbusActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.singletons.CarStatusSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoMessageUtilities;
import com.example.carduino.shared.utilities.LoggerUtilities;
import com.hoho.android.usbserial.driver.SerialTimeoutException;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.ArrayDeque;

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
    private CarduinoActivity.Connected connected;

    private final BroadcastReceiver broadcastReceiver;

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
        connected = CarduinoActivity.Connected.True;
    }

    public void disconnect() {
        connected = CarduinoActivity.Connected.False;
        if(socket != null) {
            socket.disconnect();
            socket = null;
        }
    }

    /**
     * SerialListener
     */
    public void onSerialConnect() {
        if(connected == CarduinoActivity.Connected.True) {
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
        if(connected == CarduinoActivity.Connected.True) {
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
        if(connected == CarduinoActivity.Connected.True) {
            synchronized (this) {
                onArduinoMessage(data);
            }
        }
    }

    public void write(byte[] data) throws IOException {
        if(connected != CarduinoActivity.Connected.True)
            throw new IOException("not connected");
        socket.write(data);
    }

    public void onSerialIoError(Exception e) {
        if(connected == CarduinoActivity.Connected.True) {
            synchronized (this) {
//                if (listener != null) {
//                    mainLooper.post(() -> {
//                        if (listener != null) {
//                            listener.onSerialIoError(e);
//                        } else {
//                            queue1.add(new QueueItem(QueueType.IoError, e));
//                            disconnect();
//                        }
//                    });
//                } else {
//                    queue2.add(new QueueItem(QueueType.IoError, e));
//                    disconnect();
//                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

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
                    .setContentText("Service is running, expand me to stop")
                    .setContentTitle("Carduino service")
                    .setSmallIcon(R.drawable.baseline_directions_car_24)
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

        unregisterReceiver(broadcastReceiver);
        disconnect();

        super.onDestroy();
    }

    public void onArduinoMessage(byte[] bytes) {
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
    }

    public void sendMessageToArduino(String message) {
        LoggerUtilities.logArduinoMessage("sending", message);
        if(connected != CarduinoActivity.Connected.True) {
            LoggerUtilities.logMessage("not connected");
            return;
        }
        try {
            byte[] data;
            data = (message + TextUtil.newline_crlf).getBytes();
            this.write(data);
        } catch (SerialTimeoutException e) {
            LoggerUtilities.logMessage("write timeout: " + e.getMessage());
        } catch (Exception e) {
            onSerialIoError(e);
        }
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
            LoggerUtilities.logMessage("CarduinoActivity", "connection failed: device not found");
            return;
        }

        UsbSerialDriver driver = UsbSerialProber.getDefaultProber().probeDevice(device);
        if(driver == null) {
            driver = CustomProber.getCustomProber().probeDevice(device);
        }
        if(driver == null) {
            LoggerUtilities.logMessage("CarduinoActivity", "connection failed: no driver for device");
            return;
        }

        if(driver.getPorts().size() < portNum) {
            LoggerUtilities.logMessage("CarduinoActivity","connection failed: not enough ports at device");
            return;
        }
        UsbSerialPort usbSerialPort = driver.getPorts().get(portNum);

        UsbDeviceConnection usbConnection = usbManager.openDevice(driver.getDevice());
        if(usbConnection == null && (granted == null || granted == false) && !usbManager.hasPermission(driver.getDevice())) {
            int flags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_MUTABLE : 0;
            PendingIntent usbPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Constants.INTENT_ACTION_GRANT_USB), flags);
            usbManager.requestPermission(driver.getDevice(), usbPermissionIntent);
            return;
        }
        if(usbConnection == null) {
            if (!usbManager.hasPermission(driver.getDevice()))
                LoggerUtilities.logMessage("CarduinoActivity", "connection failed: permission denied");
            else
                LoggerUtilities.logMessage("CarduinoActivity", "connection failed: open failed");
            return;
        }

        try {
            usbSerialPort.open(usbConnection);
            try {
                usbSerialPort.setParameters(115200, UsbSerialPort.DATABITS_8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            } catch (UnsupportedOperationException e) {
                LoggerUtilities.logMessage("CarduinoActivity", "Setting serial parameters failed: " + e.getMessage());
            }
            SerialSocket socket = new SerialSocket(getApplicationContext(), usbConnection, usbSerialPort);
            ArduinoSingleton.getInstance().getArduinoService().connect(socket);
        } catch (Exception e) {
            ArduinoSingleton.getInstance().getArduinoService().onSerialConnectError(e);
        }
    }

    public Boolean isConnected() {
        return connected == CarduinoActivity.Connected.True;
    }
}
