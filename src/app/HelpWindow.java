package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HelpWindow extends JDialog {

    private JButton button;

    public HelpWindow() {

        this.setTitle("Pomoc");
        this.setSize(300,300);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HelpWindow.this.dispose();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        this.add(button);
        this.setVisible(true);



    }


}
