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

    /*
    Returns an array where the first element (args[0]) is always the move type.
    If move type is PASS, QUIT or REFILL, no other arguments are provided.
    If move type is PLACE_WORD the other arguments are as follows
    args[1] = internal row coordinate
    args[2] = internal column coordinate
    args[3] = desired word
    args[4] = vertical boolean
     */
    public static String[] playerMove(Player player)
    {
        String[] args = new String[];
        String s = in.nextLine();
    }

    public static void error(int error) {

    }

    public static void help() {
        System.out.println("Enter the co-ords (bottom left being 1,1) you would like to place the first letter of your first word in, also enter PASS for xyz:\n");

    }

}