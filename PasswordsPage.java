import java.awt.Component;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PasswordsPage extends JFrame {

    JPanel mainPanel;
    Map<String, String> sitePasses;
    Encryptor encryptor;

    public PasswordsPage(Encryptor encryptor) {
        super();
        this.encryptor = encryptor;
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        this.setSize(800, 600);

        sitePasses = new TreeMap<>();
        try {
            loadPasswords();
        } catch (FileNotFoundException e) {
            System.err.println("PASSWORDS FILE NOT FOUND!");
        }

        this.pack();
        this.setSize(800, 600);
        this.setVisible(true);
        this.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent event) {
                try {
                    savePasswords();
                } catch (IOException e) {
                    System.err.println("COULDN'T SAVE PASSWORDS!");
                }
            }

        });
    }

    public void addPassword(PasswordRow row) {
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(row);
    }

    private void savePasswords() throws IOException {

        FileWriter fw = new FileWriter(new File("passwords.txt"));

        for (String key : sitePasses.keySet()) {
            fw.write(key + "," + encryptor.encrypt(sitePasses.get(key)) + "\n");
        }
        fw.close();

    }

    private void loadPasswords() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("passwords.txt"));

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] sitePass = line.split(",");
            String password = encryptor.decrypt(sitePass[1]);
            sitePasses.put(sitePass[0], password);
            addPassword(new PasswordRow(sitePass[0], password));
        }
        scanner.close();
    }

}