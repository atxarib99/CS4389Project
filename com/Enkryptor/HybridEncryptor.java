package com.Enkryptor;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HybridEncryptor extends Encryptor {

    private static SecretKey secretKey;
    private static byte[] key;

    private Encryptor rsa;

    public HybridEncryptor(String myKey, Encryptor rsa) {
        setKey(myKey);
        this.rsa = rsa;
    }

    public HybridEncryptor() {
        //generate a key
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // for example
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Couldn't generate AES key");
        }
    }

    protected String getKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public void setKey(String myKey)
    {
        byte[] decodedKey = Base64.getDecoder().decode(myKey);
        secretKey = new SecretKeySpec(decodedKey, "AES");
        // MessageDigest sha = null;
        // try {
        //     key = myKey.getBytes("UTF-8");
        //     sha = MessageDigest.getInstance("SHA-1");
        //     key = sha.digest(key);
        //     key = Arrays.copyOf(key, 16);
        //     secretKey = new SecretKeySpec(key, "AES");
        // }
        // catch (NoSuchAlgorithmException e) {
        //     e.printStackTrace();
        // }
        // catch (UnsupportedEncodingException e) {
        //     e.printStackTrace();
        // }
    }

    public void setRSA(Encryptor rsa) {
        this.rsa = rsa;
    }

    public String encrypt(String strToEncrypt)
    {
        try
        {
            //setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt)
    {
        try
        {
            //setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


}
