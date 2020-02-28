package main;

import java.util.Scanner;



public class Scrabble
{
    //Move Type Constants:
    public static final int PLACE_WORD = 0;
    public static final int PASS = 1;
    public static final int REFILL = 2;
    public static final int QUIT = 3;

    public boolean gameOver = false;

    public static void main(String[] args)
    {
        Pool pool = new Pool();
        Board board = new Board(); //initialise the game

        Player player1 = UI.playerInit();
        Player player2 = UI.playerInit();

        for(int i=0; i<5; i++)
        {
            UI.playerMove(player1);
            UI.playerMove(player2);
        }
    }

}
