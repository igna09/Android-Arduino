package com.example.carduino.shared.singletons;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivitySingleton{
    private static MainActivitySingleton mainActivitySingleton;
    private AppCompatActivity mainActivityContext;

    private MainActivitySingleton(){}
    public static MainActivitySingleton getInstance()
    {
        if (mainActivitySingleton == null)
        {
            mainActivitySingleton = new MainActivitySingleton();
        }
        return mainActivitySingleton;
    }

    public AppCompatActivity getMainActivityContext() {
        return mainActivityContext;
    }

    public void setMainActivityContext(AppCompatActivity mainActivityContext) {
        this.mainActivityContext = mainActivityContext;
    }
}