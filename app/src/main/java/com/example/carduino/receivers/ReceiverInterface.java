package com.example.carduino.receivers;

import android.content.Context;

import com.example.carduino.shared.models.ArduinoMessage;

public interface ReceiverInterface {
    void execute(Context context, ArduinoMessage message);
}
