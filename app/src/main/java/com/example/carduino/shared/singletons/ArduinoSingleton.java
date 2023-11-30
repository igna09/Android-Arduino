package com.example.carduino.shared.singletons;

import com.example.carduino.shared.utilities.CircularArrayList;
import com.example.carduino.workers.ArduinoService;

public class ArduinoSingleton {
    private static ArduinoSingleton arduinoSingleton;

    private ArduinoService arduinoService;

    private CircularArrayList<String> circularArrayList;
    private final Integer bufferSize = 100;

    private ArduinoSingleton() {}

    public static ArduinoSingleton getInstance() {
        if(arduinoSingleton == null) {
            arduinoSingleton = new ArduinoSingleton();
        }
        return arduinoSingleton;
    }

    public ArduinoService getArduinoService() {
        return arduinoService;
    }

    public void setArduinoService(ArduinoService arduinoService) {
        this.arduinoService = arduinoService;
    }

    public CircularArrayList getCircularArrayList() {
        if(circularArrayList == null) {
            this.circularArrayList = new CircularArrayList(bufferSize);
        }
        return circularArrayList;
    }
}
