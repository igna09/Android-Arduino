package com.example.carduino.shared.singletons;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Logger {
    private static Logger logger;
    private static Long counter;
    private static File ROOT = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private static final String FOLDER_NAME = "CARDUINO_ACTIVITY";
    private File sessionFolder;

    private Logger(){
        try {
            sessionFolder = createSessionFolder();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Logger getInstance() {
        if(logger == null) {
            logger = new Logger();
            counter = new Long(0);
        }
        return logger;
    }

    public void log(String text) {
        log("main", text);
    }

    private File createOrGetFolder(File parent, String folderName) {
        File folder = new File(parent, folderName);
        if(!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    private File createOrGetFile(File parent, String fileName) throws IOException {
        File file = new File(parent, fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    private File createSessionFolder() throws IOException {
        File logsFolder = createOrGetFolder(ROOT, FOLDER_NAME);
        DateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        File dayFolder = createOrGetFolder(logsFolder, df.format(new Date()));
        List<File> directories = Arrays.asList(dayFolder.listFiles(File::isDirectory));
        Integer max = directories.stream().map(f -> Integer.parseInt(f.getName())).mapToInt(value -> value).max().orElse(0);
        max = max + 1;
        File sessionFolder = createOrGetFolder(dayFolder, max.toString());

        return sessionFolder;
    }

    public void log(String file, String text)
    {
        File logFile = null;
        try {
            logFile = createOrGetFile(sessionFolder, file + ".txt");
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

    private static void increaseCounter() {
        counter = counter + 1;
    }
}
