package com.example.carduino.shared.models.carstatus;

import androidx.annotation.NonNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;

public class CarStatus {
    /*private Engine engine;
    private CelsiusTemperature externalTemperature;
    private CelsiusTemperature internalTemperature;
    private Trip trip;
    private KmhSpeed speed;
    private LuxLuminance internalLuminance;
    private CmDistance frontDistance;*/
    private HashMap <String, Value> carStatusValues;
    private PropertyChangeSupport support;

    public CarStatus() {
        /*this.engine = new Engine();
        this.externalTemperature = new CelsiusTemperature();
        this.internalTemperature = new CelsiusTemperature();
        this.trip = new Trip();
        this.speed = new KmhSpeed();
        this.internalLuminance = new LuxLuminance();
        this.frontDistance = new CmDistance();*/

        this.carStatusValues = new HashMap<>();
        Arrays.stream(CarStatusEnum.values()).forEach(carStatusEnum -> {
            try {
                this.carStatusValues.put(carStatusEnum.getId(), (Value) carStatusEnum.getType().newInstance());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /*public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CelsiusTemperature getExternalTemperature() {
        return externalTemperature;
    }

    public void setExternalTemperature(CelsiusTemperature externalTemperature) {
        this.externalTemperature = externalTemperature;
    }

    public CelsiusTemperature getInternalTemperature() {
        return internalTemperature;
    }

    public void setInternalTemperature(CelsiusTemperature internalTemperature) {
        this.internalTemperature = internalTemperature;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public KmhSpeed getSpeed() {
        return speed;
    }

    public void setSpeed(KmhSpeed speed) {
        this.speed = speed;
    }

    public LuxLuminance getInternalLuminance() {
        return internalLuminance;
    }

    public void setInternalLuminance(LuxLuminance internalLuminance) {
        this.internalLuminance = internalLuminance;
    }

    public CmDistance getFrontDistance() {
        return frontDistance;
    }

    public void setFrontDistance(CmDistance frontDistance) {
        this.frontDistance = frontDistance;
    }*/

    public void putValue(Value value) {
        support.firePropertyChange(value.getId(), this.carStatusValues.get(value.getId()), value);
        this.carStatusValues.put(value.getId(), value);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
