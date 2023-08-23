package com.example.carduino.receivers;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import java.util.Objects;

public class BrightnessReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Settings.System.canWrite(context.getApplicationContext())) {
            Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            startActivity(context.getApplicationContext(), settingsIntent, null);
        } else {
            Settings.System.putInt(context.getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("message"))));
        }
    }
}
