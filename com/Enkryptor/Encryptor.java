package com.Enkryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

//A interface that defines required methods for any encryption algorithm class.
public abstract class Encryptor {

    public abstract String encrypt(String plain);

    public abstract String decrypt(String cipher);

    private File[] getFiles(JFrame ref) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        int choice = fileChooser.showOpenDialog(ref);
        if (choice == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFiles();
        }

        return null;

    }

    public void encryptFiles(JFrame ref) throws IOException {

        File[] files = getFiles(ref);
        for (File f : files) {
            StringBuilder sb = new StringBuilder();
            InputStream is = new FileInputStream(f);
            Reader reader = new InputStreamReader(is);
            int r;
            while((r = reader.read()) != -1) {
                char c = (char) r;
                sb.append(c);
            }
            System.out.println(sb.toString());
            reader.close();
            String encryption = encrypt(sb.toString());
            //TODO: delete old file
            FileWriter fileWriter = new FileWriter("encrypted" + f.getName());
            fileWriter.write(encryption);
            fileWriter.close();  
        }
        
    }

    public void decryptFiles(JFrame ref) throws IOException {

        File[] files = getFiles(ref);
        for (File f : files) {
            StringBuilder sb = new StringBuilder();
            InputStream is = new FileInputStream(f);
            Reader reader = new InputStreamReader(is);
            int r;
            while((r = reader.read()) != -1) {
                char c = (char) r;
                sb.append(c);
            }
            System.out.println(sb.toString());
            reader.close();
            String decryption = decrypt(sb.toString());
            //TODO: delete old file
            //TODO: Just remove encrypted from the front
            FileWriter fileWriter = new FileWriter("decrypted" + f.getName());
            fileWriter.write(decryption);
            fileWriter.close();  
        }
        
    }
    
}