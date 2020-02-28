package main;

import java.util.Scanner;

public class Scrabble
{
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
