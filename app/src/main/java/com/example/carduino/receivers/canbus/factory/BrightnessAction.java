package com.example.carduino.receivers.canbus.factory;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Objects;

public class BrightnessAction implements CanbusInterface {
    @Override
    public void execute(Context context, String message) {
        if(!Settings.System.canWrite(context.getApplicationContext())) {
            Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(context.getApplicationContext(), settingsIntent, null);
        } else {
            Settings.System.putInt(context.getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    Integer.parseInt(message));
        }
    }
}
