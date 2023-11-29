package com.example.carduino.shared.models;

import com.example.carduino.settings.settingfactory.Setting;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class Settings {
    private HashMap<String, Setting> settings;
    public PropertyChangeSupport support;

    public Settings() {
        support = new PropertyChangeSupport(this);
        this.settings = new HashMap<>();
    }

    public void addSetting(Setting setting) {
        Setting oldSetting = settings.get(setting.getId());
        settings.put(setting.getId(), setting);
        if(oldSetting != null && oldSetting.getView() != null) {
            setting.setView(oldSetting.getView());
        } else {
            setting.setView(null);
        }
        this.support.firePropertyChange(setting.getId(), oldSetting, setting);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public HashMap<String, Setting> getSettings() {
        return settings;
    }
}
