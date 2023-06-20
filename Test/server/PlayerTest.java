package server;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest
{
    // Tokens for example
    static final int TOWER = 1;
    static final int BALL = 2;

    @Test
    public void verifyName()
    {
        String myName = "Josefina";
        Player p = new Player(myName,0,0);

        assertEquals(myName,p.getName());

        myName = "Antonieta";

        p.setName(myName);

        assertEquals(myName,p.getName());
    }

    @Test
    public void verifyToken()
    {
        int myToken = BALL;
        Player p = new Player(" Gonçalita ", myToken, 0);

        assertEquals(BALL, p.getToken());

        myToken = TOWER;
        p.setToken(myToken);

        assertEquals(myToken,p.getToken());
    }

    @Test
    public void verifyCash()
    {
        int money = 200;
        Player p = new Player("Joana Pinta ", BALL, money );

        assertEquals(money,p.getCash());

        int addMoney = 250;
        int result = money + addMoney;
        p.setCash(addMoney);

        assertEquals(result,p.getCash());
    }

    @Test
    public void verifyPos()
    {
        int myPos = 2;
        Player p = new Player("Alexo",TOWER, 0);
        p.setPos(myPos);

        assertEquals(myPos, p.getPos());
    }

    @Test
    public void verifyGameOver()
    {
        Player p = new Player("GAME OVER",TOWER, 0);

        int addMoney = 200;
        p.setCash(addMoney);

        assertEquals(0, p.getCash());
    }
}