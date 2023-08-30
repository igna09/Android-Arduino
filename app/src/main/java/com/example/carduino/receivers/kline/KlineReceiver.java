package com.example.carduino.receivers.kline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.receivers.kline.factory.KlineFactory;
import com.example.carduino.shared.models.ArduinoMessage;

public class KlineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ArduinoMessageExecutorInterface action = KlineFactory.getKlineInterface(intent.getStringExtra("key"));
        if(action != null) {
            action.execute(context, new ArduinoMessage(intent));
        }
    }
}