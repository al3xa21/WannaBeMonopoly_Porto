package server;

public class Player
{
	private String name;
	private int token;
	private int cash;
	private int pos;

	public Player(String n, int t, int c)
	{
		this.name  = n;
		this.token = t;
		this.cash  = c;
		this.pos   = 0;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String n)
	{
		this.name = n;
	}

	public int getToken()
	{
		return this.token;
	}

	public void setToken(int no)
	{
		this.token = no;
	}

	public int  getCash()
	{
		return this.cash;
	}

	public void setCash(int c)
	{
		if(this.name == "GAME OVER") this.cash = 0;
		else this.cash = this.cash + c;
	}

	public void setPos(int p)
	{
		this.pos  = p;
	}

	public int  getPos()
	{
		return this.pos;
	}

}