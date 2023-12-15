package com.example.carduino.arduinolistener;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carduino.shared.singletons.ArduinoSingleton;
import com.example.carduino.shared.utilities.ArduinoServiceUtilities;

public class UsbEventReceiverActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null)
        {
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(intent.getAction())) {
                if(!ArduinoServiceUtilities.foregroundServiceRunning(getApplicationContext())) {
                    ArduinoServiceUtilities.startArduinoService(getApplicationContext());
                } else {
                    if(!ArduinoSingleton.getInstance().getArduinoService().isConnected()) {
                        UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if (device != null) {
                            ArduinoSingleton.getInstance().getArduinoService().connectDevice(device.getDeviceId(), false);
                        }
                    }
                }
            }
        }

        // Close the activity
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent != null) {
            UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if(device != null) {}
//                Toast.makeText(this, Integer.valueOf(device.getDeviceId()).toString(), Toast.LENGTH_SHORT).show();
//                connect(device.getDeviceId());
            else
                Toast.makeText(this, "no device", Toast.LENGTH_SHORT).show();
        }
        super.onNewIntent(intent);
    }
}
