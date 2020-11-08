package com.Enkryptor;

import java.math.BigInteger;
import java.util.ArrayList;

public class RSAEncryptorBk extends Encryptor {

    
    private long publicKey;
    private long privateKey;
    private long n;
    public RSAEncryptorBk(long publicKey, long privateKey, long n ) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.n = n;
    }

    public RSAEncryptorBk() {
        generateKeys();
    }

    private long getGCD(long num1, long num2) {

        long gcd = 1;
        for(long i = 1; i <= num1 && i <= num2; i++)
        {
            if(num1%i==0 && num2%i==0)
                gcd = i;
        }

        return gcd;
    }

    private static long modInverse(long a, long m) 
    { 
        a = a % m; 
        for (long x = 1; x < m; x++) 
           if ((a * x) % m == 1) 
              return x; 
        return 1L; 
    } 

    public void generateKeys() {
        long p = RandomPrime.getLargePrime();
        long q = p;
        while(q == p)
            q = RandomPrime.getLargePrime();
        
        long n = p * q;
        long phin = (p-1) * (q-1);

        long e;
        for(e = 2; e < phin; e++) {
            if(getGCD(phin, e) == 1) {
                break;
            }
        }

        long d = modInverse(e, phin);

        this.n = n;
        this.publicKey = e;
        this.privateKey = d;
    }
    
    public long getN() {
        return n;
    }

    public long getPrivateKey() {
        return privateKey;
    }
    public long getPublicKey() {
        return publicKey;
    }

    @Override
    public String encrypt(String plain) {
        byte[] pla = plain.getBytes();
        StringBuilder sb = new StringBuilder();
        // long[] enc = new long[pla.length];
        ArrayList<String> enc = new ArrayList<>();

        for(int i = 0; i < pla.length; i++) {
            BigInteger t = BigInteger.valueOf((long)pla[i]);
            BigInteger p = BigInteger.valueOf(this.publicKey);
            BigInteger n = BigInteger.valueOf(this.n);
            BigInteger x = t.modPow(p, n);
            enc.add(x.toString(16));
        }

        long largest = 0;
        for(String s : enc) {
            if(s.length() > largest) {
                largest = s.length();
            }
        }

        for(int i = 0; i < enc.size(); i++) {
            String s = enc.get(i);
            while(s.length() < largest) {
                s += "";
            }
        }

        return sb.toString();
    }

    @Override
    public String decrypt(String cipher) {
        byte[] enc = cipher.getBytes();
        byte[] pla = new byte[enc.length];
        for(int i = 0; i < enc.length; i++) {
            BigInteger temp = new BigInteger(""+enc[i]);
            temp = temp.pow((int)this.privateKey);
            temp.mod(BigInteger.ONE);
            temp = temp.modPow(new BigInteger(""+this.privateKey), new BigInteger(""+this.n));
            System.out.println(temp.toString());
            pla[i] = (byte) (Math.pow(enc[i], this.privateKey) % this.n); 
        }

        return new String(pla);
    }


}