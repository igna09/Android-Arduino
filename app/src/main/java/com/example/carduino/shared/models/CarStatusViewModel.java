package com.example.carduino.shared.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.carduino.settings.factory.Setting;
import com.example.carduino.shared.models.carstatus.CarStatus;
import com.example.carduino.shared.models.carstatus.CarStatusEnum;
import com.example.carduino.shared.models.carstatus.CarStatusFactory;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.GeneralCarStatusPropertyChangeListener;
import com.example.carduino.shared.models.carstatus.propertychangelisteners.PropertyChangeListener;
import com.example.carduino.shared.models.carstatus.values.Value;

import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;

public class CarStatusViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, Value>> carStatusMutableLiveData;
    private PropertyChangeSupport support;

    public CarStatusViewModel() {
        this.carStatusMutableLiveData = new MutableLiveData<>();
        this.carStatusMutableLiveData.setValue(new HashMap<String, Value>());

        HashMap<String, Value> tmpHashMap = new HashMap<>();
        Arrays.stream(CarStatusEnum.values()).forEach(carStatusEnum -> {
            /*try {
                this.carStatusMutableLiveData.getValue().put(carStatusEnum.getId(), (Value) carStatusEnum.getType().newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }*/
            try {
                Value v = (Value) carStatusEnum.getType().newInstance();
                v.setId(carStatusEnum.getId());
                this.carStatusMutableLiveData.getValue().put(carStatusEnum.getId(), v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
            /*try {
                Value v = (Value) carStatusEnum.getType().newInstance();
                v.setId(carStatusEnum.getId());
                this.updateCarstatus(v);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }*/
        });
        // this.carStatusMutableLiveData.setValue(tmpHashMap);

        this.support = new PropertyChangeSupport(this);
        this.addPropertyChangeListener(new GeneralCarStatusPropertyChangeListener());
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void updateCarstatus(Value value) {
        if(!this.carStatusMutableLiveData.getValue().containsKey(value.getId()) && CarStatusEnum.getCarStatusEnumById(value.getId()).getPropertyChangeListener() != null) {
            this.addPropertyChangeListener(value.getPropertyChangeListener());
        }

        HashMap tmpHashMap = new HashMap<String, Value>();
        if(this.carStatusMutableLiveData.getValue() != null && !this.carStatusMutableLiveData.getValue().isEmpty()) {
            tmpHashMap.putAll(this.carStatusMutableLiveData.getValue());
        }

        support.firePropertyChange(value.getId(), this.carStatusMutableLiveData.getValue().get(value.getId()), value);

        tmpHashMap.put(value.getId(), value);
        this.carStatusMutableLiveData.postValue(tmpHashMap);
    }

    public LiveData getLiveDataCarStatus() {
        return carStatusMutableLiveData;
    }
}
