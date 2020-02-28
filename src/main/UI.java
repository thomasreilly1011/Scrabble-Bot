package main;

import java.util.Scanner;

public class UI
{
    private static Scanner in = new Scanner(System.in);

    static Player playerInit()
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
    static String[] playerMove(Player player)
    {
        String[] args = new String[5];
        String input;
        System.out.println("It is " + player.getPlayerName() + "'s turn!");
        System.out.println("Type 'HELP' to see instructions again.");
        while (true)
        {
            while (true)
            {
                input = in.nextLine();
                String[] inputArr= input.split(" ");

                if (inputArr[0].equals("PLACE"))
                {
                    //First, check that the input is valid.
                    //Are sufficient inputs given?
                    if (inputArr[1] == null || inputArr[2] == null || inputArr[3] == null || inputArr[4] == null)
                    {
                        System.out.println("Insufficient options given for 'PLACE'. Please try again.");
                        break;
                    }

                    //Does the word given contain any invalid characters such as numbers or special characters.
                    String word = inputArr[1];
                    word = word.toUpperCase();
                    for (int i=0; i<word.length(); i++)
                    {
                        if (!Character.isLetter(word.charAt(i)) && word.charAt(i) != '_')
                        {
                            System.out.println("Word contains invalid characters. Please try again.");
                            break;
                        }
                    }

                    //Are the row and column options within the valid range (0-15)?
                    int row = Integer.parseInt(inputArr[2]);
                    int col = Integer.parseInt(inputArr[3]);
                    if (row > 15 || row < 1)
                    {
                        System.out.println("Given row coordinate does not fall between 0-15. Please try again.");
                        break;
                    }
                    if (col > 15 || col < 1)
                    {
                        System.out.println("Given column coordinate does not fall between 0-15. Please try again.");
                        break;
                    }

                    //Is the V/H option valid
                    if (!inputArr[4].equals("V") && !inputArr[4].equals("H"))
                    {
                        System.out.println("Invalid vertical or horizontal option given. Must be 'V' or 'H'.");
                        break;
                    }

                    //All inputs have been checked. Now parse the inputs into the args array.
                    args[0] = "0";
                    args[1] = word;
                    args[2] = Integer.toString(++row);
                    args[3] = Integer.toString(++col);
                    if (inputArr[4].equals("V")) {
                        args[4] = "true";
                    } else {
                        args[4] = "false";
                    }
                    return args;

                } else if (inputArr[0].equals("PASS"))
                {
                    args[0] = "1";
                    return args;

                } else if (inputArr[0].equals("REFILL"))
                {
                    args[0] = "2";
                    return args;
                } else if (inputArr[0].equals("QUIT")) {
                    args[0] = "3";
                    return args;
                } else if (inputArr[0].equals("HELP")) {
                    help();
                    break;
                }
                else
                {
                    System.out.println("Invalid input, please try again. Type 'HELP' to see instructions again.");
                }
            }
        }
    }

    /*
    Notifies the user of an error in placing a word and prompts them to try again.

     */
    public static void error(int error, Player player)
    {
        if (error == Board.OUT_OF_BOUNDS) {
            System.out.println("That word falls out of the boards bounds. Please try again.");
            playerMove(player);
        }
        else if (error == Board.NO_CONNECTION) {
            System.out.println("That word does not link up with other tiles on the board or the center square. Please try again.");
            playerMove(player);
        }
        else if (error == Board.INSUFFICIENT_TILES) {
            System.out.println("That word cannot be made up using tiles from your frame or tiles already on the board. Please try again.");
            playerMove(player);
        }
        else
        {
            throw new IllegalArgumentException("Given int is not a valid error code.");
        }
    }

    /*
    Prints instructions to the screen on how the user is to make their move.
     */
    static void help()
    {
        System.out.println("                            ---------- INSTRUCTIONS FOR PLAYING ----------                  ");
        System.out.println("To place a word type 'PLACE' followed by the following (space separated) options:");
        System.out.println("    1) The desired word using letters from your frame and '_' to indicate use of a blank tile.");
        System.out.println("    2) Your desired row coordinate (0-15)");
        System.out.println("    3) Your desired column coordinate (0-15)");
        System.out.println("    4) Whether you would like the word to be placed 'V' for vertically OR 'H' for horizontally");
        System.out.println("To skip your go type 'PASS'");
        System.out.println("To refill your board type 'REFILL'");
        System.out.println("To forfeit your game type 'QUIT'");
        System.out.println();
    }

}