import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainPage extends JFrame {

    Encryptor encryptor;
    public MainPage() {
        super();
        this.setLayout(null);
        this.setSize(new Dimension(750, 500));
        // this.setLocation(0,0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showLogo();
        buildMainButtons();

        this.pack();
        this.setSize(750, 500);

        if (!Installer.isInstalled()) {
            Installer.install();
        }

        setupEncryptor();

    }

    private void setupEncryptor() {
        String encryptorName = Installer.getUserEncryptor();
        String[] key = new String[1];
        AskKeyDialog akd;

        if(encryptorName == "unknown") {
            Encryptor[] myCrypt = new Encryptor[1];
            new EncryptionSelecterDialog(MainPage.this, true, myCrypt);
            if(myCrypt[0] == null)
                System.exit(1);
            encryptor = myCrypt[0];
            try {
                Installer.setUserEncryptor(myCrypt[0]);
            } catch (IOException e) {
                System.exit(1);
            }
        }

        if(encryptorName.equals("Ceaser")) {
            akd = new AskKeyDialog(this, true, key, "Enter your Ceaser Key");
            boolean done = false;
            while (!done) {
                try {
                    this.encryptor = new CeaserEncryptor(Integer.parseInt(key[0]));
                    done = true;
                } catch (NumberFormatException e) {
                    akd = new AskKeyDialog(this, true, key, "Key could not be parsed.");
                    done = false;
                }
            }
        }

        if(encryptorName.equals("RSA")) {
            akd = new AskKeyDialog(this, true, key, "Enter your RSA Private Key");
            String keyPath = Installer.getKeyPath();
            Scanner scanner;
            try {
                scanner = new Scanner(new File(keyPath));
                String[] keyinfo = new String[1];
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if(line.isEmpty())
                        continue;
                    String[] split = line.split(",");
                    if(split[0].equals("RSA")) {
                        keyinfo = split;
                    }
                }
                encryptor = new RSAEncryptor(new BigInteger(keyinfo[1], 16), new BigInteger(key[0],16), new BigInteger(keyinfo[2], 16));
            } catch (FileNotFoundException e) {
                System.err.println("Couldn't open keys!");
                System.exit(1);
            }
        }
    }

    public void showLogo() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("f6c0ccc8-9149-4dde-aa91-3b74e1e04b82_200x200.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            picLabel.setSize(400, 194);
            picLabel.setLocation(175, 0);
            this.add(picLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildMainButtons() {
        // Build buttons
        JButton passwords = new JButton();
        passwords.setText("Passwords");
        passwords.setSize(new Dimension(250, 100));
        passwords.setLocation(100, 200);
        passwords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new PasswordsPage(encryptor).setVisible(true);
            }
        });
        this.add(passwords);

        JButton encryptorButton = new JButton();
        encryptorButton.setText("Encrypt File");
        encryptorButton.setSize(new Dimension(250, 100));
        encryptorButton.setLocation(400, 200);
        encryptorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    encryptor.encryptFiles(MainPage.this);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        this.add(encryptorButton);

        JButton decryptor = new JButton();
        decryptor.setText("Decrypt File");
        decryptor.setSize(new Dimension(250, 100));
        decryptor.setLocation(100, 350);
        decryptor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    encryptor.decryptFiles(MainPage.this);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        this.add(decryptor);

        JButton selectEncryptor = new JButton();
        selectEncryptor.setText("Select Encryptor");
        selectEncryptor.setSize(new Dimension(250, 100));
        selectEncryptor.setLocation(400, 350);
        selectEncryptor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Encryptor[] myCrypt = new Encryptor[1];
                new EncryptionSelecterDialog(MainPage.this, true, myCrypt);
                // TODO: NEED TO DO SOME HANDOFF SUCH AS LOAD ALL PASSWORDS DECRYPT WILL CURRENT
                // ENCRYPTOR, THEN ENCRYPT WITH NEW ENCRYPTOR
                // performHandoff(myCrypt[0]);
                if (myCrypt[0] != null) {
                    try {
                        performHandoff(myCrypt[0]);
                        encryptor = myCrypt[0];
                    } catch (IOException e) {
                        System.err.println("Encryptor handoff failed. No change.");
                    }
                }
            }
        });
        this.add(selectEncryptor);

    }

    private void performHandoff(Encryptor newEncryptor) throws IOException {

        //first time code
        if(encryptor == null) {
            Installer.setUserEncryptor(newEncryptor);
            return;
        }

        //get all passwords and decrypt
        Map<String, String> sitePasses = new TreeMap<>();
        Scanner scanner = new Scanner(new File(Installer.getPasswordPath()));

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] sitePass = line.split(",");
            String password = encryptor.decrypt(sitePass[1]);
            sitePasses.put(sitePass[0], password);
        }
        scanner.close();

        //reencrypt with new and save
        FileWriter fw = new FileWriter(new File(Installer.getPasswordPath()));

        for (String key : sitePasses.keySet()) {
            fw.write(key + "," + newEncryptor.encrypt(sitePasses.get(key)) + "\n");
        }
        fw.close();

        Installer.setUserEncryptor(newEncryptor);
        
    }

}