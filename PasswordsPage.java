import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PasswordsPage extends JFrame {


    JPanel mainPanel;
    public PasswordsPage() {
        super();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        this.setSize(800,600);

        //temp
        addPassword(new PasswordRow("google.com", "password1"));
        addPassword(new PasswordRow("facebook.com", "password2"));
        addPassword(new PasswordRow("facebook.com", "password3"));
        addPassword(new PasswordRow("facebook.com", "password4"));
        addPassword(new PasswordRow("facebook.com", "password5"));
        addPassword(new PasswordRow("facebook.com", "password6"));
        addPassword(new PasswordRow("facebook.com", "password7"));
        addPassword(new PasswordRow("facebook.com", "password8"));
        

        this.pack();
        this.setSize(800,600);
        this.setVisible(true);
    }

    public void addPassword(PasswordRow row) {
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(row);
    }
}