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
import java.util.Date;

public class Logger {
    private static Logger logger;
    private static Long counter;

    private Logger(){}

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

    public void log(String file, String text)
    {
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File logFile = new File(root, file + "_carduino_log.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
