package com.Enkryptor;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        this.add(siteField);

        JTextField passwordField = new JTextField();
        passwordField.setText(password);
        passwordField.setPreferredSize(new Dimension(400,50));
        passwordField.setMinimumSize(passwordField.getPreferredSize());
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        this.add(passwordField);


        ImageIcon imgicon = new ImageIcon("copy.png");
        Image img = imgicon.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton copyButton = new JButton(new ImageIcon(img));
        copyButton.setPreferredSize(new Dimension(50,50));
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                StringSelection stringSelection = new StringSelection(passwordField.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });
        this.add(copyButton);
        

        //TODO: Button for show/hide password

    }
}