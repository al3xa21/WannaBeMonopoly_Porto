package gui;
//import client.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame
{
    private JPanel contentPane;
    private JPanel buttonsContainer;

    public JButton newGameButton;
    public JButton joinButton;
    public JButton instructionsButton;
    public JButton aboutButton;
    private JLabel background;

    ImageIcon image = new ImageIcon("src/Resources/background.png");

    public MainMenu()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100,100,1250,750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        buttonsContainer = new JPanel(new GridLayout(0, 1, 0, 5));
        buttonsContainer.setBounds(430, 300, 412, 296);
        contentPane.add(buttonsContainer);

        buttonsContainer.setOpaque(false);

        newGameButton = new JButton("New Game");
        joinButton = new JButton("Join Game");
        instructionsButton = new JButton("Instructions");
        aboutButton = new JButton("About Us");

        buttonsContainer.add(newGameButton);
        buttonsContainer.add(joinButton);
        buttonsContainer.add(instructionsButton);
        buttonsContainer.add(aboutButton);

        // background = new JLabel(new ImageIcon(Image.class.getResource("/Resources/background.png")));
        // background = new JLabel(new ImageIcon(getClass().getResource("/Resource/background.png")));
        background = new JLabel(image);

        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 1280, 710);
        contentPane.add(background);
    }

    public void mainMenuView(boolean status)
    {
        this.setVisible(status);
    }
}
