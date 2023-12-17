package com.example.carduino.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carduino.shared.utilities.LoggerUtilities;

public class DialogActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        try {
            Intent intent = getIntent();
            DialogEnum dialogEnum = DialogEnum.valueOf(intent.getStringExtra("ACTION"));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(dialogEnum.message);
            builder.setTitle(dialogEnum.title);
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", dialogEnum.positiveCallback);
            builder.setNegativeButton("No", dialogEnum.negativeCallback);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            LoggerUtilities.logException(e);
        }
    }
}
