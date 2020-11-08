public class RSAEncryptor extends Encryptor {

    
    public long publicKey;
    private long privateKey;
    public long n;
    public RSAEncryptor(long publicKey, long privateKey, long n ) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public RSAEncryptor() {
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



    @Override
    public String encrypt(String plain) {
        byte[] pla = plain.getBytes();
        byte[] enc = new byte[plain.getBytes().length];
        for(int i = 0; i < enc.length; i++) {
            enc[i] = (byte) (Math.pow(pla[i], this.publicKey) % this.n); 
        }

        return new String(enc);
    }

    @Override
    public String decrypt(String cipher) {
        byte[] enc = cipher.getBytes();
        byte[] pla = new byte[enc.length];
        for(int i = 0; i < enc.length; i++) {
            pla[i] = (byte) (Math.pow(enc[i], this.privateKey) % this.n); 
        }

        return new String(pla);
    }


}
