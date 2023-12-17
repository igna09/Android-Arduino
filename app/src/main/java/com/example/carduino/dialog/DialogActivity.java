package com.example.carduino.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

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
            builder.setPositiveButton("Yes", dialogEnum.getPositiveCallback());
            builder.setNegativeButton("No", dialogEnum.getNegativeCallback());
            builder.setOnDismissListener(dialogInterface -> {
                finish();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            LoggerUtilities.logException(e);
        }
    }
}
