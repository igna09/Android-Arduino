package com.example.carduino.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carduino.shared.utilities.LoggerUtilities;

public class DialogActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent intent = getIntent();
            DialogEnum dialogEnum = DialogEnum.valueOf(intent.getStringExtra("DIALOG"));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(dialogEnum.getMessage());
            builder.setTitle(dialogEnum.getTitle());
            builder.setCancelable(false);
            if(dialogEnum.getPositiveCallback() != null) {
                builder.setPositiveButton("Yes", dialogEnum.getPositiveCallback());
            }
            if(dialogEnum.getNegativeCallback() != null) {
                builder.setNegativeButton("No", dialogEnum.getNegativeCallback());
            }
            builder.setOnDismissListener(dialogInterface -> {
                finish();
            });
            AlertDialog alertDialog = builder.create();
            runOnUiThread(() -> alertDialog.show());

            if(dialogEnum.getTimedDialogAction() != null) {
                Thread autoCloseDialogThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int leftDuration = dialogEnum.getTimedDialogAction().getDuration().getDuration();
                        while(leftDuration > 0) {
                            int finalLeftDuration = leftDuration;
                            runOnUiThread(() -> alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(String.format("Yes (%d)", finalLeftDuration)));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            leftDuration--;
                        }
                        if(alertDialog.isShowing()) {
                            alertDialog.dismiss();
                            runOnUiThread(() -> dialogEnum.getTimedDialogAction().getOnClickListener().onClick(alertDialog, -1));
                        }
                    }
                });
                autoCloseDialogThread.start();
            }
        } catch (Exception e) {
            LoggerUtilities.logException(e);
            finish();
        }
    }
}
