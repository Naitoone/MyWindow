package app;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutWindow extends JDialog {

    private JButton button;
    private JLabel l1,l2,l3,l4,l5,lBorder;

    private Font font1 = new Font("TimesRoman", Font.PLAIN, 22);
    private Font font2 = new Font("TimesRoman", Font.PLAIN, 12);
    private Font font3 = new Font("TimesRoman", Font.BOLD, 12);
    private Font font4 = new Font("TimesRoman", Font.PLAIN, 12);

    private Border line = null;



    public AboutWindow() {

        this.setTitle("Informacja o Autorze");
        this.setSize(300, 270);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutWindow.this.dispose();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        l1 = new JLabel("Aplikacja testowa");
        l1.setFont(font1);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l2 = new JLabel("wersja 1.0.1");
        l2.setFont(font2);
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        l3 = new JLabel("Dawid Wojcieszak");
        l3.setFont(font3);
        l3.setHorizontalAlignment(SwingConstants.CENTER);
        l4 = new JLabel("Politechnika Koszalinska - WEiI");
        l4.setFont(font3);
        l4.setHorizontalAlignment(SwingConstants.CENTER);
        l5 = new JLabel("e-mail: email@gmail.pl");
        l5.setFont(font4);
        lBorder = new JLabel("");
        line = new EtchedBorder(EtchedBorder.LOWERED);
        
        l1.setBounds(35,20,210,30);
        l2.setBounds(35,50,210,30);
        l3.setBounds(35,90,210,20);
        l4.setBounds(35,110,210,20);
        lBorder.setBounds(5,175,300,2);
        l5.setBounds(10,184,200,20);
        button.setBounds(200,182,60,25);

        lBorder.setBorder(line);
        this.add(l1);
        this.add(l2);
        this.add(l3);
        this.add(l4);
        this.add(l5);
        this.add(lBorder);
        this.add(button);

        this.setVisible(true);
    }


}
