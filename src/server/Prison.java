package server;

public class Prison extends Square
{
    boolean police;

    public Prison(String name, boolean p)
    {
        this.name   = name;
        this.police = p;
    }
}
