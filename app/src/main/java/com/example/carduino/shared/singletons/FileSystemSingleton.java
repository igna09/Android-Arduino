package com.example.carduino.shared.singletons;

import android.media.MediaScannerConnection;
import android.os.Environment;

import com.example.carduino.shared.utilities.LoggerUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystemSingleton {
    private final File ROOT;
    private static final String ROOT_FOLDER_NAME = "CARDUINO";
    private static FileSystemSingleton instance;

    private FileSystemSingleton() {
        ROOT = createOrGetFolder(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), ROOT_FOLDER_NAME);
    }

    public File getCarduinoRootFolder() {
        return ROOT;
    }

    public static FileSystemSingleton getInstance() {
        if(instance == null) {
            instance = new FileSystemSingleton();
        }
        return instance;
    }

    public File createOrGetFolder(File parent, String folderName) {
        File folder = new File(parent, folderName);
        if(!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    public File createOrGetFile(File parent, String fileName) throws IOException {
        File file = new File(parent, fileName);
        if(ContextsSingleton.getInstance().getApplicationContext() != null) {
            MediaScannerConnection.scanFile(ContextsSingleton.getInstance().getApplicationContext(), new String[]{file.toString()}, new String[]{"text/json"}, null);
        }
        if(!file.exists()) {
            if(!file.createNewFile()) {
                return null;
            }
        }
        return file;
    }

    public Boolean writeToFile(File file, String content, Boolean append) throws IOException {
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, append));

            buf.write(content);

            buf.flush();
            buf.close();

            return true;
        } catch (IOException e) {
            LoggerUtilities.logException(e);
            return false;
        }
    }
}
