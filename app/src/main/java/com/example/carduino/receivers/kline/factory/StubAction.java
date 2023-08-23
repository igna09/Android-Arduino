package com.example.carduino.receivers.kline.factory;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class StubAction implements KlineInterface {
    @Override
    public void execute(Context context, String message) {
        // TODO: action here
    }
}
