import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AskKeyDialog extends JDialog {

    JPanel mainPanel;
    JLabel errors;
    String[] key;
    public AskKeyDialog(java.awt.Frame parent, boolean modal, String[] key, String errormsg) {
        super(parent, modal);

        this.key = key;

        mainPanel = new JPanel();
        mainPanel.setSize(500,200);
        mainPanel.setLayout(null);
        this.setSize(500, 200);
        this.add(mainPanel);
        buildPage();
        errors.setText(errormsg);


        this.pack();
        this.setSize(500,200);
        this.setVisible(true);

    }

    private void buildPage() {

        errors = new JLabel("", SwingConstants.RIGHT);
        errors.setForeground(Color.red);
        errors.setSize(250, 50);
        errors.setLocation(250, 0);
        mainPanel.add(errors);
        errors.setVisible(true);
        
        //TODO: margins
        JLabel header = new JLabel("Enter your key:");
        header.setLocation(0, 0);
        header.setSize(250,50);
        mainPanel.add(header);
        header.setVisible(true);
        JTextField keyField = new JTextField();
        keyField.setLocation(0,50);
        keyField.setSize(500,50);
        mainPanel.add(keyField);

        JButton accept = new JButton("Go!");
        accept.setSize(100,25);
        accept.setLocation(400, 150);
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                key[0] = keyField.getText();
                finish();
            }

        });
        mainPanel.add(accept);
        
        JButton decline = new JButton("Close");
        decline.setSize(100,25);
        decline.setLocation(300,150);
        decline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        mainPanel.add(decline);
        
        JButton forgot = new JButton("Forgot?");
        forgot.setSize(100,25);
        forgot.setLocation(0,150);
        forgot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //TODO: implement some sort of wipe / restart
            }
        });
        mainPanel.add(forgot);
        mainPanel.setVisible(true);

    }

    private void finish() {
        this.dispose();
    }
    
    public void setErrorMsg(String msg) {
        errors.setText(msg);
    }
    
}
