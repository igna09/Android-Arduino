package com.example.carduino.shared.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.carstatus.CarStatus;

import java.util.HashMap;

public class CarStatusViewModel extends ViewModel {
    private MutableLiveData<CarStatus> carStatusMutableLiveData;

    public CarStatusViewModel() {
        this.carStatusMutableLiveData = new MutableLiveData<>();
    }

    public void updateCarStatus() throws CloneNotSupportedException {
//        updateCarStatus((CarStatus) carStatusMutableLiveData.getValue().clone());
    }

    public void updateCarStatus(CarStatus carStatus) {
        if(carStatus == null) {
            carStatus = new CarStatus();
        }
        this.carStatusMutableLiveData.postValue(carStatus);
    }

    public LiveData getLiveDataCarStatus() {
        return carStatusMutableLiveData;
    }

    public CarStatus getCarStatus() {
        return carStatusMutableLiveData.getValue();
    }
}
