package com.example.carduino.shared.singletons;

import com.example.carduino.settings.settingfactory.Setting;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class SharedDataSingleton {
    private static SharedDataSingleton instance;
    private Map settings;
    private Boolean advancedMode;
    public PropertyChangeSupport advancedModepropertyChangeSupport;

    private SharedDataSingleton(){
        advancedMode = false;
        advancedModepropertyChangeSupport = new PropertyChangeSupport(this);
    }
    public static SharedDataSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new SharedDataSingleton();
        }
        return instance;
    }

    public Map getSettings() {
        return settings;
    }

    public void setSettings(Setting setting) {
        if(this.settings == null) {
            this.settings = new HashMap<String, Setting>();
        }

        this.settings.put(setting.getId(), setting);
    }

    public static void invalidate() {
        instance = null;
    }

    public void setAdvancedMode(Boolean advancedMode) {
        boolean oldValue = advancedMode;
        this.advancedMode = advancedMode;
        this.advancedModepropertyChangeSupport.firePropertyChange("ADVANCED_MODE", oldValue, this.advancedMode.booleanValue());
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
}
