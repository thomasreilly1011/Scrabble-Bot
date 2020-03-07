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
        int length = word.length();


    }

    public static void move(String[] strings, Player player)
    {
        if(parseInt(strings[0]) == 0)
        {
            int i = board.placeWord(parseInt(strings[2]), parseInt(strings[3]), strings[1], player.getFrame(), parseBoolean(strings[4]));
            if(i == 5)
            {
                calculateScore(parseInt(strings[2]), parseInt(strings[3]), strings[1], parseBoolean(strings[4]));
            }
            else
            {
                UI.error(i, player);
            }
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
            System.out.println(board.score);
            move(UI.playerMove(player2), player2);
        }
    }

}
