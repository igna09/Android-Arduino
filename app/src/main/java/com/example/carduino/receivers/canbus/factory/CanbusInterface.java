package com.example.carduino.receivers.canbus.factory;

import android.app.Application;
import android.content.Context;

public interface CanbusInterface {
    void execute(Context context, String message);
}
