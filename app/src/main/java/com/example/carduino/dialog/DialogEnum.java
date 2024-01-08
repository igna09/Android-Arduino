package com.example.carduino.dialog;

import android.content.DialogInterface;

import com.example.carduino.shared.singletons.TripSingleton;

public enum DialogEnum {
    CONTINUE_LAST_TRIP("Restart trip", "Would you like to start new trip in place of restoring last session?", (dialog, which) -> {
                TripSingleton.getInstance().startTrip();
            },
            null,
            new TimedDialogAction(Duration.SHORT, (dialog, which) -> {
                try {
                    TripSingleton.getInstance().restoreTrip();
                    TripSingleton.getInstance().startTrip();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));

    private DialogInterface.OnClickListener positiveCallback;
    private DialogInterface.OnClickListener negativeCallback;
    private String message;
    private String title;
    private TimedDialogAction timedDialogAction;

    DialogEnum(String title, String message, DialogInterface.OnClickListener positiveCallback, DialogInterface.OnClickListener negativeCallback, TimedDialogAction timedDialogAction) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        this.title = title;
        this.message = message;
        this.timedDialogAction = timedDialogAction;
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

    public TimedDialogAction getTimedDialogAction() {
        return timedDialogAction;
    }
}
