package server;

public class Property extends Square
{
    private int owner = 0;
    private int price;
    private int tax;

    public Property(String name,int price, int tax)
    {
        this.name = name;
        this.price  = price;
        this.tax = tax;
    }

    @Override
    public int getOwner()
    {
        return this.owner;
    }

    @Override
    public void setOwner(int p)
    {
        this.owner = p;
    }

    @Override
    public int getTax()
    {
        return this.tax;
    }

    @Override
    public int getPrice()
    {
        return this.price;
    }
}
