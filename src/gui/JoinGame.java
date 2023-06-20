package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JoinGame extends JFrame
{
    private JPanel contentP;
    private JPanel nickTextField;
    private JPanel linkTextField;
    private JPanel buttonSG;
    private JLabel background;
    public JTextField userNick;
    public JTextField userLink;
    public JButton startGameButton;
    public JButton goBackButtonJ;

    ImageIcon image = new ImageIcon("src/Resources/JoinGame_Layout.jpg");
    Icon icon = new ImageIcon("src/Resources/arrow.png");

    public JoinGame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1250,750);
        contentP = new JPanel();
        contentP.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentP);
        contentP.setLayout(null);

        goBackButtonJ = new JButton(icon);
        contentP.add(goBackButtonJ);
        goBackButtonJ.setBounds(585, 670, 100, 30);
        goBackButtonJ.setBackground(new Color(255,255,255,0));
        goBackButtonJ.setBorderPainted(false);
        goBackButtonJ.setContentAreaFilled(false);
        goBackButtonJ.setFocusPainted(false);

        buttonSG = new JPanel(new GridLayout(0, 1, 0,0 ));
        buttonSG.setBounds(490, 525, 300, 70);
        contentP.add(buttonSG);

        nickTextField = new JPanel(new GridLayout(0, 1, 0, 0));
        nickTextField.setBounds(490, 270, 300, 50);
        contentP.add(nickTextField);

        userNick = new JTextField();
        nickTextField.add(userNick);

        linkTextField = new JPanel(new GridLayout(0, 1, 0, 500));
        linkTextField.setBounds(490, 390, 300, 50);
        contentP.add(linkTextField);

        userLink = new JTextField();
        linkTextField.add(userLink);

        buttonSG.setOpaque(false);

        startGameButton = new JButton("Start Game");
        buttonSG.add(startGameButton);

        background = new JLabel(image);
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 1280, 710);
        contentP.add(background);
    }

    public void joinGameView(boolean status)
    {
        this.setVisible(status);
    }
}
