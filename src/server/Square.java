package server;

public class Square
{
    protected String name;
    private int currPlayers = 0;
    private Integer[] players = new Integer[4];

    public void setName(String n){
        this.name = n;
    }

    public String getName()
    {
        return this.name;
    }

    public void addPlayers (int p)
    {
        this.currPlayers ++;
        this.players[p-1] = p;
    }

    public void subPlayers (int p)
    {
        this.currPlayers --;
        this.players[p - 1] = 0;
    }

    public int getCurrPlayers()
    {
        return this.currPlayers;
    }

    public boolean playerPresent(int p)
    {
        for(int i = 0 ; i < 4; i++)
        {
            if(p == players[i]) return true;
        }
        return false;
    }

    // these methods were implemented by other classes
    public int getPrice()
    {
        return 0;
    }

    public int getOwner()
    {
        return 0;
    }

    public int getTax()
    {
        return 0;
    }

    public void setOwner(int player)
    {
    }

    public void randomCommChest(int n ,String command, int tax)
    {
    }
}
