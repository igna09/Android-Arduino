package com.example.carduino.receivers.canbus;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.example.carduino.receivers.canbus.factory.CanbusFactory;
import com.example.carduino.receivers.canbus.factory.CanbusInterface;

import java.util.Objects;

public class CanbusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CanbusInterface action = CanbusFactory.getCanbusInterface(intent.getAction());
        action.execute(context, intent.getStringExtra("message"));
    }
}
