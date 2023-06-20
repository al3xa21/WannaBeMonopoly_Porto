package client;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ClientTest
{
    Client c = new Client();
    @Test
    public void verifyChooseToken()
    {
        Random generator = new Random();
        int p1Token = generator.nextInt(10) + 5 ;   // the real id for tokens belongs int the range [1,4]
        int numPlayer = generator.nextInt(10) + 5 ; // the real id for players belongs int the range [1,4]
        int token = c.chooseToken(p1Token, numPlayer);    //token should be 0 when the token id or the clients id are out of range
        int errorToken = 0;

        assertEquals(errorToken, token);
    }
}