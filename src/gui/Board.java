package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Board extends JFrame
{
    static final int TOWER = 1;
    static final int BALL = 2;
    static final int FRANCEZINHA = 3;
    static final int WINE = 4;

    private JPanel contentP;
    private JPanel buttonsPlayer;
    private JLabel background;
    private JLabel nick1, nick2, nick3, nick4;
    private JLabel cash1, cash2, cash3,cash4;

    private JPanel chatArea;
    private JLabel lblChat;
    public JTextField txtChat;
    public JTextArea taChat;
    private JTextArea taDetails;
    private JScrollPane scrollDisplay, scrollDetails;

    public JButton checkMyCardsB;
    public JButton checkMyPropertiesB;
    public JButton rollDiceB;
    public JButton quitB;

    int totalProp = 40;
    private JButton tower[] = new JButton[2], francezinha[] = new JButton[2], ball[] = new JButton[2], wine[] = new JButton[2];
    private JButton red[] = new JButton[totalProp], yellow[] = new JButton[totalProp], blue[] = new JButton[totalProp], green[] = new JButton[totalProp];
    private JButton dice1[] = new JButton[6], dice2[] = new JButton[6];

    Icon icon       = new ImageIcon("src/Resources/arrow.png");
    Icon tIcon      = new ImageIcon("src/Resources/tower.png");
    Icon fIcon      = new ImageIcon("src/Resources/francesinha.png");
    Icon bIcon      = new ImageIcon("src/Resources/ball.png");
    Icon wIcon      = new ImageIcon("src/Resources/wine.png");
    Icon redIcon    = new ImageIcon("src/Resources/red.png");
    Icon blueIcon   = new ImageIcon("src/Resources/blue.png");
    Icon yellowIcon = new ImageIcon("src/Resources/yellow.png");
    Icon greenIcon  = new ImageIcon("src/Resources/green.png");

    Icon n1 = new ImageIcon("src/Resources/1.PNG");
    Icon n2 = new ImageIcon("src/Resources/2.PNG");
    Icon n3 = new ImageIcon("src/Resources/3.PNG");
    Icon n4 = new ImageIcon("src/Resources/4.PNG");
    Icon n5 = new ImageIcon("src/Resources/5.PNG");
    Icon n6 = new ImageIcon("src/Resources/6.PNG");

    ImageIcon image = new ImageIcon("src/Resources/Board_Layout.png");

    public Board()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1288, 770);
        contentP = new JPanel();
        contentP.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentP);
        contentP.setLayout(null);

        // JPanel player buttons
        checkMyCardsB = new JButton("Check Cards");
        checkMyPropertiesB = new JButton("My Properties");
        rollDiceB = new JButton("Roll Dice");

        buttonsPlayer = new JPanel(new GridLayout(1, 1, 0, 0));
        buttonsPlayer.setBounds(70, 280, 180, 30);
        contentP.add(buttonsPlayer);
        buttonsPlayer.setOpaque(false);

        buttonsPlayer.add(rollDiceB);

        // chatPanel for chatting area
        chatArea = new JPanel();
        chatArea.setOpaque(true);
        chatArea.setBounds(10, 410, 300, 350);// 317/661
        chatArea.add(lblChat = new JLabel("Enter Chat Message:"));
        chatArea.add(txtChat = new JTextField(20));
        taChat = new JTextArea();
        taChat.setCaretPosition(0);
        taChat.setEditable(false);
        scrollDisplay = new JScrollPane(taChat);
        scrollDisplay.setOpaque(true);
        scrollDisplay.setBounds(653, 207, 380, 300);
        //chatArea.add(scrollDisplay);
        TitledBorder chatTitle = BorderFactory.createTitledBorder("Chatting Interface");
        chatArea.add(taChat);
        chatArea.setBorder(chatTitle);
        contentP.add(chatArea);

        // player nicks labels
        nick1 = new JLabel();
        nick1.setBounds(70, 60, 180, 30);
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        nick1.setBorder(border);
        nick1.setText("...");
        nick1.setOpaque(true);
        nick1.setBackground(Color.lightGray);

        nick2 = new JLabel();
        nick2.setBounds(1098, 60, 145, 30);
        nick2.setOpaque(true);
        nick2.setBackground(Color.lightGray);
        nick2.setBorder(border);
        nick2.setText("...");

        nick3 = new JLabel();
        nick3.setBounds(1098, 300, 145, 30);
        nick3.setOpaque(true);
        nick3.setBackground(Color.lightGray);
        nick3.setBorder(border);
        nick3.setText("...");

        nick4 = new JLabel();
        nick4.setBounds(1098, 535, 145, 30);
        nick4.setBorder(border);
        nick4.setText("...");
        nick4.setOpaque(true);
        nick4.setBackground(Color.lightGray);

        contentP.add(nick1);
        contentP.add(nick2);
        contentP.add(nick3);
        contentP.add(nick4);

        // Current Cash

        cash1 = new JLabel();
        cash1.setBounds(70, 120, 180, 30);
        cash1.setBorder(border);
        cash1.setText(" 0 $ ");
        cash1.setOpaque(true);
        cash1.setBackground(Color.lightGray);

        cash2 = new JLabel();
        cash2.setBounds(1098, 120, 145, 30);
        cash2.setBorder(border);
        cash2.setText(" 0 $ ");
        cash2.setOpaque(true);
        cash2.setBackground(Color.lightGray);

        cash3 = new JLabel();
        cash3.setBounds(1098, 360, 145, 30);
        cash3.setBorder(border);
        cash3.setText(" 0 $ ");
        cash3.setOpaque(true);
        cash3.setBackground(Color.lightGray);

        cash4 = new JLabel();
        cash4.setBounds(1098, 595, 145, 30);
        cash4.setBorder(border);
        cash4.setText(" 0 $ ");
        cash4.setOpaque(true);
        cash4.setBackground(Color.lightGray);

        contentP.add(cash1);
        contentP.add(cash2);
        contentP.add(cash3);
        contentP.add(cash4);

        // Buttons
        quitB = new JButton(icon);
        contentP.add(quitB);
        quitB.setBounds(70, 325, 180, 30);
        quitB.setBackground(new Color(255, 255, 255, 0));
        quitB.setBorderPainted(false);
        quitB.setContentAreaFilled(false);
        quitB.setFocusPainted(false);

        // Set tokens and colors
        for (int i = 0; i < 2; i++)
        {
            tower[i] = new JButton(tIcon);
            contentP.add(tower[i]);
            tower[i].setBounds(270, 620, 200, 100);
            tower[i].setBackground(new Color(255, 255, 255, 0));
            tower[i].setBorderPainted(false);
            tower[i].setContentAreaFilled(false);
            tower[i].setFocusPainted(false);
        }
        for (int i = 0; i < 2; i++)
        {
            ball[i] = new JButton(bIcon);
            contentP.add(ball[i]);
            ball[i].setBounds(270, 620, 200, 100);
            ball[i].setBackground(new Color(255, 255, 255, 0));
            ball[i].setBorderPainted(false);
            ball[i].setContentAreaFilled(false);
            ball[i].setFocusPainted(false);
        }
        for (int i = 0; i < 2; i++)
        {
            francezinha[i] = new JButton(fIcon);
            contentP.add(francezinha[i]);
            francezinha[i].setBounds(270, 620, 200, 100);
            francezinha[i].setBackground(new Color(255, 255, 255, 0));
            francezinha[i].setBorderPainted(false);
            francezinha[i].setContentAreaFilled(false);
            francezinha[i].setFocusPainted(false);
        }
        for (int i = 0; i < 2; i++)
        {
            wine[i] = new JButton(wIcon);
            contentP.add(wine[i]);
            wine[i].setBounds(270, 620, 200, 100);
            wine[i].setBackground(new Color(255, 255, 255, 0));
            wine[i].setBorderPainted(false);
            wine[i].setContentAreaFilled(false);
            wine[i].setFocusPainted(false);
        }

        // Colors
        for (int i = 0; i < totalProp; i++)
        {
            red[i] = new JButton(redIcon);
            contentP.add(red[i]);
            red[i].setBounds(0, 600, 100, 30);
            red[i].setBackground(new Color(255, 255, 255, 0));
            red[i].setBorderPainted(false);
            red[i].setContentAreaFilled(false);
            red[i].setFocusPainted(false);
        }

        for (int i = 0; i < totalProp; i++)
        {
            blue[i] = new JButton(blueIcon);
            contentP.add(blue[i]);
            blue[i].setBounds(0, 600, 100, 30);
            blue[i].setBackground(new Color(255, 255, 255, 0));
            blue[i].setBorderPainted(false);
            blue[i].setContentAreaFilled(false);
            blue[i].setFocusPainted(false);
        }
        for(int i = 0 ; i < totalProp  ; i++)
        {
            yellow[i] = new JButton(yellowIcon);
            contentP.add(yellow[i]);
            yellow[i].setBounds(0, 600, 100, 30);
            yellow[i].setBackground(new Color(255, 255, 255, 0));
            yellow[i].setBorderPainted(false);
            yellow[i].setContentAreaFilled(false);
            yellow[i].setFocusPainted(false);
        }
        for(int i = 0 ; i < totalProp  ; i++)
        {
            green[i] = new JButton(greenIcon);
            contentP.add(green[i]);
            green[i].setBounds(0, 600, 100, 30);
            green[i].setBackground(new Color(255, 255, 255, 0));
            green[i].setBorderPainted(false);
            green[i].setContentAreaFilled(false);
            green[i].setFocusPainted(false);
        }

        for(int i = 0 ; i < 6 ; i++)
        {
            if(i == 0){ dice1[i] = new JButton(n1); dice2[i] = new JButton(n1);}
            if(i == 1){ dice1[i] = new JButton(n2); dice2[i] = new JButton(n2);}
            if(i == 2){ dice1[i] = new JButton(n3); dice2[i] = new JButton(n3);}
            if(i == 3){ dice1[i] = new JButton(n4); dice2[i] = new JButton(n4);}
            if(i == 4){ dice1[i] = new JButton(n5); dice2[i] = new JButton(n5);}
            if(i == 5){ dice1[i] = new JButton(n6); dice2[i] = new JButton(n6);}
            contentP.add(dice1[i]);
            contentP.add(dice2[i]);
            dice1[i].setBounds(50, 600, 100, 100);
            dice2[i].setBounds(50, 600, 100, 100);
            dice1[i].setBackground(new Color(255, 255, 255, 0));
            dice2[i].setBackground(new Color(255, 255, 255, 0));
            dice1[i].setBorderPainted(false);
            dice2[i].setBorderPainted(false);
            dice1[i].setContentAreaFilled(false);
            dice2[i].setContentAreaFilled(false);
            dice1[i].setFocusPainted(false);
            dice2[i].setFocusPainted(false);
        }

        // Set Background
        background = new JLabel(image);
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 1288, 728);
        contentP.add(background);
    }

    public void updateBoard(int player,String[] names ,Integer[] tokens, Integer[] cash,int turn,int pchange,int pos, boolean buy)
    {
        this.nick1.setText(names[player-1]);
        this.cash1.setText(cash[player-1]+"$");
        if(buy == true && pchange == player)  setPropertyColors("red",pos);

        int MAX = 4;

        for(int i = 0 ; i < MAX; i++)
        {
            if(tokens[i] == null) tokens[i] = 0;
        }

        tokenID(player-1, tokens,1);
        colorID();

        if(player != turn) this.rollDiceB.setEnabled(false);

        else this.rollDiceB.setEnabled(true);

        if(player == 1)
        {
            this.nick2.setText(names[player]);
            if(buy == true && pchange == player+1)  setPropertyColors("blue",pos);
            tokenID(player, tokens,2);
            this.nick3.setText(names[player+1]);
            if(buy == true && pchange == player+2)  setPropertyColors("green",pos);
            tokenID(player+1, tokens,3);
            this.nick4.setText(names[player+2]);
            if(buy == true && pchange == player+3)  setPropertyColors("yellow",pos);
            tokenID(player+2, tokens,4);
            this.cash2.setText(cash[player]+"$");
            this.cash3.setText(cash[player+1]+"$");
            this.cash4.setText(cash[player+2]+"$");
        }
        else if(player == 2)
        {
            this.nick2.setText(names[player-2]);
            if(buy == true && pchange == player-1)  setPropertyColors("blue",pos);
            tokenID(player-2, tokens,2);
            this.nick3.setText(names[player]);
            if(buy == true && pchange == player+1)  setPropertyColors("green",pos);
            tokenID(player, tokens,3);
            this.nick4.setText(names[player+1]);
            if(buy == true && pchange == player+2)  setPropertyColors("yellow",pos);
            tokenID(player+1, tokens,4);
            this.cash2.setText(cash[player-2]+"$");
            this.cash3.setText(cash[player]+"$");
            this.cash4.setText(cash[player+1]+"$");
        }
        else if(player == 3)
        {
            this.nick2.setText(names[player-3]);
            if(buy == true && pchange == player-2)  setPropertyColors("blue",pos);
            tokenID(player-3, tokens,2);
            this.nick3.setText(names[player-2]);
            if(buy == true && pchange == player-1)  setPropertyColors("green",pos);
            tokenID(player-2, tokens,3);
            this.nick4.setText(names[player]);
            if(buy == true && pchange == player+1)  setPropertyColors("yellow",pos);
            tokenID(player, tokens,4);
            this.cash2.setText(cash[player-3]+"$");
            this.cash3.setText(cash[player-2]+"$");
            this.cash4.setText(cash[player]+"$");
        }
        else if(player == 4)
        {
            this.nick2.setText(names[player-4]);
            if(buy == true && pchange == player-3)  setPropertyColors("blue",pos);
            tokenID(player-4, tokens,2);
            this.nick3.setText(names[player-3]);
            if(buy == true && pchange == player-2)  setPropertyColors("green",pos);
            tokenID(player-3, tokens,3);
            this.nick4.setText(names[player-2]);
            if(buy == true && pchange == player-1)  setPropertyColors("yellow",pos);
            tokenID(player-2, tokens,4);
            this.cash2.setText(cash[player-4]+"$");
            this.cash3.setText(cash[player-3]+"$");
            this.cash4.setText(cash[player-2]+"$");
        }

        if(pchange != 0)   moveT(tokens[pchange-1],pos);

    }

    public void setPropertyColors(String color,int pos)
    {
        if(color == "red")
        {
            if(pos > 0 && pos < 10) red[pos].setBounds(320, 620 -(pos*60), 200, 100);
            else if(pos > 10 && pos < 20) red[pos].setBounds(290+((pos-10)*60), 55, 200, 100);
            else if(pos > 20 && pos < 30) red[pos].setBounds(860, 20 +((pos-20)*60), 200, 100);
            else red[pos].setBounds(350+(9*60) -((pos-30)*60), 580, 200, 100);
        }
        else if(color == "blue")
        {
            if(pos > 0 && pos < 10) blue[pos].setBounds(320, 620 -(pos*60), 200, 100);
            else if(pos > 10 && pos < 20) blue[pos].setBounds(290+((pos-10)*60), 55, 200, 100);
            else if(pos > 20 && pos < 30) blue[pos].setBounds(860, 20 +((pos-20)*60), 200, 100);
            else blue[pos].setBounds(350+(9*60)  -((pos-30)*60), 580, 200, 100);
        }
        else if(color == "green")
        {
            if(pos > 0 && pos < 10) green[pos].setBounds(320, 620 -(pos*60), 200, 100);
            else if(pos > 10 && pos < 20) green[pos].setBounds(290+((pos-10)*60), 55, 200, 100);
            else if(pos > 20 && pos < 30) green[pos].setBounds(860, 20 +((pos-20)*60), 200, 100);
            else green[pos].setBounds(350+(9*60)  -((pos-30)*60), 580, 200, 100);
        }
        else if(color =="yellow")
        {
            if(pos > 0 && pos < 10) yellow[pos].setBounds(320, 620 -(pos*60), 200, 100);
            else if(pos > 10 && pos < 20) yellow[pos].setBounds(290+((pos-10)*60), 55, 200, 100);
            else if(pos > 20 && pos < 30) yellow[pos].setBounds(860, 20 +((pos-20)*60), 200, 100);
            else yellow[pos].setBounds(350+(9*60)  -((pos-30)*60), 580, 200, 100);
        }
    }

    public void moveT(int t , int p)
    {
        int x = 0;
        int y = 0;

        if(p >= 0 && p < 11)
        {
            x = 270;
            y = 620-(p*60);
        }
        else if(p > 10 && p < 20)
        {
            x = 290+((p-10)*60);
            y = 20;
        }
        else if(p > 19 && p < 30)
        {
            x = 650+270;
            y = 20 + ((p-20)*60);
        }
        else if(p > 29 && p < 40)
        {
            x = 350+(9*60) - ((p-30)*60);
            y = 620;
        }

        if(t == TOWER) tower[0].setBounds(x, y, 200, 100);
        else if(t == BALL) ball[0].setBounds(x, y, 200, 100);
        else if(t == WINE) wine[0].setBounds(x, y, 200, 100);
        else if(t == FRANCEZINHA) francezinha[0].setBounds(x, y, 200, 100);
    }

    public void tokenID(int p , Integer[] t, int n)
    {
        if (t[p] == TOWER)
        {
            if(n == 1) tower[1].setBounds(70, 160, 200, 100);
            if(n == 2) tower[1].setBounds(1098, 160, 200, 100);
            if(n == 3) tower[1].setBounds(1098, 400, 200, 100);
            if(n == 4) tower[1].setBounds(1098, 635, 200, 100);
        }
        if (t[p] == BALL)
        {
            if(n == 1) ball[1].setBounds(70, 160, 200, 100);
            if(n == 2) ball[1].setBounds(1098, 160, 200, 100);
            if(n == 3) ball[1].setBounds(1098, 400, 200, 100);
            if(n == 4) ball[1].setBounds(1098, 635, 200, 100);
        }
        if (t[p] == WINE)
        {
            if(n == 1) wine[1].setBounds(70, 160, 200, 100);
            if(n == 2) wine[1].setBounds(1098, 160, 200, 100);
            if(n == 3) wine[1].setBounds(1098, 400, 200, 100);
            if(n == 4) wine[1].setBounds(1098, 635, 200, 100);
        }
        if (t[p] == FRANCEZINHA)
        {
            if(n == 1) francezinha[1].setBounds(70, 160, 200, 100);
            if(n == 2) francezinha[1].setBounds(1098, 160, 200, 100);
            if(n == 3) francezinha[1].setBounds(1098, 400, 200, 100);
            if(n == 4) francezinha[1].setBounds(1098, 635, 200, 100);
            if(n == 4) francezinha[1].setBounds(1098, 635, 200, 100);
        }
    }

    public void colorID()
    {
        red[0].setBounds(50, 160, 200, 100);
        blue[0].setBounds(1040, 160, 200, 100);
        green[0].setBounds(1040, 400, 200, 100);
        yellow[0].setBounds(1040, 635, 200, 100);

       //red[1].setBounds(350, 55, 200, 100);
    }

    public void showDiceValue(int d1, int d2, boolean show)
    {
        if(show == true)
        {
            dice1[d1-1].setBounds(500,200,200,200);
            dice2[d2-1].setBounds(700,200,200,200);
        }
        else
        {
            dice1[d1-1].setBounds(50, 600, 100, 100);
            dice2[d2-1].setBounds(50, 600, 100, 100);
        }
    }

    public void boardView(boolean status)
    {
        this.setVisible(status);
    }
}


