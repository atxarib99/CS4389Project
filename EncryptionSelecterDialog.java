import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class EncryptionSelecterDialog extends JDialog {

    JPanel mainPanel;
    Encryptor[] myCrypt;

    public EncryptionSelecterDialog(java.awt.Frame parent, boolean modal, Encryptor[] myCrypt) {
        super(parent, modal);

        this.myCrypt = myCrypt;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        this.setSize(800, 600);

        buildSelections();
        this.pack();
        this.setSize(800, 600);
        this.setVisible(true);

    }

    private void buildSelections() {
        for (Crypters crypter : Crypters.values()) {
            JButton button = new JButton();
            button.setText(crypter.name());
            button.setSize(new Dimension(125, 50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Crypters current = Crypters.valueOf(button.getText());
                    switch(current) {
                        case Ceaser:
                            myCrypt[0] = new CeaserEncryptor(); setupCeaser((CeaserEncryptor)myCrypt[0]); break;
                        case RSA:
                            myCrypt[0] = new RSAEncryptor(); setupRSA((RSAEncryptor)myCrypt[0]); break;
                        default:
                            break;

                    }
                    // check for keys if not generate
                    finish();
                }
            });
            mainPanel.add(button);
        }
    }

    private void setupCeaser(CeaserEncryptor enc) {
        String[] key = new String[1];
        new AskKeyDialog(this, true, key, "Enter a key: Any number");
        boolean done = false;
        while (!done) {
            try {
                enc.setKey(Integer.parseInt(key[0]));
                done = true;
            } catch (NumberFormatException e) {
                new AskKeyDialog(this, true, key, "Enter a key: Any number");
                done = false;
            }
        }
    }

    private void setupRSA(RSAEncryptor rsa) {

        // check for key file
        String keyPath = Installer.getKeyPath();
        File file = new File(keyPath);
        if (!file.exists()) {

            try {
                FileWriter fos = new FileWriter(file);
                fos.write("RSA,");

                fos.write("" + rsa.getPublicKey().toString(16));
                fos.write(",");
                fos.write("" + rsa.getN().toString(16));
                fos.write("\n");
                fos.close();
            } catch (IOException e) {
                System.err.println("ERROR WRITING KEYS!");
            }

            //show private key
            new ShowKeyDialog(EncryptionSelecterDialog.this, true, rsa.getPrivateKey().toString(16));
        } else {
            try {
                Scanner scanner = new Scanner(file);
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
                if(keyinfo.length > 1) {
                    String[] key = new String[1];
                    AskKeyDialog akd = new AskKeyDialog(this, true, key, "Enter your RSA key");
                    rsa = new RSAEncryptor(new BigInteger(keyinfo[1], 16), new BigInteger(key[0],16), new BigInteger(keyinfo[2], 16));
                }
            } catch(IOException e) {
                System.err.println("Error with files while setting up rsa.");
            }

        }
    }

    private void finish() {
        this.dispose();
    }

}