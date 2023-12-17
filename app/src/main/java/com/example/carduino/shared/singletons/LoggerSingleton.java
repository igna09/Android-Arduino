package com.example.carduino.shared.singletons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoggerSingleton {
    private static LoggerSingleton instance;
    private Long counter;
    private File sessionFolder;
    private FileSystemSingleton fileSystemSingleton;

    private LoggerSingleton(){
        fileSystemSingleton = FileSystemSingleton.getInstance();
        try {
            sessionFolder = createSessionFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        counter = new Long(0);
    }

    public static LoggerSingleton getInstance() {
        if(instance == null) {
            instance = new LoggerSingleton();
        }
        return instance;
    }

    public void log(String text) {
        log("main", text);
    }

    private File createSessionFolder() throws IOException {
        File carduinoRootFolder = fileSystemSingleton.getCarduinoRootFolder();
        DateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        File dayFolder = fileSystemSingleton.createOrGetFolder(carduinoRootFolder, df.format(new Date()));
        List<File> directories = Arrays.asList(dayFolder.listFiles(File::isDirectory));
        Integer max = directories.stream().map(f -> Integer.parseInt(f.getName())).mapToInt(value -> value).max().orElse(0);
        max = max + 1;
        File sessionFolder = fileSystemSingleton.createOrGetFolder(dayFolder, max.toString());

        return sessionFolder;
    }

    public void log(String file, String text)
    {
        File logFile = null;
        try {
            logFile = fileSystemSingleton.createOrGetFile(sessionFolder, file + ".txt");

            StringBuilder builder = new StringBuilder();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            builder.append(df.format(new Date()));
            builder.append(" - ");
            builder.append(counter.toString());
            builder.append(" - ");
            builder.append(text);
            builder.append('\n');

            Boolean writed = fileSystemSingleton.writeToFile(logFile, builder.toString(), true);
            if(writed) {
                increaseCounter();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log(sw.toString());
    }

    private void increaseCounter() {
        counter = counter + 1;
    }

    public static void invalidate() {
        instance = null;
    }
}
