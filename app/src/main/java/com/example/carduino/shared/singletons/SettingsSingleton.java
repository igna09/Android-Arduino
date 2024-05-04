package com.example.carduino.shared.singletons;

import com.example.carduino.settings.SettingsEnum;
import com.example.carduino.settings.settingfactory.Setting;
import com.example.carduino.settings.settingfactory.SettingsFactory;
import com.example.carduino.settings.settingviewfactory.SettingViewWrapper;
import com.example.carduino.shared.models.trip.Trip;
import com.example.carduino.shared.models.trip.tripvalue.TripValue;
import com.example.carduino.shared.models.trip.tripvalue.TripValueEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsSingleton {
    private static SettingsSingleton instance;
    private HashMap<String, Setting> settings;
    public PropertyChangeSupport support;
    private final FileSystemSingleton fileSystemSingleton;
    private static final String SETTINGS_FILE_NAME = "settings.json";

    private final Thread backupThread;

    private class SettingsValueDeserializer implements JsonDeserializer<HashMap> {
        @Override
        public HashMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            HashMap<String, Setting> settings = new HashMap<>();
            json.getAsJsonObject().keySet().forEach(key -> {
                JsonElement jsonElement = json.getAsJsonObject().get(key).getAsJsonObject().get("value");
                Setting setting = SettingsFactory.getSetting(key, jsonElement != null ? jsonElement.toString() : null);
                if(setting != null) {
                    settings.put(key, setting);
                }
            });
            return settings;
        }
    }

    private SettingsSingleton(){
        fileSystemSingleton = FileSystemSingleton.getInstance();

        support = new PropertyChangeSupport(this);
        this.settings = new HashMap<>();

        backupThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(backupThread.isAlive() && !backupThread.isInterrupted()) {
                        Thread.sleep(15 * 1000);
                        backupSettings();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        if(settingsBackupAvailable()) {
            try {
                restoreSettings();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        Arrays.stream(SettingsEnum.values())
                .filter(settingEnum -> settingEnum.getSettingType() == SettingsEnum.SettingType.APP)
                .forEach(settingEnum -> {
                    if(!this.getSettings().containsKey(settingEnum.name())) { // if setting does not exists i create it
                        this.addSetting(SettingsFactory.getSetting(settingEnum.name(), null));
                    }
                });

        backupThread.start();
    }

    public static SettingsSingleton getInstance()
    {
        if (instance == null)
        {
            instance = new SettingsSingleton();
        }
        return instance;
    }

    public void addSetting(Setting setting) {
        Setting oldSetting = settings.get(setting.getId());
        settings.put(setting.getId(), setting);
        /*if(oldSetting != null && oldSetting.getView() != null) {
            setting.setView(oldSetting.getView());
        } else {
            setting.setView(null);
        }*/
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

    public void backupSettings() throws IOException {
        File settingsFile = getSettingsFile();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(settings);

        if(settingsFile != null) {
            fileSystemSingleton.writeToFile(settingsFile, json, false);
        }
    }

    private File getSettingsFile() throws IOException {
        return fileSystemSingleton.createOrGetFile(fileSystemSingleton.getCarduinoRootFolder(), SETTINGS_FILE_NAME);
    }

    public Boolean settingsBackupAvailable() {
        return fileSystemSingleton.existsInRoot(SETTINGS_FILE_NAME);
    }

    public Boolean restoreSettings() throws Exception {
        File tripFile = getSettingsFile();

        FileInputStream fin = new FileInputStream(tripFile);
        String json = convertStreamToString(fin);
        fin.close();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(HashMap.class, new SettingsSingleton.SettingsValueDeserializer())
                .create();
        settings = gson.fromJson(json, HashMap.class);

        return true;
    }

    public void resetSettings() throws IOException {
        settings = new HashMap<>();
        backupSettings();
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

    public void startBackupThread() {
        if(!backupThread.isAlive()) {
            backupThread.start();
        }
    }

    public void stopBackupThread() {
        backupThread.interrupt();
    }

    public static void invalidate() {
        instance = null;
    }
}
