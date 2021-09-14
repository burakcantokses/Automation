/*
 * @Delta 2019-2021
 */
package io.deltaware.check;

import io.deltaware.Delta;
import io.deltaware.managers.BalanceManager;
import io.deltaware.managers.DataManager;
import io.deltaware.managers.PurchaseManager;

import java.io.File;
import java.util.Scanner;

/**
 * @Author burakcantokses
 */
public class Check {
    //Konsola yazdığımız verileri okumamıza yardımcı olur.
    public Scanner input = new Scanner(System.in);

    //Otomasyonu başlatır.
    public void startAutomation() {
        System.out.println("Bakiyeli otomasyona hoşgeldin.");
        System.out.println("Kayıt oluşturmak için '!kayit' yazın.");
        System.out.println("Eğer kaydınız varsa '!giris' yazın.");
        String checksum=input.nextLine();
        if (checksum.equalsIgnoreCase("!kayit")) {
            registerMember();
        }else if (checksum.equalsIgnoreCase("!giris")) {
            System.out.println("Kimlik (id) numaranızı girin.");
            int id = Integer.parseInt(input.nextLine());
            onCheck(id);
        }else {
            System.out.println("Yanlış komut kullandığınız için program sonlandırılıyor.");
            System.exit(0);
        }
    }

    //Kullanıcının kayıtlı olup olmadığını eğer kayıtlı değilse kayıt olmayı, kayıtlıysa giriş yapmayı sağlar.
    public void onCheck(int id) {
        File file = new File(Delta.directory+"\\data"+id+".json");
        if (!file.exists()) {
            System.out.println("Böyle bir kayıt bulunamadı.");
            System.out.println("Kayıt yapmak için '!kayit' yazın.");
            System.out.println("Menüye dönmek için '!geri' yazın.");
            System.out.println("Eğer yanlış komut kullanırsanız program kendini sonlandıracaktır.");
            if (input.nextLine().equals("!kayit")) {
                registerMember();
            }else if (input.nextLine().equals("!geri")) {
                startAutomation();
            }else {
                System.out.println("Yanlış komut kullandığınız için program sonlandırılıyor.");
                System.exit(0);
            }
        }else if (file.exists()) {
            loginMember(id);
        }else {
            System.out.println("Bilinmeyen bir hata oluştu.");
            System.exit(0);
        }
    }
    //Kullanıcının giriş yaparak bakiyesini azaltmasını sağlar. Eğer alacağı ürün bakiyeden az ise yükleme yaptırma bölümüne girer.
    //Yükleme yapma yeri değişken olduğundan varsayılan değer 100 bakiye olarak girilmiştir.
    public void loginMember(int id) {
        File file = new File(Delta.directory+"\\data"+id+".json");
        DataManager dataManager = new DataManager();
        BalanceManager balanceManager = new BalanceManager();
        PurchaseManager purchase = new PurchaseManager();
        dataManager.readData(id);
        balanceManager.setBalance(dataManager.getDataBalance());
        int balance = balanceManager.getBalance();
        if (balance>=purchase.getPrice()) {
            int newBalance = balance - purchase.getPrice();
            balanceManager.setBalance(newBalance);
            System.out.println(purchase.getPrice() + " miktarında harcama yapıldı.");
            System.out.println("Eski bakiye: " + balance);
            System.out.println("Yeni bakiye: " + newBalance);
            dataManager.writeData("" + newBalance, id);
            System.out.println("Menüye yönlendiriliyorsun.");
            startAutomation();
        }
        else if (balance<purchase.getPrice()) {
            // Bakiye yükleme yeri
            // Test aşamasında olduğundan otomatik para yüklemekte
            balanceManager.setAddBalance(100);
            balanceManager.setBalance(balanceManager.getBalance()+balanceManager.getAddBalance());
            dataManager.writeData("" + balanceManager.getBalance(), id);
            dataManager.readData(id);
            System.out.println("Bakiye başarıyla yüklendi. Hata çıkmaması için menüye yönlendiriliyorsun.");
            startAutomation();
        }else {
            System.out.println("Bilinmeyen bir hata oluştu menüye yönlendiriliyorsun.");
            startAutomation();
        }
    }

    //Kullanıcı kaydetmeyi sağlar.
    public void registerMember() {
        DataManager dataManager = new DataManager();
        BalanceManager balanceManager = new BalanceManager();
        System.out.println("Kayıt sistemine hoşgeldin.");
        System.out.println("Kayıt etmek istediğiniz kişinin adını girin.");
        String name = input.nextLine();
        System.out.println("Kayıt etmek istediğiniz kişinin soyadını girin.");
        String surname = input.nextLine();
        System.out.println("Kayıt etmek istediğiniz kişinin kimlik (id) numarasını girin.");
        while (true) {
           int id = Integer.parseInt(input.nextLine());
            File file = new File(Delta.directory+"\\data"+id+".json");
            if (file.exists()) {
                System.out.println("Böyle bir kimlik (id) numarası bulunmaktadır. Tekrar deneyin.");
            }else {
                dataManager.createNewData(name,surname,""+balanceManager.getBalance(), id);
                break;
            }
        }
        startAutomation();
    }
}
