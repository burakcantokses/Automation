package io.deltaware;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class DataManager {
    public static FileWriter file;

    public static void readData(int id) {
        String filePath = Delta.directory+"\\data"+id+".json";
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            String balance = (String) jsonObject.get("Bakiye");
            System.out.println("Bakiyeniz: " + balance);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeData(String name, String surname, String balance, int id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Bakiye", balance);
            jsonObject.put("Ad", name);
            jsonObject.put("Soyad", surname);

            System.out.print(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(Delta.directory+"\\data"+id+".json");
            file.write(jsonObject.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + jsonObject);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
