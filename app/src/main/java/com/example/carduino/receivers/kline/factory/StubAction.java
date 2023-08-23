package com.example.carduino.receivers.kline.factory;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.example.carduino.receivers.ReceiverInterface;
import com.example.carduino.shared.models.ArduinoMessage;

public class StubAction implements ReceiverInterface {
    @Override
    public void execute(Context context, ArduinoMessage message) {
        // TODO: action here
    }
}
