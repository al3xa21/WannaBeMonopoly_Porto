package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Instructions extends JFrame
{
    private JPanel contentP;
    private JLabel background;
    ImageIcon image = new ImageIcon("src/Resources/Instructions_Layout.png");
    public JButton goBackButtonI;
    Icon icon = new ImageIcon("src/Resources/arrow.png");

    public Instructions()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1280,750);
        contentP = new JPanel();
        contentP.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentP);
        contentP.setLayout(null);

        goBackButtonI = new JButton(icon);

        goBackButtonI.setBackground(new Color(255,255,255,0));
        goBackButtonI.setBorderPainted(false);
        goBackButtonI.setContentAreaFilled(false);
        goBackButtonI.setFocusPainted(false);

        contentP.add(goBackButtonI);
        goBackButtonI.setBounds(583, 670, 100, 30);

        background = new JLabel(image);

        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 1280, 727);
        contentP.add(background);
    }

    public void instructionsView(boolean status)
    {
        this.setVisible(status);
    }
}
