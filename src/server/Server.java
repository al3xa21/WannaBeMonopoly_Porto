package server;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.util.Random;

public class Server
{
	private static ArrayList<Socket> clientList = new ArrayList<Socket>();
	private static final int MIN=2;
	private static final int MAX=4;
	private static int totalPlayers;

 	public static void main(String[] args)
	{
 		while(true)
		 {
	 		String input = JOptionPane.showInputDialog(null,"Enter number of players (min: "+MIN+ " max: "+MAX+")");
	 		if (input!=null)
			 {
		 		totalPlayers = Integer.parseInt(input);
		 		if (totalPlayers >=MIN && totalPlayers <=MAX)
		 		{
		 			try
					{
		    			// create a server socket
						ServerSocket serverSocket = new ServerSocket(7000,20);

						for (int i = 1; i< totalPlayers+1; i++){
							// s2 � listen to accept connection from client
							System.out.println("server.Server is waiting for client's connection");
		      				Socket client = serverSocket.accept();
		      				// add client to ArrayList
		      				clientList.add(client);
							System.out.println("client.Client connected");
							// create a session and start the session
		    	  			Session ss = new Session(client, clientList, i, totalPlayers);
		      				ss.start();
						}
	     			}
		     		catch (IOException e)
					{
		         		System.out.println("Could not listen on port: 7000.");
		      		}
		      		break;
		 		}
		 		else
		 			JOptionPane.showMessageDialog(null, "Enter a number between 2 to 4");
	 		}
	 		else
	 			System.out.println("End of program. Thank you");
 		}
   	}
}

/****************************************************************************************************************************************/

class Session extends Thread
{
	private Square[] map = new Square[40];

	static final int TOWER = 1;
	static final int BALL = 2;
	static final int FRANCEZINHA = 3;
	static final int WINE = 4;

	private int totalPlayers;
	private int turn = 1;

	// player util.
	private ArrayList<Socket> clientList;
	private Player[] pList = new Player[4];
	private Socket client;
	private int player;

	// codes for game logic
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

    public Session(Socket client,  ArrayList<Socket> aList, int p, int n)
	{
    	this.client = client;
    	this.clientList = aList;
    	player = p;
    	totalPlayers = n;
    }

   	public void run()
	{
      	try
		{
			squareConfig();
  			DataInputStream socketIn  = new DataInputStream(client.getInputStream());
			broadcast(clientList, totalPlayers, player);
			String playerName = socketIn.readUTF();
			int token = 0;

			// Lobby
			// Player 1 update token and add himself to player List
			if(player == 1)
			{
				token = socketIn.readInt();
				pList[player-1] = new Player(playerName,token,1500);
			}

			//Player to send message to player 1 with p2 info and add himself to the pList
			if(player != 1)
			{
				sendPlayerInfo(clientList.get(0),LOBBY,player,token,playerName);
				pList[player-1] = new Player(playerName, token, 1500);
			}

			int auxP, auxT;
			String auxN;
			int cont = 1;

			while (true)
			{
				int code=socketIn.readInt();

				if(code == LOBBY)
				{
					cont++;

					if(player == 1)
					{
						// Player1 receives a message from p1client about p info and add him to the plist
						auxN  = socketIn.readUTF();
						auxP= socketIn.readInt();
						auxT = socketIn.readInt();

						pList[auxP-1] = new Player(auxN, auxT, 1500);
						// P1server sends Pclient the p2token
						sendData(clientList.get(auxP-1),LOBBY);
						sendData(clientList.get(auxP-1),auxT);
						//P1 server sends P client info about himself
						sendPlayerInfo(clientList.get(auxP-1), 0,player,token,playerName);

						//P1s sends P2c info about the new p
						if(cont > 2 )
						{
							sendPlayerInfo(clientList.get(auxP-2), LOBBY, auxP,auxT,auxN);
							if(auxP == 4) sendPlayerInfo(clientList.get(auxP-3), LOBBY, auxP,auxT,auxN);
							//Send client p info about the other players
							sendPlayerInfo(clientList.get(auxP-1), 0, auxP-1, pList[auxP-2].getToken(), pList[auxP-2].getName());
							if(auxP == 4)sendPlayerInfo(clientList.get(auxP-1), 0, auxP-2, pList[auxP-3].getToken(), pList[auxP-3].getName());
						}

					}
					else if(cont > 2)
					{
						// Player2 receives a message from p2client about p info and add him to the plist
						auxN  = socketIn.readUTF();
						auxP= socketIn.readInt();
						auxT = socketIn.readInt();
						pList[auxP-1] = new Player(auxN, auxT, 1500);
					}

					else if(player != 1)
					{
						// player2server receives from client 2 info about p1 and his token.
						auxN = socketIn.readUTF();
						auxP = socketIn.readInt();
						auxT = socketIn.readInt();
						token = socketIn.readInt();

						// p2server update playerlist
						pList[player-1].setToken(token);
						pList[auxP-1] = new Player(auxN,auxT,1500);

						if(player > 2){
							for(int i = 2 ; i < player; i ++){
								auxN = socketIn.readUTF();
								auxP = socketIn.readInt();
								auxT = socketIn.readInt();
								pList[auxP-1] = new Player(auxN,auxT,1500);
							}
						}
					}
				}

				else if(code == START_GAME)
				{
					broadcast(clientList, START_GAME);
				}
				else if (code == CHAT)
				{
					String message = socketIn.readUTF();
					broadcast(clientList, CHAT, message);
				}
				else if(code == ROLL_DICE)
				{
					boolean startReward = false;

					if(player == turn)
					{
						Random generator = new Random();
						int random_n1 = generator.nextInt(6) + 1 ;
						System.out.println("Dado 1 sai nº "+ random_n1);
						int random_n2 = generator.nextInt(6) + 1;
						System.out.println("Dado 2 sai nº"+ random_n1);
						int newPos = pList[player-1].getPos() + random_n1 + random_n2;

						if(newPos > 39) {
							newPos = newPos - 40;
							pList[player-1].setCash(200);
							startReward = true;
						}

						pList[player-1].setPos(newPos);
						broadcast(clientList,ROLL_DICE,player,pList[player-1].getPos(),startReward);
						sendData(clientList.get(player-1),random_n1);
						sendData(clientList.get(player-1),random_n2);

						property_handler(newPos);
						//commChest_handler(newPos);
					}
					else System.out.println("Not player turn!");

					if(turn == totalPlayers) turn = 1;
					else turn++;
				}
				else if(code == UPDATE)
				{
					auxP = socketIn.readInt();
					int pos = socketIn.readInt();
					int cash = socketIn.readInt();
					String buy = socketIn.readUTF();
					String tax = socketIn.readUTF();
					String gameOver = socketIn.readUTF();

					if(buy.equals("true"))
					{
						map[pos].setOwner(auxP);
						pList[auxP-1].setCash(cash - map[pos].getPrice());
					}
					else if(tax.equals("true") )
					{
						pList[auxP-1].setCash(map[pos].getTax()*(-1));
						pList[map[pos].getOwner()-1].setCash(map[pos].getTax());
					}
					else if(gameOver.equals("true"))
					{
						pList[auxP-1].setName("GAME OVER");
					}
					else  pList[auxP-1].setCash(cash);

					pList[auxP-1].setPos(pos);

					if(turn == totalPlayers) turn = 1;
					else turn++;
				}
			}
      	}
      	catch (IOException e)
		{
         	System.out.println("Could not listen on port: 7000.");
      	}
      	System.exit(0);
   	}

