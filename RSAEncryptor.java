import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAEncryptor extends Encryptor {

    
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger n;
    private final static SecureRandom random = new SecureRandom();
    public RSAEncryptor(BigInteger publicKey, BigInteger privateKey, BigInteger n ) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.n = n;
    }

    public RSAEncryptor() {
        generateKeys();
    }

    private BigInteger getGCD(BigInteger num1, BigInteger num2) {

        BigInteger gcd = BigInteger.valueOf(1);
        BigInteger i;
        for(i = new BigInteger("1"); i.compareTo(num1) <= 0 && i.compareTo(num2) <= 0; i.add(BigInteger.ONE))
        {
            if(num1.mod(i).equals(BigInteger.ZERO) && num2.mod(i).equals(BigInteger.ZERO))
                gcd = i;
        }

        return gcd;
    }

    private static BigInteger modInverse(BigInteger a, BigInteger m) 
    {
        BigInteger x = new BigInteger(a.toString(10));
        x = a.mod(m);
        BigInteger y;
        for (y = new BigInteger("1"); y.compareTo(m) < 0; y.add(BigInteger.ONE))
           if ((a.multiply(y)).mod(m).equals(BigInteger.ONE))
              return x; 
        return BigInteger.ONE; 
    } 

    public void generateKeys() {
        BigInteger one = new BigInteger("1");
        BigInteger p = BigInteger.probablePrime(64, random);
        BigInteger q = p;
        while(q == p)
            q = BigInteger.probablePrime(64, random);
        
        BigInteger n = p.multiply(q);
        BigInteger phin = (p.subtract(one)).multiply(q.subtract(one));

        //from my research it is common practice to use this.
        BigInteger e = new BigInteger("65537");

        /**
         * THIS WOULD TAKE FOREVER TO COMPUTE
         */
        // for(e = new BigInteger("2"); e.compareTo(phin) < 0; e.add(BigInteger.ONE)) {
        //     if(getGCD(phin, e).equals(BigInteger.valueOf(1))) {
        //         break;
        //     }
        // }

        // BigInteger d = modInverse(e, phin);
        BigInteger d = e.modInverse(phin);

        this.n = n;
        this.publicKey = e;
        this.privateKey = d;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getN() {
        return n;
    }

    protected BigInteger getPrivateKey() {
        return privateKey;
    }

    @Override
    public String encrypt(String plain) {
        StringBuilder sb = new StringBuilder();
        StringBuilder out = new StringBuilder();
        BigInteger pla;
        BigInteger enc = BigInteger.valueOf(0L);
        for(int i = 0; i < plain.length(); i++) {
            sb.append(Integer.toHexString((int)plain.charAt(i)));
            if(i % 15 == 0 && i != 0) {
                pla = new BigInteger(sb.toString(), 16);
                enc = pla.modPow(this.publicKey, this.n);
                String temp = enc.toString(16);
                while(temp.length() < 32) {
                    temp = "0" + temp;
                }
                out.append(temp);
                sb = new StringBuilder();
            }
        }

        pla = new BigInteger(sb.toString(), 16);
        enc = pla.modPow(this.publicKey, this.n);
        String temp = enc.toString(16);
        while(temp.length() < 32) {
            temp = "0" + temp;
        }
        out.append(temp);

        return out.toString();
    }

    @Override
    public String decrypt(String cipher) {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i < cipher.length(); i+=32) {
            String sub = cipher.substring(i, i+32);
            BigInteger enc = new BigInteger(sub, 16);
            BigInteger pla = enc.modPow(this.privateKey, this.n);

            String temp = pla.toString(16);
            StringBuilder plain = new StringBuilder();
            for(int j = 0; j < temp.length()-1; j+=2) {
                plain.append((char)Integer.parseInt("" + temp.charAt(j) + temp.charAt(j+1), 16));
            }
            out.append(plain);
        }

        return out.toString();
    }


}
