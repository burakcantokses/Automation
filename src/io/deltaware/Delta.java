/*
 * @Delta 2019-2021
 */
package io.deltaware;

import java.io.File;

/**
 * @Author burakcantokses
 */
public class Delta {
    //Varsayılan veri depolama adresi
    public static File directory = new File(System.getProperty("user.home")+"\\AppData\\Roaming\\.delta");

    public static void start() {
        //Oluşturulmuş klasör yoksa hata almamak için klasör oluşturur.
        if (!directory.exists()) {
            new File(directory.toString()). mkdir();
        }
        //Otomasyonu başlatır.
        Check check = new Check();
        check.startAutomation();
    }
}