	public void	property_handler(int p) throws IOException
	{
		DataInputStream socketIn  = new DataInputStream(client.getInputStream());
		try
		{
			if(p == 1 || p == 3 || p == 6 || p == 8 || p == 9 || p == 11 || p == 13 || p == 14 || p == 16 || p == 18 || p == 19 || p == 21 || p == 23 || p == 24 || p == 26 || p == 27 || p == 29 || p == 31 || p == 32 || p == 34 || p == 37 || p == 39)
			{
				if(map[p].getOwner() == 0) broadcast(clientList, map[p].getOwner() ,map[p].getName(),map[p].getPrice());
				else broadcast(clientList, map[p].getOwner() ,map[p].getName(),map[p].getTax());

				int result = socketIn.readInt();

				if(result == BUY)
				{
					if(pList[player-1].getCash() - map[p].getPrice() < 0 ) broadcast(clientList, BROKE);
					else
					{
						map[p].setOwner(player);
						pList[player-1].setCash(map[p].getPrice()*(-1));
						broadcast(clientList,BUY,pList[player-1].getCash());
					}
				}
				else if(result == PAY_TAX)
				{
					if(pList[player-1].getCash() - map[p].getTax() < 0 )
					{
						pList[player-1].setName("GAME_OVER");
						broadcast(clientList, GAME_OVER);
					}
					else
					{
						pList[player-1].setCash(map[p].getTax() * (-1));
						pList[map[p].getOwner()-1].setCash(map[p].getTax());
						broadcast(clientList, PAY_TAX,pList[player-1].getCash(),pList[map[p].getOwner()-1].getCash());
					}
				}
				else broadcast(clientList, CONTINUE);
			}
		}
		catch (IOException e)
		{
			System.out.println("Could not listen on port: 7000.");
		}
	}

	public void commChest_handler(int p) throws IOException
	{
		DataInputStream socketIn  = new DataInputStream(client.getInputStream());
		Random generator = new Random();

		if(p == 2 || p == 17)
		{
			int rand = generator.nextInt(4) + 1 ;
			String command = null;
			int value = 0;
			map[p].randomCommChest(rand, command, value);

			sendUTF(clientList.get(player-1), command);
			broadcast(clientList, value);
		}
	}

