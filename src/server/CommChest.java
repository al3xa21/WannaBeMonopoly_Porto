package server;

public class CommChest extends Square {

    public CommChest(String name){
        this.name = name;
    }

    @Override
    public void randomCommChest(int n, String command , int tax )
    {
        if(n == 1)
        {
            command = "Pay your insurance: -100$ ";
            tax = -100;
        }
        else if(n == 2)
        {
            command = "Today is your birthday !! You received 20$ !";
            tax = 20;
        }
        else if(n == 3)
        {
            command = "You came in 2nd place in a beauty contest! You received 30$ ";
            tax  = 30;
        }
        else if(n == 4)
        {
            command = "Pay your medical bills: -100$ ";
            tax = -100;
        }
    }
}
