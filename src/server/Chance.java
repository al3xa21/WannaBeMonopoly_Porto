package server;

public class Chance extends Square
{
    public Chance(String n){
        this.name = n;
    }

    public void randomCommChest(int n, String command , int tax )
    {
        if(n == 1)
        {
            command = "Fine for drunk -50$ ";
            tax = -50;
        }
        else if(n == 2)
        {
            command = "Pay for school expenses -50$!";
            tax = -50;
        }
        else if(n == 3)
        {
            command = "Speeding fine -30$";
            tax  = -30;
        }
        else if(n == 4)
        {
            command = "The bank pays you dividends in the amount of 50$";
            tax = 50;
        }
    }
}
