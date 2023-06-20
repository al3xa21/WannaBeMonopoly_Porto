package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AboutUs extends JFrame
{
    private JPanel contentP;
    private JLabel background;
    public JButton goBackButtonA;
    ImageIcon image = new ImageIcon("src/Resources/AboutUs_Layout.png");
    Icon icon = new ImageIcon("src/Resources/arrow.png");

    public AboutUs()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1280,750);
        contentP = new JPanel();
        contentP.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentP);
        contentP.setLayout(null);

        goBackButtonA = new JButton(icon);
        contentP.add(goBackButtonA);
        goBackButtonA.setBounds(583, 670, 100, 30);
        goBackButtonA.setBackground(new Color(255,255,255,0));
        goBackButtonA.setBorderPainted(false);
        goBackButtonA.setContentAreaFilled(false);
        goBackButtonA.setFocusPainted(false);

        background = new JLabel(image);

        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 1280, 719);
        contentP.add(background);
    }

    public void aboutUsView(boolean status)
    {
        this.setVisible(status);
    }
}
