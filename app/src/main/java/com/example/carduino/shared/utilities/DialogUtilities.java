package com.example.carduino.shared.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.carduino.dialog.DialogActivity;
import com.example.carduino.shared.singletons.ContextsSingleton;
import com.example.carduino.shared.singletons.TripSingleton;

public class DialogUtilities {
    public static Boolean isShowingDialog() {
        return ContextsSingleton.getInstance().getApplicationContext().getForegroundActivity().getLocalClassName().equals("dialog.DialogActivity");
    }
    public static void showContinueTripSession() {
        Intent intent = new Intent(ContextsSingleton.getInstance().getApplicationContext(), DialogActivity.class);
        intent.putExtra("DIALOG", "START_NEW_TRIP_IN_PLACE_OF_LAST_SESSION");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextsSingleton.getInstance().getApplicationContext().startActivity(intent);
    }
}
