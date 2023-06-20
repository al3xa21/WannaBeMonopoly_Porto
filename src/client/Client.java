package client;
import gui.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import static java.lang.Thread.sleep;

public class Client extends JFrame implements ActionListener
{
	private static Socket client;
	private static DataInputStream dIn;
	private static DataOutputStream dOut;
	private static String    ipAddressServer = "";

	static final int TOWER       = 1;
	static final int BALL        = 2;
	static final int FRANCEZINHA = 3;
	static final int WINE        = 4;

	// Init Gui
	private static MainMenu     newMenu      = new MainMenu();
	private static NewGame      newGame      = new NewGame();
	private static Instructions instructions = new Instructions();
	private static AboutUs      aboutus      = new AboutUs();
	private static JoinGame     newJoinGame  = new JoinGame();
	private static Board        newBoard;
	private static Lobby        newLobby;

	// Aux var
	private static int       maxPlayers = 4;
	private static boolean   stop = false;
	private static int       totalPlayers;
	private static int       currNPlayers = 1;
	private static int       turn = 0;
	private static boolean   buy = false;
	private static boolean   tax = false;
	private static boolean   gameOver = false;

	// vectors to store other players info
	Integer[] pTokens = new Integer[maxPlayers];
	Integer[] pCash   = new Integer[maxPlayers];
	String[]  pNames  = new String[maxPlayers];

	// Declaring client
	private static int    token = 0;
	private static String nickname;
	private static int    player;

	// CODES for the game update
	private final int LOBBY      = 1234;
	private final int START_GAME = 1235;
	private final int ROLL_DICE  = 1236;
	private final int UPDATE     = 1237;
	private final int BUY        = 1238;
	private final int CONTINUE   = 1239;
	private final int BROKE      = 1300;
	private final int PAY_TAX    = 1301;
	private final int GAME_OVER  = 1302;
	private final int CHAT       = 89;

