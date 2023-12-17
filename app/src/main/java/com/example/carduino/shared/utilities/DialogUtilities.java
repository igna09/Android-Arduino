package com.example.carduino.shared.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.TripSingleton;

public class DialogUtilities {
    public static void showContinueTripSession() {
        Intent intent = new Intent("com.example.carduino.DIALOG");
        intent.putExtra("ACTION", "CONTINUE_LAST_TRIP");
        ContextsSingleton.getInstance().getApplicationContext().startActivity(intent);
    }
}
