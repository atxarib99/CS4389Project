import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;

public class PasswordRow extends JPanel {

    public PasswordRow(String site, String password) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        this.setSize(new Dimension(800,200));

        JTextField siteField = new JTextField();
        siteField.setSize(new Dimension(400,200));
        siteField.setText(site);
        siteField.setEditable(false);
        // siteField.setAlignmentY(Component.CENTER_ALIGNMENT);
        // siteField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(siteField);

        JTextField passwordField = new JTextField();
        passwordField.setText(password);
        passwordField.setSize(new Dimension(400,200));
        // passwordField.setAlignmentY(Component.CENTER_ALIGNMENT);
        // passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(passwordField);

        //TODO: Button for show/hide password

        //TODO: Button for copy


    }
}