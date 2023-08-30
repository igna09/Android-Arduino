package com.example.carduino.shared.models.carstatus;

import androidx.annotation.NonNull;

public class Engine {
    private CelsiusTemperature waterCoolingTemperature;
    private CelsiusTemperature oilTemperature;
    private KpaPressure intakeManifoldPressure;
    private Rpm rpm;

    public Engine() {
        this.waterCoolingTemperature = new CelsiusTemperature();
        this.oilTemperature = new CelsiusTemperature();
        this.intakeManifoldPressure = new KpaPressure();
        this.rpm = new Rpm();
    }

    public CelsiusTemperature getWaterCoolingTemperature() {
        return waterCoolingTemperature;
    }

    public void setWaterCoolingTemperature(CelsiusTemperature waterCoolingTemperature) {
        this.waterCoolingTemperature = waterCoolingTemperature;
    }

    public CelsiusTemperature getOilTemperature() {
        return oilTemperature;
    }

    public void setOilTemperature(CelsiusTemperature oilTemperature) {
        this.oilTemperature = oilTemperature;
    }

    public KpaPressure getIntakeManifoldPressure() {
        return intakeManifoldPressure;
    }

    public void setIntakeManifoldPressure(KpaPressure intakeManifoldPressure) {
        this.intakeManifoldPressure = intakeManifoldPressure;
    }

    public Rpm getRpm() {
        return rpm;
    }

    public void setRpm(Rpm rpm) {
        this.rpm = rpm;
    }
}
