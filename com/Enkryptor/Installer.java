package com.Enkryptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Installer {

    public static boolean isInstalled() {

        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);

        if (enkryptor.isDirectory())
            return true;

        return false;
    }

    public static void install() {
        // path: /usr/local

        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        if (!enkryptor.isDirectory()) {
            enkryptor.mkdir();
        }

        try {
            // create passwords file.
            File passwords = new File(enkryptor.getAbsolutePath() + sep + "passwords.enk");
            FileOutputStream fos = new FileOutputStream(passwords);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            System.err.println("Installer failed!");
        }

    }

    public static void uninstall() {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        String[]entries = enkryptor.list();
        for(String s: entries){
            File currentFile = new File(enkryptor.getPath(),s);
            currentFile.delete();
        }
        enkryptor.delete();
    }

    public static String getInstallationDir() {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        return enkryptor.getAbsolutePath();
    }

    public static String getPasswordPath() {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        File passwords = new File(enkryptor.getAbsolutePath() + sep + "passwords.enk");

        return passwords.getAbsolutePath();
    }

    public static String getKeyPath(String algo) {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        File keys = new File(enkryptor.getAbsolutePath() + sep + algo + "keys.enk");

        return keys.getAbsolutePath();

    }

    public static String getUserEncryptor() {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        File encryptor = new File(enkryptor.getAbsolutePath() + sep + "encryptor.enk");

        Scanner scanner;
        try {
            scanner = new Scanner(encryptor);
            String encryptorName = scanner.nextLine();
            scanner.close();
            return encryptorName;
        } catch (FileNotFoundException e) {
            return "unknown";
        }

    }
    
    public static void setUserEncryptor(String enc) throws IOException {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        File encryptor = new File(enkryptor.getAbsolutePath() + sep + "encryptor.enk");

        FileWriter fw = new FileWriter(encryptor);
        fw.write(enc);
        fw.close();
    }
    public static void setUserEncryptor(Encryptor enc) throws IOException {
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        File encryptor = new File(enkryptor.getAbsolutePath() + sep + "encryptor.enk");

        FileWriter fw = new FileWriter(encryptor);
        if(enc != null) {
            if(enc instanceof CeaserEncryptor) {
                fw.write("Ceaser");
            }
            else if (enc instanceof RSAEncryptor) {
                fw.write("RSA");
            } else if(enc instanceof HybridEncryptor) {
                fw.write("Hybrid");
            }
        }
        fw.close();
    }

    

}
