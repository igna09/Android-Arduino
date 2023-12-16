package com.example.carduino.shared.singletons;

import com.example.carduino.shared.models.trip.Trip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TripSingleton {
    private static TripSingleton tripSingleton;

    private Trip trip;

    private final FileSystemSingleton fileSystemSingleton;
    private static final String TRIP_FILE_NAME = "last_trip.json";
    private final Thread backupThread;

    private TripSingleton() {
        trip = new Trip();
        fileSystemSingleton = FileSystemSingleton.getInstance();

        backupThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(backupThread.isAlive() && !backupThread.isInterrupted()) {
                        backupTrip();
                        Thread.sleep(15 * 1000);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backupThread.start();
    }

    public void stopBackupThread() {
        backupThread.interrupt();
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

    public void backupTrip() throws IOException {
        File tripFile = fileSystemSingleton.createOrGetFile(fileSystemSingleton.getCarduinoRootFolder(), TRIP_FILE_NAME);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(trip);

        BufferedWriter buf = new BufferedWriter(new FileWriter(tripFile, false));

        buf.append(json);

        buf.flush();
        buf.close();
    }

    public Boolean tripBackupAvailable() {
        return new File(fileSystemSingleton.getCarduinoRootFolder(), TRIP_FILE_NAME).exists();
    }

    public Boolean restoreTrip() throws Exception {
        if(tripBackupAvailable()) {
            File tripFile = fileSystemSingleton.createOrGetFile(fileSystemSingleton.getCarduinoRootFolder(), TRIP_FILE_NAME);

            FileInputStream fin = new FileInputStream(tripFile);
            String json = convertStreamToString(fin);
            fin.close();;

            Gson gson = new Gson();
            trip = gson.fromJson(json, Trip.class);

            return true;
        } else {
            return false;
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
