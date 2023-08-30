package com.example.carduino.receivers.kline.factory;

import android.content.Context;

import com.example.carduino.receivers.ArduinoMessageExecutorInterface;
import com.example.carduino.shared.models.ArduinoMessage;

public class StubAction implements ArduinoMessageExecutorInterface {
    @Override
    public void execute(Context context, ArduinoMessage message) {
        // TODO: action here
    }
}
