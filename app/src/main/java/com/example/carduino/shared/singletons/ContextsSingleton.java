package com.example.carduino.shared.singletons;

import com.example.carduino.shared.MyApplication;

public class ContextsSingleton {
    private static ContextsSingleton instance;
    private MyApplication applicationContext;

    private ContextsSingleton(){}
    public static ContextsSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new ContextsSingleton();
        }
        return instance;
    }

    public MyApplication getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(MyApplication applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void invalidate() {
        instance = null;
    }
}