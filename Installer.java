import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Installer {
    
    public static boolean isInstalled() {
        
        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);

        if(enkryptor.isDirectory())
            return true;

        return false;
    }

    public static void install() {
        //path: /usr/local

        String sep = File.separator;
        String home = System.getProperty("user.home");

        File enkryptor = new File(home + sep + "Enkryptor" + sep);
        if(!enkryptor.isDirectory()) {
            enkryptor.mkdir();
        }

        try {
            //create passwords file.
            File passwords = new File(enkryptor.getAbsolutePath() + sep + "passwords.enk");
            FileOutputStream fos = new FileOutputStream(passwords);
            fos.write("".getBytes());
            fos.close();
        } catch (IOException e) {
            System.err.println("Installer failed!");
        }

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
}
