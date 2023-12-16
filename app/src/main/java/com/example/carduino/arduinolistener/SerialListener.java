package com.example.carduino.arduinolistener;

import java.util.ArrayDeque;

public interface SerialListener {
    void onSerialConnect      ();
    void onSerialRead         (byte[] data);                // socket -> service
    void onSerialIoError      (Exception e);
}
