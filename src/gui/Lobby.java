package gui;

import javax.swing.*;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Lobby extends JFrame
{
    static final int TOWER = 1;
    static final int BALL = 2;
    static final int FRANCEZINHA = 3;
    static final int WINE = 4;

    private JPanel contentP;
    private JPanel buttonSG;
    private JLabel background;
    public JLabel nick1;
    private JLabel nick2;
    private JLabel nick3;
    private JLabel nick4;
    public JButton startGameButton;
    public JButton goBackButtonL;

    ImageIcon image = new ImageIcon("src/Resources/Lobby_Layout.png");
    Icon icon = new ImageIcon("src/Resources/arrow.png");

    public Lobby()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1250,750);
        contentP = new JPanel();
        contentP.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentP);
        contentP.setLayout(null);

        goBackButtonL = new JButton(icon);
        contentP.add(goBackButtonL);
        goBackButtonL.setBounds(585, 670, 100, 30);
        goBackButtonL.setBackground(new Color(255,255,255,0));
        goBackButtonL.setBorderPainted(false);
        goBackButtonL.setContentAreaFilled(false);
        goBackButtonL.setFocusPainted(false);

        buttonSG = new JPanel(new GridLayout(0, 1, 0,0 ));
        buttonSG.setBounds(490, 525, 300, 70);
        contentP.add(buttonSG);

        buttonSG.setOpaque(false);

        startGameButton = new JButton("Start Game");
        buttonSG.add(startGameButton);

        nick1 = new JLabel();
        nick1.setBounds(180, 200, 180, 30);
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        nick1.setBorder(border);
        nick1.setText("Loading....");

        nick2 = new JLabel();
        nick2.setBounds(425, 200, 185, 30);
        nick2.setBorder(border);
        nick2.setText("Loading....");

        nick3 = new JLabel();
        nick3.setBounds(670, 200, 185, 30);
        nick3.setBorder(border);
        nick3.setText("Loading....");

        nick4 = new JLabel();
        nick4.setBounds(915, 200, 180, 30);
        nick4.setBorder(border);
        nick4.setText("Loading....");

        contentP.add(nick1);
        contentP.add(nick2);
        contentP.add(nick3);
        contentP.add(nick4);

        background = new JLabel(image);
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 1280, 719);
        contentP.add(background);
    }

    public void lobbyView(boolean status)
    {
        this.setVisible(status);
    }

    public void updateLobby(int total_players,int playernum , String nickname, int token)
    {
        if(token == TOWER ) this.nick1.setText(nickname);
        if(token == BALL ) this.nick2.setText(nickname);
        if(token == WINE ) this.nick3.setText(nickname);
        if(token == FRANCEZINHA ) this.nick4.setText(nickname);
        //this.lobbyView(true);
    }
}
