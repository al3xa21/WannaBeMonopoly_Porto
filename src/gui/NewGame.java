package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewGame extends JFrame
{
    private JPanel contentP;
    private JPanel buttonsC;
    private JPanel buttonSG;
    private JPanel textField;
    public JButton tower;
    public JButton wine;
    public JButton ball;
    public JButton francezinha;
    public JButton startGameButton;
    public JButton goBackButtonN;
    private JLabel back;
    public JTextField chooseNick;

    Icon icon = new ImageIcon("src/Resources/arrow.png");
    ImageIcon image = new ImageIcon("src/Resources/NewGame_Layout.jpg");

    public NewGame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1250,750);
        contentP = new JPanel();
        contentP.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentP);
        contentP.setLayout(null);

        goBackButtonN = new JButton(icon);
        contentP.add(goBackButtonN);
        goBackButtonN.setBounds(585, 680, 100, 30);
        goBackButtonN.setBackground(new Color(255,255,255,0));
        goBackButtonN.setBorderPainted(false);
        goBackButtonN.setContentAreaFilled(false);
        goBackButtonN.setFocusPainted(false);

        buttonsC = new JPanel(new GridLayout(1, 4, 60,0 ));
        buttonsC.setBounds(377, 540, 500, 20);
        contentP.add(buttonsC);

        buttonSG = new JPanel(new GridLayout(0, 1, 0,0 ));
        buttonSG.setBounds(490, 600, 300, 70);
        contentP.add(buttonSG);

        textField = new JPanel(new GridLayout(0, 1, 0,0 ));
        textField.setBounds(490, 270, 300, 50);
        contentP.add(textField);

        chooseNick = new JTextField();
        textField.add(chooseNick);

        buttonsC.setOpaque(false);
        buttonSG.setOpaque(false);

        tower = new JButton();
        wine  = new JButton();
        ball  = new JButton();
        francezinha     = new JButton();
        startGameButton = new JButton("Start Game");

        buttonsC.add(tower);
        buttonsC.add(ball);
        buttonsC.add(wine);
        buttonsC.add(francezinha);
        buttonSG.add(startGameButton);

        back = new JLabel(image);
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.setBounds(0, 0, 1280, 710);
        contentP.add(back);
    }

    public void newGameView(boolean status)
    {
         this.setVisible(status);
    }
}
