package com.example.backgroundbrightness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

public class MainActivity extends AppCompatActivity {
    private OneTimeWorkRequest workRequest;
    private TextView displayTextView;
    private EditText editText;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getApplicationContext();

        setContentView(R.layout.activity_main);
        displayTextView = findViewById(R.id.diplayTextView);
        editText = findViewById(R.id.editText);
        sendBtn = findViewById(R.id.sendBtn);
        displayTextView.setMovementMethod(new ScrollingMovementMethod());
        displayTextView.setSingleLine(false);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextString =   editText.getText().toString();
                editText.getText().clear();

                Log.d("MainActivity", "Sent: " + editTextString);
            }
        });

        workRequest =
                new OneTimeWorkRequest.Builder(ArduinoWorker.class)
                        .addTag("arduino_worker")
                        .build();
        Operation operation = WorkManager.getInstance(context).enqueueUniqueWork("arduino_worker", ExistingWorkPolicy.KEEP, workRequest);

        WorkManager.getInstance(context)
                .getWorkInfosForUniqueWorkLiveData("arduino_worker")
                .observe(this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(@Nullable List<WorkInfo> workInfos) {
                        if (workInfos != null && !workInfos.isEmpty()) {
                            Data progress = workInfos.get(0).getProgress();
                            String value = progress.getString("OUTPUT");

                            if(value != null) {
                                Log.d("MainActivity", "received message from arduino " + value);
                                display(value);
                            }

                            if(workInfos.get(0).getState() == WorkInfo.State.CANCELLED) {
                                finish();
                            }
                        }
                    }
                });
    }

    public void display(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayTextView.append(message);
                displayTextView.append("\n");
            }
        });
    }
}
