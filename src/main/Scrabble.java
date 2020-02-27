package main;

import java.util.Scanner;

public class Scrabble
{

    public static void main(String[] args)
    {
        Pool pool = new Pool();
        Board board = new Board();

        Scanner in = new Scanner(System.in);

        System.out.println("Enter Player 1's name:");
        String s1 = in.nextLine();

        System.out.println("Enter Player 2's name:");
        String s2 = in.nextLine();

        Player player1 = new Player(s1);
        Player player2 = new Player(s2);

        System.out.println("\n" + s1 + "'s Frame:\n" + player1.getFrame());
        System.out.println(s2 + "'s Frame:\n" + player2.getFrame());



        for(int i=0; i<10; i++)
        {
            UI.playersmove();
        }
    }

}
