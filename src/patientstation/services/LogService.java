/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientstation.services;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author rajalprasad
 */
public class LogService {
    
    public static String getTZone () {
    TimeZone tz = TimeZone.getDefault();
    String setTZone = tz.getID();
    return setTZone;
}
    
    public static void Logger (String username, boolean successful) {
        Logger loginlogs = Logger.getLogger("log.txt");
        try {
            FileHandler fh = new FileHandler("logs.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            loginlogs.addHandler(fh);
            if (successful) {
                loginlogs.info(getTZone() +"\nUsername: " +username+
                "\nLogin: successful\n");
            } else {
                loginlogs.info(getTZone() +"\nUsername: " +username+
                "\nLogin: failed\n");
            }
            fh.close();
        } catch (IOException e) {
                System.out.println("Logger error: " + e.getMessage());
        }
    }
}
