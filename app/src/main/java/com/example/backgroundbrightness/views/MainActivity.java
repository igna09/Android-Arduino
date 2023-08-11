package com.example.backgroundbrightness.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.backgroundbrightness.R;
import com.example.backgroundbrightness.workers.ArduinoWorker;

public class MainActivity extends AppCompatActivity {
    private OneTimeWorkRequest workRequest;
    private TextView displayTextView;
    private EditText editText;
    private Button sendBtn;
    private BroadcastReceiver receiver;

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

                Intent intent = new Intent();
                intent.setAction("com.example.backgroundbrightness.SEND_ARDUINO_MESSAGE");
                intent.putExtra("message", editTextString);
                context.sendBroadcast(intent);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.backgroundbrightness.RECEIVED_ARDUINO_MESSAGE_LOGGER");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                if(message != null) {
                    Log.d("MainActivity", "received message from arduino " + message);
                    display(message);
                }
            }
        };
        registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED);

        workRequest =
                new OneTimeWorkRequest.Builder(ArduinoWorker.class)
                        .build();
        WorkManager.getInstance(context).enqueueUniqueWork("arduino_worker", ExistingWorkPolicy.REPLACE, workRequest);
    }

    public void display(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Boolean shouldScroll = displayTextView.getLayout().getLineTop(displayTextView.getLineCount()) == displayTextView.getHeight();

                displayTextView.append(message);
                displayTextView.append("\n");

                if(/*shouldScroll*/true) {
                    // find the amount we need to scroll.  This works by
                    // asking the TextView's internal layout for the position
                    // of the final line and then subtracting the TextView's height
                    final int scrollAmount = displayTextView.getLayout().getLineTop(displayTextView.getLineCount()) - displayTextView.getHeight();
                    // if there is no need to scroll, scrollAmount will be <=0
                    if (scrollAmount > 0)
                        displayTextView.scrollTo(0, scrollAmount);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
}
