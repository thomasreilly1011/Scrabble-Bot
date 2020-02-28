package main;

import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.Boolean.parseBoolean;


public class Scrabble
{
    public static Pool pool = new Pool();
    public static Board board = new Board(); //initialise the game

    //Move Type Constants:
    public static final int PLACE_WORD = 0;
    public static final int PASS = 1;
    public static final int REFILL = 2;
    public static final int QUIT = 3;

    public static boolean gameOver = false;

    public static void calculateScore(int row, int column, String word, Boolean vertical)
    {

    }

    public static void move(String[] strings, Player player)
    {
        if(parseInt(strings[0]) == 0)
        {
            board.placeWord(parseInt(strings[1]), parseInt(strings[2]), strings[3], player.getFrame(), parseBoolean(strings[4]));

            calculateScore(parseInt(strings[1]), parseInt(strings[2]), strings[3], parseBoolean(strings[4]));
        }
        else if(parseInt(strings[0]) == 2)
        {
            player.getFrame().refill();
        }
        else if(parseInt(strings[0]) == 3)
        {
            gameOver = true;
            //TODO maybe a UI.endGame() function call of sorts that also asks "Are you sure? (Y/N)"
        }
    }

    public static void main(String[] args)
    {
        Player player1 = UI.playerInit();
        Player player2 = UI.playerInit();

        for(int i=0; i<5; i++)
        {
            move(UI.playerMove(player1), player1);
            move(UI.playerMove(player2), player2);
        }
    }

}
