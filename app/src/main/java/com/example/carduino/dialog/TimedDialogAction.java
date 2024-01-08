package com.example.carduino.dialog;

import android.content.DialogInterface;

public class TimedDialogAction {
    private Duration duration;
    private DialogInterface.OnClickListener onClickListener;

    public TimedDialogAction(Duration duration, DialogInterface.OnClickListener onClickListener) {
        this.duration = duration;
        this.onClickListener = onClickListener;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public DialogInterface.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(DialogInterface.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
