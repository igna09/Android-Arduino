package com.example.carduino.shared.models.carstatus;

public class Trip {
    private Duration duration;
    private KmhSpeed averageSpeed;
    private KmhSpeed maxSpeed;

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public KmhSpeed getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(KmhSpeed averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public KmhSpeed getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(KmhSpeed maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
