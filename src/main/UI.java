package main;

import java.util.Scanner;

public class UI
{
    //this exists
    //TODO check scrabble class

    private static Scanner in = new Scanner(System.in);

    public static Player playerInit()
    {

        System.out.println("Enter a player's name:");
        String s = in.nextLine();

        Player player = new Player(s);

        System.out.println("\n" + s + "'s Frame:\n" + player.getFrame() + "\n");

        return player;
    }

    public static void playerMove(Player player)
    {
        System.out.println(player.getPlayerName() + "'s move. Enter the co-ords (bottom left being 1,1) you would like to place the first letter of your first word in:\n");
        String s = in.nextLine();

    }
}