/*
 * @Delta 2019-2021
 */
package io.deltaware;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Author burakcantokses
 */
public class DataManager {
    public FileWriter file;
    public int dataBalance;

    public int getDataBalance() {
        return dataBalance;
    }

    public void setDataBalance(int dataBalance) {
        this.dataBalance = dataBalance;
    }

    // Veriyi okur, bakiye objesini dataBalance değişkenine atar.
    public void readData(int id) {
        String filePath = Delta.directory+"\\data"+id+".json";
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;

            String balance = (String) jsonObject.get("Bakiye");
            System.out.println(balance);
            setDataBalance(Integer.parseInt(balance));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Yeni kişi oluşturulurken kullanılır.
    public void createNewData(String name, String surname, String balance, int id){
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
            file = new FileWriter(Delta.directory+"\\data"+id+".json");
            file.write(jsonObject.toJSONString());
            System.out.println("\n JSON Objesi: " + jsonObject);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Oluşturulan kişinin verilerinin düzenlenmesi için kullanılır. Yalnızca bakiye için geçerlidir ama üzerinde oynanarak farklı veriler içinde kullanılabilir.
    public void writeData(String balance, int id){

        String filePath = Delta.directory+"\\data"+id+".json";
        JSONParser jsonParser = new JSONParser();
        File file = new File(Delta.directory+"\\data"+id+".json");
        Check check = new Check();
        String name="",surname="",balanceStr="";
        try{
            Object obj = jsonParser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            name = (String) jsonObject.get("Ad");
            surname = (String) jsonObject.get("Soyad");
            balanceStr = (String) jsonObject.get("Bakiye");
        }catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        if (!file.exists()) {
            System.out.println("Böyle bir kayıt bulunamadı.");
            System.out.println("Menüye aktarılıyorsunuz.");
            check.startAutomation();
        }else if (file.exists()) {
            try {
                file.delete();
                System.out.println("Dosya başarıyla yeniden oluşturulmak üzere silindi.");
                createNewData(name, surname, balance, id);
                System.out.println("Dosya oluşturuldu.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Bilinmeyen bir hata oluştu sistem kapanıyor.");
            System.exit(0);
        }
    }
}
