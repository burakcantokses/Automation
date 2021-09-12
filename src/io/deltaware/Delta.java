package io.deltaware;

import java.io.File;

public class Delta {
    public static File directory = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\.delta");
    public static void firstStart() {
        if (!directory.exists()) {
            new File(directory.toString()). mkdir();
        }
    }
}
