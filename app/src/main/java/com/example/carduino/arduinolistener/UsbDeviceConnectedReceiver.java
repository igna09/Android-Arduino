package com.example.carduino.arduinolistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.utilities.ArduinoServiceUtilities;
import com.example.carduino.shared.utilities.LoggerUtilities;

public class UsbDeviceConnectedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LoggerUtilities.logMessage("UsbDeviceConnectedReceiver", "received event " + intent.getAction());

        if (intent != null)
        {
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(intent.getAction())) {
                if(!ArduinoServiceUtilities.foregroundServiceRunning(context)) {
                    ArduinoServiceUtilities.startArduinoService(context);
                } else {
                    if(ArduinoSingleton.getInstance().getArduinoService() != null && !ArduinoSingleton.getInstance().getArduinoService().isConnected()) {
                        UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (device != null) {
                            ArduinoSingleton.getInstance().getArduinoService().attemptConnect(device.getDeviceId(), false);
                        }
                    }
                }
            }
        }
    }
}

