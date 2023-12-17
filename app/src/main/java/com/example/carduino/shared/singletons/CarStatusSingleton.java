package com.example.carduino.shared.singletons;

import com.example.carduino.shared.models.carstatus.CarStatus;

public class CarStatusSingleton {
    private static CarStatusSingleton instance;

    private CarStatus carStatus;

    private CarStatusSingleton(){}

    public static CarStatusSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new CarStatusSingleton();
        }
        return instance;
    }

    public CarStatus getCarStatus() {
        if(carStatus == null) {
            this.carStatus = new CarStatus();
        }
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public static void invalidate() {
        instance = null;
    }
}
