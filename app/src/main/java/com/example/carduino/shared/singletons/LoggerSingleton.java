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
        try {
            sessionFolder = createSessionFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        counter = new Long(0);
        fileSystemSingleton = FileSystemSingleton.getInstance();
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
//            SimpleDateFormat.getDateTimeInstance();
            buf.append(df.format(new Date()));
            buf.append(" - ");
            buf.append(counter.toString());
            buf.append(" - ");
            buf.append(text);
            buf.newLine();
            buf.flush();
            buf.close();

            increaseCounter();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
}
