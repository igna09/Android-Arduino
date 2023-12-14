package com.example.carduino.arduinolistener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.example.carduino.services.ArduinoService;
import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.utilities.ArduinoServiceUtilities;
import com.example.carduino.shared.utilities.LoggerUtilities;

public class ConnectReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(intent.getAction())) {
                if(!ArduinoServiceUtilities.foregroundServiceRunning(context)) {
                    ArduinoServiceUtilities.startArduinoService(context);
                } else {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if(device != null) {
                        ArduinoSingleton.getInstance().getArduinoService().connectDevice(device.getDeviceId(), false);
                    }
                }
            }
        } catch (Exception e) {
            LoggerUtilities.logException(e);
        }
    }
}
