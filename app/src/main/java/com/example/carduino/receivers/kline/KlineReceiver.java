package com.example.carduino.receivers.kline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.carduino.receivers.canbus.factory.CanbusFactory;
import com.example.carduino.receivers.canbus.factory.CanbusInterface;
import com.example.carduino.receivers.kline.factory.KlineFactory;
import com.example.carduino.receivers.kline.factory.KlineInterface;

public class KlineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        KlineInterface action = KlineFactory.getKlineInterface(intent.getStringExtra("key"));
        action.execute(context, intent.getStringExtra("value"));
    }
}