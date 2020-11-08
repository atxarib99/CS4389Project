package com.Enkryptor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.awt.Image;

public class ShowKeyDialog extends JDialog {

    JPanel mainPanel;
    private String key;
    public ShowKeyDialog(JDialog parent, boolean modal, String key) {
        super(parent, modal);
        this.key = key;

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        this.add(mainPanel);

        buildPage();

        this.pack();
        this.setSize(600,150);
        this.setVisible(true);

    }

    private void buildPage() {
        JLabel warning = new JLabel("SAVE THIS KEY. DO NOT LOSE IT. IT CANNOT BE REGENERATED FOR YOU.");
        warning.setSize(600,25);
        warning.setLocation(10,0);
        mainPanel.add(warning);

        JTextArea keyText = new JTextArea(key);
        keyText.setEditable(false);
        keyText.setSize(550,50);
        keyText.setLocation(0,25);
        mainPanel.add(keyText);

        ImageIcon imgicon = new ImageIcon(ShowKeyDialog.class.getResource("images/copy.png"));
        Image img = imgicon.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton copyButton = new JButton(new ImageIcon(img));
        copyButton.setSize(50,50);
        copyButton.setLocation(550,25);
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                StringSelection stringSelection = new StringSelection(keyText.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        mainPanel.add(copyButton);

        JButton close = new JButton("Close");
        close.setSize(100, 25);
        close.setLocation(500,75);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                finish();
            }
        });
        mainPanel.add(close);
    }

    private void finish() {
        this.dispose();
    }
    
}
