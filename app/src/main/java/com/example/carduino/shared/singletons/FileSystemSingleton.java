package com.example.carduino.shared.singletons;

import android.os.Environment;

import java.io.File;
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
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
