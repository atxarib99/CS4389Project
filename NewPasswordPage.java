import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class NewPasswordPage extends JDialog {
    
    JPanel mainPanel;
    String[] params;
    public NewPasswordPage(JFrame parent, boolean modal, String[] params) {
        super(parent, modal);
        this.params = params;

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(550,125);
        this.add(mainPanel);

        buildPage();

        this.pack();
        this.setSize(550,125);
        this.setVisible(true);
    }

    private void buildPage() {
        // [field] [field]
        //     [button]

        JTextField site = new JTextField();
        site.setSize(250,50);
        site.setLocation(0, 0);
        mainPanel.add(site);

        JTextField password = new JTextField();
        password.setSize(250,50);
        password.setLocation(250, 0);
        mainPanel.add(password);

        ImageIcon imgicon = new ImageIcon("generate.png");
        Image img = imgicon.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton generateButton = new JButton(new ImageIcon(img));
        generateButton.setSize(new Dimension(50,50));
        generateButton.setLocation(500, 0);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                password.setText(PasswordGenerator.generate());
            }
        });
        mainPanel.add(generateButton);

        JButton add = new JButton("Add");
        add.setSize(100,25);
        add.setLocation(450,75);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                params[0] = site.getText();
                params[1] = password.getText();
                finish();
            }

        });
        mainPanel.add(add);


        JButton cancel = new JButton("Cancel");
        cancel.setSize(100,25);
        cancel.setLocation(350,75);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                params[0] = "##Canceled";
                params[1] = "##Canceled";
                finish();
            }

        });
        mainPanel.add(cancel);

        JButton checkStrength = new JButton("Check Strength");
        checkStrength.setSize(150,25);
        checkStrength.setLocation(0,75);
        checkStrength.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                
                JTextArea performanceText = new JTextArea();
                performanceText.setSize(550, 100);
                performanceText.setLocation(0,125);
                performanceText.setEditable(false);
                mainPanel.add(performanceText);
                performanceText.setText("Crunching numbers...");
                NewPasswordPage.this.setSize(550,250);
                double sec = PasswordGenerator.timeToCrack(password.getText());
                double hours = sec / 3600;
                double days = hours / 24;
                double years = days / 365.25;
                performanceText.setText(String.format("We estimate it would take...\n%.2f seconds\n%.2f hours\n%.2f days\n%.2f years\nto crack this password on this machine.", sec, hours, days, years));
            }
        });
        mainPanel.add(checkStrength);

    }

    private void finish() {
        this.dispose();
    }

}
