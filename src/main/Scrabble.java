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

    //TEMP: (Note from Thomas) I replaced some ints with constant names for readability and added a move call for the error case
    public static void move(String[] strings, Player player)
    {
        if(parseInt(strings[0]) == PLACE_WORD)
        {
            int i = board.placeWord(parseInt(strings[2]), parseInt(strings[3]), strings[1], player.getFrame(), parseBoolean(strings[4]));
            if(i != Board.SUCCESS)
            {
                UI.error(i, player);
                move(UI.playerMove(player), player);
            }
        }
        else if(parseInt(strings[0]) == REFILL)
        {
            player.getFrame().refill();
        }
        else if(parseInt(strings[0]) == QUIT)
        {
            //TEMP: (Note from Thomas) I added the UI function for checking endgame.
            gameOver = UI.endGame();
            if (gameOver == false)
            {
                move(UI.playerMove(player), player);
            }
        }
    }

    //TEMP: (Note from Thomas) I added the print board call in the game loop. This makes the console log less clogged up and more readable. Can be changed in the future.
    public static void main(String[] args)
    {
        Player player1 = UI.playerInit();
        Player player2 = UI.playerInit();

        for(int i=0; i<5; i++)
        {
            UI.printBoard();
            move(UI.playerMove(player1), player1);
            System.out.println(board.score);
            UI.printBoard();
            move(UI.playerMove(player2), player2);
            System.out.println(board.score);
        }
    }

}
