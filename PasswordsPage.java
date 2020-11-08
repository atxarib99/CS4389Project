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
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PasswordsPage extends JFrame {

    JPanel mainPanel;
    Map<String, String> sitePasses;
    Encryptor encryptor;
    JScrollPane mainScrollPane;
    JPanel passwordsPanel;

    public PasswordsPage(Encryptor encryptor) {
        super();
        this.encryptor = encryptor;

        sitePasses = new TreeMap<>();
        try {
            loadPasswords();
        } catch (FileNotFoundException e) {
            System.err.println("PASSWORDS FILE NOT FOUND!");
        }

        buildPage();

        this.pack();
        this.setSize(850, 600);
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

    private void buildPage() {

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        passwordsPanel = new JPanel();
        passwordsPanel.setLayout(new BoxLayout(passwordsPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        this.setSize(850, 600);
        mainScrollPane = new JScrollPane(passwordsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(mainScrollPane);

        for(String key : sitePasses.keySet()) {
            addPassword(new PasswordRow(key, sitePasses.get(key)));
        }

        //note that password row elements have already been built
        JButton add = new JButton("Add Password");
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        String[] params = new String[2];
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new NewPasswordPage(PasswordsPage.this, true, params);
                if(!params[0].equals("##Canceled") && !params[1].equals("##Canceled"))
                    sitePasses.put(params[0], params[1]);
                clearPage();
                buildPage();
            }

        });
        mainPanel.add(add);
    }

    private void clearPage() {
        this.remove(mainPanel);
    }


    public void addPassword(PasswordRow row) {
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordsPanel.add(row);
    }

    private void savePasswords() throws IOException {

        FileWriter fw = new FileWriter(new File(Installer.getPasswordPath()));

        for (String key : sitePasses.keySet()) {
            fw.write(key + "," + encryptor.encrypt(sitePasses.get(key)) + "\n");
        }
        fw.close();

    }

    private void loadPasswords() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(Installer.getPasswordPath()));

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty())
                continue;
            String[] sitePass = line.split(",");
            String password = encryptor.decrypt(sitePass[1]);
            sitePasses.put(sitePass[0], password);
        }
        scanner.close();
    }

}