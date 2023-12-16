package com.example.carduino.shared.singletons;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carduino.services.ArduinoService;
import com.example.carduino.shared.MyApplication;

public class ContextsSingleton {
    private static ContextsSingleton contextsSingleton;
    private AppCompatActivity mainActivityContext;
    private MyApplication applicationContext;
    private ArduinoService arduinoServiceContext;

    private ContextsSingleton(){}
    public static ContextsSingleton getInstance()
    {
        if (contextsSingleton == null)
        {
            contextsSingleton = new ContextsSingleton();
        }
        return contextsSingleton;
    }

    public AppCompatActivity getMainActivityContext() {
        return mainActivityContext;
    }

    public void setMainActivityContext(AppCompatActivity mainActivityContext) {
        this.mainActivityContext = mainActivityContext;
    }

    public MyApplication getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(MyApplication applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ArduinoService getServiceContext() {
        return arduinoServiceContext;
    }

    public void setArduinoService(ArduinoService arduinoServiceContext) {
        this.arduinoServiceContext = arduinoServiceContext;
    }
}