
public class CeaserEncryptor extends Encryptor {

    private int key;
    public CeaserEncryptor(int key) {
        this.key = key % 26;
    }

    public CeaserEncryptor() {
        key = 2;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public String encrypt(String s) {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            c += key;
            sb.append(("" + c));
        }
        return sb.toString();

    }

    public String decrypt(String s) {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            c -= key;
            sb.append(("" + c));
        }
        return sb.toString();

    }
}