	public void squareConfig()
	{
		map[0]  = new Property("Start", 0, 0);
		map[1]  = new Property("Rua Dr Julio Matos", 60, 20);
		map[2]  = new CommChest("CommChest1");
		map[3]  = new Property("Rua das Flores", 60, 20);
		map[4]  = new Property("Income Tax", 0, 200);
		map[5]  = new Property("Santo Ovideo", 200, 20);
		map[6]  = new Property("Rua Dr. Roberto Frias", 100, 40);
		map[7]  = new Chance("Chance1");
		map[8]  = new Property("Rua Formosa", 100, 50);
		map[9]  = new Property("Rua de Sa da Bandeira",120, 50);
		map[10] = new Prison("Prison", false);
		map[11] = new Property("Rua de 31 de Janeiro", 140, 60);
		map[12] = new Property("Electric Company", 150, 65);
		map[13] = new Property("Rua do Sol", 140, 60);
		map[14] = new Property("Rua de Sao Joao", 160, 70);
		map[15] = new Property("Sao Bento", 200, 80);
		map[16] = new Property("Rua da Bolsa", 180, 75);
		map[17] = new CommChest("CommChest2");
		map[18] = new Property("Rua de Cedofeita", 180, 65);
		map[19] = new Property("Rua de Santa Catarina", 200, 85);
		map[20] = new FreeParking("Free Parking");
		map[21] = new Property("Rua Galeria de Paris", 220, 90);
		map[22] = new Chance("Chance2");
		map[23] = new Property("Rua de Pedro Hispano", 220, 80);
		map[24] = new Property("Rua do Teatro", 240, 100);
		map[25] = new Property("Estadio do Dragao", 200, 85);
		map[26] = new Property("Rua de Sao Mgiuel", 260, 110);
		map[27] = new Property("Rua de Candido Reis", 260, 100);
		map[28] = new Property("Water Works", 150, 65);
		map[29] = new Property("Rua das Taipas", 280, 120);
		map[30] = new Prison("Go to Prison",true);
		map[31] = new Property("Avenida Fernao Magalhaes", 300, 130);
		map[32] = new Property("Avenida de Franca", 300, 110);
		map[33] = new CommChest("CommChest3");
		map[34] = new Property("Avenida de Montevideu", 320, 125);
		map[35] = new Property("Aeroporto", 200, 75);
		map[36] = new Chance("Chance3");
		map[37] = new Property("Avenida da Boavista", 350, 140);
		map[38] = new Property("Luxury Tax", 0, 100);
		map[39] = new Property("Avenida dos Aliados", 400, 165);
	}

	public void sendPlayerInfo(Socket con, int n,int p,  int t, String name)
	{
		try
		{
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeInt(n);
			out.writeInt(p);
			out.writeInt(t);
			out.writeUTF(name);
		}
		catch (Exception e){}
	}

	public void sendData(Socket con, int n)
	{
		try
		{
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeInt(n);
		}
		catch (Exception e){}
	}

	public void sendUTF(Socket con, String n){

		try
		{
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeUTF(n);
		}
		catch (Exception e){}
	}

    public void broadcast(ArrayList<Socket> aList, int n1)
	{
		try
		{
	    	for (Socket s : aList)
			{
				DataOutputStream socketOut = new DataOutputStream(s.getOutputStream());
				socketOut.writeInt(n1);
	    	}
		}
		catch (Exception e){}
    }

    public void broadcast(ArrayList<Socket> aList, int n1, int n2)
	{
		try
		{
    		for (Socket s : aList)
			{
				DataOutputStream socketOut = new DataOutputStream(s.getOutputStream());
				socketOut.writeInt(n1);
				socketOut.writeInt(n2);
	    	}
		}
		catch (Exception e){}
    }
	public void broadcast(ArrayList<Socket> aList, int n1, int n2,int n3)
	{
		try{
			for (Socket s : aList)
			{
				DataOutputStream socketOut = new DataOutputStream(s.getOutputStream());
				socketOut.writeInt(n1);
				socketOut.writeInt(n2);
				socketOut.writeInt(n3);
			}
		}
		catch (Exception e){}
	}
    public void broadcast(ArrayList<Socket> aList, int n1, int n2, int n3, boolean n4)
	{
		try
		{
	    	for (Socket s : aList)
			{
				DataOutputStream socketOut = new DataOutputStream(s.getOutputStream());
				socketOut.writeInt(n1);
				socketOut.writeInt(n2);
				socketOut.writeInt(n3);
				socketOut.writeBoolean(n4);
    		}
		}
		catch (Exception e){}
    }

	// buy / tax
	public void broadcast(ArrayList<Socket> aList , int owner, String name, int cost)
	{
		try
		{
			for (Socket s : aList)
			{
				DataOutputStream socketOut = new DataOutputStream(s.getOutputStream());
				socketOut.writeInt(owner);
				socketOut.writeUTF(name);
				socketOut.writeInt(cost);
			}
		}
		catch (Exception e) {}
	}

    public void broadcast(ArrayList<Socket> aList, int code, String str)
	{
		try
		{
	    	for (Socket s : aList)
			{
				DataOutputStream socketOut = new DataOutputStream(s.getOutputStream());
				socketOut.writeInt(code);
				socketOut.writeUTF(str);
	    	}
		}
		catch (Exception e){}
    }
}
