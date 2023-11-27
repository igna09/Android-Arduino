package com.example.carduino.shared.singletons;

import com.example.carduino.shared.models.ArduinoMessage;
import com.example.carduino.shared.utilities.CircularArrayList;

public class ArduinoMessageSingleton {
    private static ArduinoMessageSingleton arduinoMessageSingleton;

    private CircularArrayList<String> circularArrayList;

    private final Integer bufferSize = 100;

    private ArduinoMessageSingleton(){}

    public static ArduinoMessageSingleton getInstance()
    {
        if (arduinoMessageSingleton == null)
        {
            arduinoMessageSingleton = new ArduinoMessageSingleton();
        }
        return arduinoMessageSingleton;
    }

    public CircularArrayList getCircularArrayList() {
        if(circularArrayList == null) {
            this.circularArrayList = new CircularArrayList(bufferSize);
        }
        return circularArrayList;
    }
}