    public Client()
 	{
		newLobby = new Lobby();
		newLobby.goBackButtonL.addActionListener(this);
		newLobby.startGameButton.addActionListener(this);

		newBoard = new Board();
		newBoard.txtChat.addActionListener(this);
		newBoard.rollDiceB.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == newBoard.txtChat)
		{   // Execute the method in the actionPerformed if the client type something in the txtChat
			sendData(CHAT);
			String message = nickname +">>"+newBoard.txtChat.getText();
			newBoard.txtChat.setText("");
			sendMessage(message+"\n");
		}
		else if(e.getSource() == newLobby.goBackButtonL)
		{
			newLobby.lobbyView(false);
			newMenu.mainMenuView(true);
		}
		else if(e.getSource() == newLobby.startGameButton)
		{
			if(player == 1)
			{
				if(currNPlayers < totalPlayers) JOptionPane.showMessageDialog(null, "Wait for other players!!");
				else sendData(START_GAME);
			}
			else if(player != 1) JOptionPane.showMessageDialog(null, "Your are not the Leader of this lobby!");
		}
		else if(e.getSource() == newBoard.rollDiceB)
		{
			if(player == turn)sendData(ROLL_DICE);
		}
   }

   public static void sendData(int n)
   {
    	try
		{
    		// Read primitive data type(Data) from the server
    		dOut.writeInt(n);
    	}
    	// Catch I/O exception
    	catch(IOException e){}
    }

    public static void sendMessage(String message)
	{
		try
		{
			// Read primitive data type(Message) from the server
			dOut.writeUTF(message);
		}
		// Catch I/O exception
		catch(IOException e){}
	}

	public static void main(String[] args) throws InterruptedException
	{
		// Connecting to the server
		newMenu.mainMenuView(true);

		Buttons b = new Buttons();
		newMenu.newGameButton.addActionListener(b);
		newMenu.joinButton.addActionListener(b);
		newMenu.aboutButton.addActionListener(b);
		newMenu.instructionsButton.addActionListener(b);
		newGame.goBackButtonN.addActionListener(b);
		instructions.goBackButtonI.addActionListener(b);
		aboutus.goBackButtonA.addActionListener(b);
		newJoinGame.goBackButtonJ.addActionListener(b);
		newGame.startGameButton.addActionListener(b);
		newGame.ball.addActionListener(b);
		newGame.wine.addActionListener(b);
		newGame.tower.addActionListener(b);
		newGame.francezinha.addActionListener(b);
		newJoinGame.startGameButton.addActionListener(b);

		while(stop == false) { sleep(100);}

		try
		{
			client = new Socket(ipAddressServer, 7000);
			dIn    = new DataInputStream(client.getInputStream());
			dOut   = new DataOutputStream(client.getOutputStream());
			totalPlayers = dIn.readInt();
			player = dIn.readInt();
			dOut.writeUTF(nickname);
			if(player == 1)dOut.writeInt(token);

			Client t = new Client();
			t.newLobby.lobbyView(true);

			if(player == 1) t.newLobby.updateLobby(totalPlayers,player,nickname,token);

			t.process();
		}
		catch (Exception e) { System.out.println(e.getMessage());}
	}

	public int chooseToken(int p1Token, int numP)
	{
		int t = 0;

		if(p1Token == TOWER)
		{
			if(numP == 2) t = BALL;
			if(numP == 3) t = WINE;
			if(numP == 4) t = FRANCEZINHA;
		}
		else if(p1Token == BALL)
		{
			if(numP == 2) t = TOWER;
			if(numP == 3) t = WINE;
			if(numP == 4) t = FRANCEZINHA;
		}
		else if(p1Token == WINE)
		{
			if(numP == 2) t = TOWER;
			if(numP == 3) t = BALL;
			if(numP == 4) t = FRANCEZINHA;
		}
		else if(p1Token == FRANCEZINHA)
		{
			if(numP == 2) t = TOWER;
			if(numP == 3) t = BALL;
			if(numP == 4) t = WINE;
		}

		return t;
	}

    // Method for Moving, Winning, Starting, Quiting, Chatting process
	public void process()
	{
		int cont = 1;

		// aux var to store for a short time other players info
		int p;
		int t;
		String n;

		// store the current client info
		if(player == 1) pTokens[player-1] = token;
		pNames[player-1] = nickname;

		while (true)
		{
			try
			{
				int code = dIn.readInt();
				System.out.println(code);

				if (code == LOBBY)
				{
					cont++;

					if (player == 1)
					{
						// P1 client receives info from p2 server with p2 server info
						p = dIn.readInt();
						t = dIn.readInt();
						n = dIn.readUTF();
						pNames[p - 1] = n;
						pTokens[p - 1] = chooseToken(token, p);
						currNPlayers++;

						newLobby.updateLobby(totalPlayers, p, n, pTokens[p - 1]);
						//P1 client sends P1 server info about p2
						sendPId(p, pTokens[p - 1], n);
					}
					else if (cont > 2)
					{
						//P receives from sp1 info about p+1
						p = dIn.readInt();
						t = dIn.readInt();
						n = dIn.readUTF();
						pNames[p - 1] = n;
						//pTokens[p - 1] = chooseToken(token, p);
						pTokens[p-1] = t;
						currNPlayers++;

						newLobby.updateLobby(totalPlayers, p, n, pTokens[p - 1]);
						//P client sends P server info about p+1
						sendPId(p, pTokens[p - 1], n);
					}
					else if (player != 1)
					{
						//P client receives info from p1server (token attribution) and p1info
						token = dIn.readInt();
						pTokens[player - 1] = token;
						int k = dIn.readInt();
						p = dIn.readInt();
						pTokens[p - 1] = dIn.readInt();
						pNames[p - 1] = dIn.readUTF();
						currNPlayers++;

						newLobby.updateLobby(totalPlayers, player, nickname, pTokens[player - 1]);
						newLobby.updateLobby(totalPlayers, p, pNames[p - 1], pTokens[p - 1]);

						//P sends pserver the info about his new token and the player1 info
						sendPId(p, pTokens[p - 1], pNames[p - 1]);
						sendData(token);

						//P CLient receives info about the other players and send that info to the server
						if (player > 2)
						{
							for (int i = 2; i < player; i++)
							{
								k = dIn.readInt();
								p = dIn.readInt();
								pTokens[p - 1] = dIn.readInt();
								pNames[p - 1] = dIn.readUTF();
								currNPlayers++;
								newLobby.updateLobby(totalPlayers, p, pNames[p - 1], pTokens[p - 1]);
								sendMessage(pNames[p - 1]);
								sendData(p);
								sendData(pTokens[p - 1]);
							}
						}
					}
				}
				else if(code == START_GAME)
				{
					turn = 1;

					for(int i = 0; i < totalPlayers; i++){
						pCash[i] = 1500;
					}
					newLobby.lobbyView(false);
					newBoard.updateBoard(player, pNames,pTokens,pCash,turn,0,0,false);
					newBoard.boardView(true);
				}
				else if (code == CHAT)
				{
					String msg = dIn.readUTF();
					newBoard.taChat.append(msg);
					newBoard.taChat.setCaretPosition(newBoard.taChat.getText().length());
				}

				else if (code == ROLL_DICE)
				{
					p = dIn.readInt();
					int pos = dIn.readInt();
					boolean startReward = dIn.readBoolean();

					if(player == turn)
					{
						int firstDice  = dIn.readInt();
						int secondDice = dIn.readInt();
						int sum = firstDice + secondDice;
						newBoard.showDiceValue(firstDice,secondDice,true);
						JOptionPane.showMessageDialog(null, "You advanced " + sum + " in total!");
						newBoard.showDiceValue(firstDice,secondDice,false);
					}

					if(startReward == true )
					{
						if(player == turn) JOptionPane.showMessageDialog(null,"YOU RECEIVED 200$!");
						pCash[p-1] = pCash[p-1] + 200;
					}

					propertyHandler(p , pos);

					if(player != turn)
					{
						sendData(UPDATE);

						if( buy == true)
						{
							if(startReward == true) sendPlayerUpdate(p, pos, 200, "true","false","false");
							else sendPlayerUpdate(p, pos,0,"true","false","false");
						}
						else if (tax == true)
						{
							if(startReward == true) sendPlayerUpdate(p, pos, 200, "false","true","false");
							else sendPlayerUpdate(p, pos,0,"false","true","false");
						}
						else if(gameOver == true)
						{
							if(startReward == true) sendPlayerUpdate(p, pos, 200, "false","false","true");
							else sendPlayerUpdate(p, pos,0,"false","false","true");
						}
						else
						{
							if(startReward == true) sendPlayerUpdate(p, pos, 200, "false","false","false");
							else sendPlayerUpdate(p, pos,0,"false","false","false");
						}
					}

					if(turn == totalPlayers) turn = 1;
					else turn++;

					newBoard.updateBoard(player,pNames,pTokens,pCash,turn,p,pos,buy);

					buy = false;
					tax = false;
					gameOver = false;
				}
			}
			catch (Exception e) { System.out.println("Exception: " + e.getMessage());}
		}
	}

	public void propertyHandler(int currPlayer, int p)
	{
		try{
			if(p == 1 || p == 3 || p == 6 || p == 8 || p == 9 || p == 11 || p == 13 || p == 14 || p == 16 || p == 18 || p == 19 || p == 21 || p == 23 || p == 24 || p == 26 || p == 27 || p == 29 || p == 31 || p == 32 || p == 34 || p == 37 || p == 39)
			{
				int owner = dIn.readInt();
				String propName = dIn.readUTF();
				int money = dIn.readInt();
				int result;

				if(owner == 0  && player == turn)
				{
					result = JOptionPane.showConfirmDialog(null,"Do you want to purchase " + propName  + " for " + money + "$ ?","Buy property:",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION)
					{
						sendData(BUY);

						int status = dIn.readInt();

						if(status == BROKE ) JOptionPane.showMessageDialog(null, "You do not have enough money!!");
						else
						{
							pCash[player-1] = dIn.readInt();
							buy = true;
						}
					}
					else sendData(CONTINUE);
				}
				if(owner == 0 && player != turn)
				{
					result = dIn.readInt();
					if(result == BUY)
					{
						pCash[currPlayer-1] = dIn.readInt();
						buy = true;
					}
				}

				if(owner != 0 && player == turn)
				{
					if(player != owner)
					{
						JOptionPane.showMessageDialog(null,"You need to pay taxes to player "+ pNames[owner-1]+"!");
						sendData(PAY_TAX);
						result = dIn.readInt();

						if(result == GAME_OVER)
						{
							JOptionPane.showMessageDialog(null, "You do not have enough money! The game is over for you!");
							pNames[player-1] = "GAME_OVER";
							pCash[player-1] = 0;
						}
						else if( result == PAY_TAX)
						{
							pCash[player-1] = dIn.readInt();
							pCash[owner-1]  = dIn.readInt();
						}
					}
				}

				if(owner != 0 && player != turn)
				{
					if(currPlayer != owner)
					{

						if(player == owner) JOptionPane.showMessageDialog(null,"You received "+ money+"$ from player "+ pNames[currPlayer-1]);
						result = dIn.readInt();

						if(result == GAME_OVER){
							pNames[currPlayer-1] = "GAME_OVER";
							pCash[owner-1] = 0;
							gameOver = true;
						}
						else if( result == PAY_TAX){
							pCash[currPlayer-1] = dIn.readInt();
							pCash[owner-1]  = dIn.readInt();
							tax = true;
						}
					}
				}
			}
		}
		catch (Exception e) { System.out.println("Exception: " + e.getMessage());}
	}

	public void sendPId( int p ,int t ,String n)
	{
		sendData(LOBBY);
		sendMessage(n);
		sendData(p);
		sendData(t);
	}


    public void sendPlayerUpdate(int p , int pos, int cash, String buy , String tax, String pSatus)
	{
		sendData(p);
		sendData(pos);
		sendData(cash);
		sendMessage(buy);
		sendMessage(tax);
		sendMessage(pSatus);
    }


	private static class Buttons implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == newMenu.newGameButton)
			{
				newMenu.mainMenuView(false);
				newGame.newGameView(true);

			}
			else if (e.getSource() == newMenu.joinButton)
			{
				newMenu.mainMenuView(false);
				newJoinGame.joinGameView(true);

			}
			else if (e.getSource() == newMenu.instructionsButton)
			{
				newMenu.mainMenuView(false);
				instructions.instructionsView(true);

			}
			else if (e.getSource() == newMenu.aboutButton)
			{
				newMenu.mainMenuView(false);
				aboutus.aboutUsView(true);
			}
			else if(e.getSource() == newGame.goBackButtonN)
			{
				newGame.newGameView(false);
				newMenu.mainMenuView(true);
			}
			else if(e.getSource() == aboutus.goBackButtonA)
			{
				aboutus.aboutUsView(false);
				newMenu.mainMenuView(true);
			}
			else if(e.getSource() == instructions.goBackButtonI)
			{
				instructions.instructionsView(false);
				newMenu.mainMenuView(true);
			}
			else if(e.getSource() == newJoinGame.goBackButtonJ)
			{
				newJoinGame.joinGameView(false);
				newMenu.mainMenuView(true);
			}

			// Tokens
			else if(e.getSource() == newGame.ball)
			{
				newGame.tower.setEnabled(true);
				newGame.francezinha.setEnabled(true);
				newGame.wine.setEnabled(true);
				newGame.ball.setEnabled(false);
				token = BALL;
			}
			else if(e.getSource() == newGame.tower)
			{
				newGame.ball.setEnabled(true);
				newGame.francezinha.setEnabled(true);
				newGame.wine.setEnabled(true);

				newGame.tower.setEnabled(false);
				token = TOWER;
			}
			else if(e.getSource() == newGame.wine)
			{
				newGame.tower.setEnabled(true);
				newGame.francezinha.setEnabled(true);
				newGame.ball.setEnabled(true);

				newGame.wine.setEnabled(false);
				token = WINE;
			}
			else if(e.getSource() == newGame.francezinha)
			{
				newGame.tower.setEnabled(true);
				newGame.ball.setEnabled(true);
				newGame.wine.setEnabled(true);

				newGame.francezinha.setEnabled(false);
				token = FRANCEZINHA;
			}

			// ConnectServer
			else if(e.getSource() == newGame.startGameButton)
			{
				nickname = newGame.chooseNick.getText();

				if(nickname.length() < 1){
					JOptionPane.showMessageDialog(null, "You need to choose a nickname!");
					newGame.newGameView(true);
				}
				else if(token == 0){
					JOptionPane.showMessageDialog(null, "You need to choose a token!");
				}
				else {
					newGame.newGameView(false);
					stop = true;
				}

			}
			else if(e.getSource() == newJoinGame.startGameButton)
			{
				nickname = newJoinGame.userNick.getText();
				ipAddressServer = newJoinGame.userLink.getText();

				if(nickname.length() < 1)
				{
					JOptionPane.showMessageDialog(null, "You need to choose a nickname!");
				}
				else
				{
					newJoinGame.joinGameView(false);
					stop = true;
				}
			}
		}
	}
}