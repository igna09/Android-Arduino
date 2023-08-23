package com.example.carduino.canbus.fragments;

import static android.content.Context.RECEIVER_NOT_EXPORTED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.carduino.R;

public class Canbus extends Fragment {
    private TextView displayTextView;
    private EditText editText;
    private Button sendBtn;
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canbus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getActivity().getApplicationContext();

        displayTextView = view.findViewById(R.id.diplayTextView);
        editText = view.findViewById(R.id.editText);
        sendBtn = view.findViewById(R.id.sendBtn);
        displayTextView.setMovementMethod(new ScrollingMovementMethod());
        displayTextView.setSingleLine(false);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextString = editText.getText().toString();
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
                if (message != null) {
                    Log.d("MainActivity", "received message from arduino " + message);
                    display(message);
                }
            }
        };
        getActivity().registerReceiver(receiver, filter, RECEIVER_NOT_EXPORTED);
    }

    public void display(final String message){
        getActivity().runOnUiThread(new Runnable() {
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
    public void onDestroy() {
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }
}
