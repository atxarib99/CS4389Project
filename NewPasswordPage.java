import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPasswordPage extends JDialog {
    
    JPanel mainPanel;
    String[] params;
    public NewPasswordPage(JFrame parent, boolean modal, String[] params) {
        super(parent, modal);
        this.params = params;

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setSize(500,100);
        this.add(mainPanel);

        buildPage();

        this.pack();
        this.setSize(500,100);
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

        JButton add = new JButton("Add");
        add.setSize(100,25);
        add.setLocation(200,50);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                params[0] = site.getText();
                params[1] = password.getText();
                finish();
            }

        });
        mainPanel.add(add);

    }

    private void finish() {
        this.dispose();
    }

}
