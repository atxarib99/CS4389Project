import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FriendRSAInfo extends JDialog {

    JPanel mainPanel;
    RSAEncryptor encryptor;
    public FriendRSAInfo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        this.add(mainPanel);

        buildPage();

        this.pack();
        this.setSize(525,125);
        this.setVisible(true);

    }

    private void buildPage() {
        JTextField publicKey = new JTextField("10001");
        publicKey.setSize(250,50);
        publicKey.setLocation(0,0);
        mainPanel.add(publicKey);

        JTextField n = new JTextField();
        n.setSize(250,50);
        n.setLocation(275,0);
        mainPanel.add(n);

        JButton done = new JButton("Done!");
        done.setSize(100, 25);
        done.setLocation(425, 75);
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                encryptor = new RSAEncryptor(new BigInteger(publicKey.getText(), 16), BigInteger.ZERO, new BigInteger(n.getText(), 16));     
                try {
                    encryptor.encryptFiles(null);
                } catch(IOException e) {
                    System.err.println("error encrypting files.");
                }
                finish();
            }
        });
        mainPanel.add(done);

        JButton cancel = new JButton("Cancel");
        cancel.setSize(100,25);
        cancel.setLocation(325,75);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                finish();
            }
        });
        mainPanel.add(cancel);

    }

    private void finish() {
        this.dispose();
    }
    
}
