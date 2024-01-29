package com.example.carduino.shared.singletons;

import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.shared.utilities.CircularArrayList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class SharedDataSingleton {
    private static SharedDataSingleton instance;
    private Boolean advancedMode;
    public PropertyChangeSupport advancedModepropertyChangeSupport;
    private CircularArrayList<Integer> luminanceReadings;
    private Integer maxDisplayBrightness;

    private SharedDataSingleton(){
        advancedMode = false;
        advancedModepropertyChangeSupport = new PropertyChangeSupport(this);
        luminanceReadings = new CircularArrayList<>(5);
        maxDisplayBrightness = null;
    }
    public static SharedDataSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new SharedDataSingleton();
        }
        return instance;
    }

    public static void invalidate() {
        instance = null;
    }

    public void setAdvancedMode(Boolean advancedMode) {
        Boolean oldValue = Boolean.valueOf(this.advancedMode);
        this.advancedMode = advancedMode;
        this.advancedModepropertyChangeSupport.firePropertyChange("ADVANCED_MODE", oldValue, this.advancedMode);
    }

    public Boolean getAdvancedMode() {
        return advancedMode;
    }

    public void addAdvancedModeChangeListener(PropertyChangeListener pcl) {
        advancedModepropertyChangeSupport.addPropertyChangeListener(pcl);
    }

    public void removeAdvancedModeChangeListener(PropertyChangeListener pcl) {
        advancedModepropertyChangeSupport.removePropertyChangeListener(pcl);
    }

    public CircularArrayList<Integer> getLuminanceReadings() {
        return luminanceReadings;
    }

    public Integer getMaxDisplayBrightness() {
        return maxDisplayBrightness;
    }

    public void setMaxDisplayBrightness(Integer maxDisplayBrightness) {
        this.maxDisplayBrightness = maxDisplayBrightness;
    }
}
