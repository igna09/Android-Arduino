package com.example.carduino.receivers.canbus.factory;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.example.carduino.receivers.ReceiverInterface;
import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.shared.models.ArduinoMessage;

import java.util.Objects;

public class BrightnessAction implements ReceiverInterface {
    @Override
    public void execute(Context context, ArduinoMessage message) {
        if(!Settings.System.canWrite(context.getApplicationContext())) {
            Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(context.getApplicationContext(), settingsIntent, null);
        } else {
            Settings.System.putInt(context.getApplicationContext().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS,
                    map(Integer.parseInt(message.getValue()), 0, 5000, 0, 100));
        }
    }

    Integer map(Integer x, Integer in_min, Integer in_max, Integer out_min, Integer out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
