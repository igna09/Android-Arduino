package com.example.carduino.shared.singletons;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carduino.services.ArduinoService;
import com.example.carduino.shared.MyApplication;

public class ContextsSingleton {
    private static ContextsSingleton contextsSingleton;
    private MyApplication applicationContext;

    private ContextsSingleton(){}
    public static ContextsSingleton getInstance()
    {
        if (contextsSingleton == null)
        {
            contextsSingleton = new ContextsSingleton();
        }
        return contextsSingleton;
    }

    public MyApplication getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(MyApplication applicationContext) {
        this.applicationContext = applicationContext;
    }
}