package com.example.carduino.shared.models.carstatus;

public class CarStatus {
    private Engine engine;
    private CelsiusTemperature externalTemperature;
    private CelsiusTemperature internalTemperature;
    private Trip trip;
    private KmhSpeed speed;
    private LuxLuminance internalLuminance;
    private CmDistance frontDistance;

    public Engine getEngine() {
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
    }
}
