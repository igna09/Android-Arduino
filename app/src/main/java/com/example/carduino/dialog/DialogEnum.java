package com.example.carduino.dialog;

import android.content.DialogInterface;

import com.example.carduino.shared.singletons.TripSingleton;

public enum DialogEnum {
    CONTINUE_LAST_TRIP("Continue trip", "Would you like to continue last trip session", (dialog, which) -> {
        try {
            TripSingleton.getInstance().restoreTrip();
            TripSingleton.getInstance().startTrip();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }, (dialog, which) -> {
        TripSingleton.getInstance().startTrip();
    });

    DialogInterface.OnClickListener positiveCallback;
    DialogInterface.OnClickListener negativeCallback;
    String message;
    String title;

    DialogEnum(String title, String message, DialogInterface.OnClickListener positiveCallback, DialogInterface.OnClickListener negativeCallback) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
    }
}
