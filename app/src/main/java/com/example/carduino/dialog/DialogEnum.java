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
    }, Duration.SHORT);

    private DialogInterface.OnClickListener positiveCallback;
    private DialogInterface.OnClickListener negativeCallback;
    private String message;
    private String title;
    private Duration duration;

    DialogEnum(String title, String message, DialogInterface.OnClickListener positiveCallback, DialogInterface.OnClickListener negativeCallback, Duration duration) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        this.title = title;
        this.message = message;
        this.duration = duration;
    }

    public DialogInterface.OnClickListener getPositiveCallback() {
        return positiveCallback;
    }

    public DialogInterface.OnClickListener getNegativeCallback() {
        return negativeCallback;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public Duration getDuration() {
        return duration;
    }
}
