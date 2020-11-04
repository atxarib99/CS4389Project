import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

        //TODO: first time app launch code
        //temp need to use user's preference.
        //TODO: oops this is not valid for things other than ceaser
        boolean done = false;
        String[] key = new String[1];
        AskKeyDialog akd = new AskKeyDialog(this, true, key, "");
        while(!done) {
            try {
                this.encryptor = new CeaserEncryptor(Integer.parseInt(key[0]));
                done = true;
            } catch (NumberFormatException e) {
                akd = new AskKeyDialog(this, true, key, "Key could not be parsed.");
                done = false;
            }
        }
    }

    public void showLogo() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("f6c0ccc8-9149-4dde-aa91-3b74e1e04b82_200x200.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            picLabel.setSize(400,194);
            picLabel.setLocation(175,0);
            this.add(picLabel);

        } catch (IOException e) {
            // TODO Auto-generated catch block
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
        decryptor.setSize(new Dimension(250,100));
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
                Crypters[] myCrypt = new Crypters[1];
                new EncryptionSelecterDialog(MainPage.this, true, myCrypt);
                //TODO: NEED TO DO SOME HANDOFF SUCH AS LOAD ALL PASSWORDS DECRYPT WILL CURRENT ENCRYPTOR, THEN ENCRYPT WITH NEW ENCRYPTOR
                if(myCrypt[0] != null) {
                    switch (myCrypt[0]) {
                        case Ceaser:
                            encryptor = new CeaserEncryptor(); //TODO: NEED A KEY VALUE AS WELL NOW UGH. PERHAPS EncryptionSelecterDialog SHOULD JUST RETURN A ENCRYPTOR OBJECT
                        case RSA:
                            encryptor = new RSAEncryptor();
                        default:
                            break;
                        
                    }
                }
            }
        });
        this.add(selectEncryptor);

    }

}