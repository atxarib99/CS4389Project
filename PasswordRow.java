import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

public class PasswordRow extends JPanel {

    public PasswordRow(String site, String password) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.setPreferredSize(new Dimension(850,50));

        JTextField siteField = new JTextField();
        siteField.setPreferredSize(new Dimension(400,50));
        siteField.setMinimumSize(siteField.getPreferredSize());
        siteField.setMaximumSize(siteField.getPreferredSize());
        siteField.setText(site);
        siteField.setEditable(false);
        // siteField.setAlignmentY(Component.CENTER_ALIGNMENT);
        // siteField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(siteField);

        JTextField passwordField = new JTextField();
        passwordField.setText(password);
        passwordField.setPreferredSize(new Dimension(400,50));
        passwordField.setMinimumSize(passwordField.getPreferredSize());
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        // passwordField.setAlignmentY(Component.CENTER_ALIGNMENT);
        // passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(passwordField);


        ImageIcon imgicon = new ImageIcon("copy.png");
        Image img = imgicon.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton copyButton = new JButton(new ImageIcon(img));
        copyButton.setPreferredSize(new Dimension(50,50));
        this.add(copyButton);
        

        //TODO: Button for show/hide password

        //TODO: Button for copy


    }
}