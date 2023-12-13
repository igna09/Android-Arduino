package com.example.carduino.shared.singletons;

import com.example.carduino.shared.models.trip.Trip;

public class TripSingleton {
    private static TripSingleton tripSingleton;
    private Trip trip;

    private TripSingleton() {
        trip = new Trip();
    }

    public static TripSingleton getInstance() {
        if(tripSingleton == null) {
            tripSingleton = new TripSingleton();
        }
        return tripSingleton;
    }

    public Trip getTrip() {
        return trip;
    }
}
