package com.example.carduino.shared.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carduino.shared.models.Setting;

import java.util.HashMap;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, Setting>> hashMapMutableLiveData;

    public void addSetting(Setting setting) {
        HashMap tmpHashMap = new HashMap<String, Setting>();
        if(this.hashMapMutableLiveData.getValue() != null && !this.hashMapMutableLiveData.getValue().isEmpty()) {
            tmpHashMap.putAll(this.hashMapMutableLiveData.getValue());
        }
        tmpHashMap.put(setting.getId(), setting);
        this.hashMapMutableLiveData.postValue(tmpHashMap);
    }

    public LiveData getSettings() {
        return this.hashMapMutableLiveData;
    }
}
