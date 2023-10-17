package com.example.carduino.test;

import static android.content.Context.RECEIVER_NOT_EXPORTED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.carduino.shared.models.ArduinoActions;
import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.models.ArduinoMessageViewModel;

import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;

public class Test extends Fragment implements ArduinoListener {
    private TextView displayTextView;
    private EditText editText;
    private Button sendBtn;
    private Arduino arduino;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_canbus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayTextView = view.findViewById(R.id.diplayTextView);
        editText = view.findViewById(R.id.editText);
        sendBtn = view.findViewById(R.id.sendBtn);
        displayTextView.setMovementMethod(new ScrollingMovementMethod());
        displayTextView.setSingleLine(false);

        arduino = new Arduino(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        arduino.setBaudRate(115200);
        arduino.addVendorId(6790);
        arduino.setArduinoListener(this);

        UsbManager usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        display(usbManager.getDeviceList().toString());
    }

    public void display(final String message){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Boolean shouldScroll = displayTextView.getLayout().getLineTop(displayTextView.getLineCount()) == displayTextView.getHeight();

                displayTextView.append(message);
                displayTextView.append("\n");

                /*if(/*shouldScroll*true) {
                    // find the amount we need to scroll.  This works by
                    // asking the TextView's internal layout for the position
                    // of the final line and then subtracting the TextView's height
                    final int scrollAmount = displayTextView.getLayout().getLineTop(displayTextView.getLineCount()) - displayTextView.getHeight();
                    // if there is no need to scroll, scrollAmount will be <=0
                    if (scrollAmount > 0)
                        displayTextView.scrollTo(0, scrollAmount);
                }*/
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        arduino.unsetArduinoListener();
        arduino.close();

    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        display("Arduino attached!");
        arduino.open(device);
    }

    @Override
    public void onArduinoDetached() {
        display("Arduino detached");
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {
        display("> "+new String(bytes));
    }

    @Override
    public void onArduinoOpened() {
        String str = "Hello World !";
        arduino.send(str.getBytes());
    }

    @Override
    public void onUsbPermissionDenied() {
        display("Permission denied... New attempt in 3 sec");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arduino.reopen();
            }
        }, 3000);
    }
}
