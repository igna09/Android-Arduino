package com.example.carduino.shared.singletons;

import com.example.carduino.shared.utilities.CircularArrayList;
import com.example.carduino.services.ArduinoService;

public class ArduinoSingleton {
    private static ArduinoSingleton instance;

    private ArduinoService arduinoService;

    private CircularArrayList<String> circularArrayList;
    private final Integer bufferSize = 100;

    private ArduinoSingleton() {}

    public static ArduinoSingleton getInstance() {
        if(instance == null) {
            instance = new ArduinoSingleton();
        }
        return instance;
    }

    public static void invalidate() {
        instance = null;
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
