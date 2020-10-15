import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class EncryptionSelecterDialog extends JDialog {
    
    JPanel mainPanel;
    Crypters[] myCrypt;
    public EncryptionSelecterDialog(java.awt.Frame parent, boolean modal, Crypters[] myCrypt) {
        super(parent, modal);

        this.myCrypt = myCrypt;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);
        this.setSize(800,600);

        buildSelections();
        this.pack();
        this.setSize(800,600);
        this.setVisible(true);

    }

    private void buildSelections() {
        for (Crypters crypter : Crypters.values()) {
            JButton button = new JButton();
            button.setText(crypter.name());
            button.setSize(new Dimension(125,50));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    myCrypt[0] = Crypters.valueOf(button.getText());
                    //TODO: Close window
                }
            });
            mainPanel.add(button);
        }
    }

}