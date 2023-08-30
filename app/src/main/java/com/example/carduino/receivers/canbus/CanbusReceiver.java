package com.example.carduino.receivers.canbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.receivers.canbus.factory.CanbusFactory;
import com.example.carduino.shared.models.ArduinoMessage;

public class CanbusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ArduinoMessageExecutorInterface action = CanbusFactory.getCanbusInterface(intent.getStringExtra("key"));
        if(action != null) {
            action.execute(context, new ArduinoMessage(intent));
        }
    }
}
