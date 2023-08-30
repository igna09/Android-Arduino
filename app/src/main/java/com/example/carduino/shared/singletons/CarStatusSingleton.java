package com.example.carduino.shared.singletons;

import com.example.carduino.shared.models.carstatus.CarStatus;

public class CarStatusSingleton {
    private static CarStatusSingleton carStatusSingleton;

    private CarStatus carStatus;

    private CarStatusSingleton(){}

    public static CarStatusSingleton getInstance()
    {
        if (carStatusSingleton == null)
        {
            carStatusSingleton = new CarStatusSingleton();
        }
        return carStatusSingleton;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }
}
