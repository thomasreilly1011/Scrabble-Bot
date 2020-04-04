package IO;

import main.Board;
import main.Player;
import main.Scrabble;

import java.util.Scanner;

public class CLI {

    private final Scanner in = new Scanner(System.in);

    public Player playerInit()
    {

        System.out.println("Enter a player's name:");
        String s = in.nextLine();

        Player player = new Player(s);

        System.out.println(s + "'s Frame:\n" + player.getFrame() + "\n");

        return player;
    }

    /*
    Returns an array where the first element (args[0]) is always the move type.
    If move type is PASS, QUIT or REFILL, no other arguments are provided.
    If move type is PLACE_WORD the other arguments are as follows
    args[1] = desired word
    args[2] = internal row coordinate
    args[3] = internal column coordinate
    args[4] = vertical boolean
     */
    public String[] playerMove(Player player)
    {
        String[] args = new String[5];
        String input;


        System.out.println("It is " + player.getPlayerName() + "'s turn!");
        System.out.println("Here is your frame: " + player.getFrame());

        System.out.println("Type 'HELP' to see instructions again.");
        while (true)
        {
            input = in.nextLine();
            String[] inputArr= input.split(" ");

            switch (inputArr[0])
            {
                case "PLACE":
                    //First, check that the input is valid.
                    //Are sufficient inputs given?
                    //First check no. of inputs.
                    if (inputArr.length > 5)
                    {
                        System.out.println("To many options given for 'PLACE'. Please try again.");
                        continue;
                    }
                    if (inputArr.length < 5)
                    {
                        System.out.println("Not enough options given for 'PLACE'. Please try again.");
                        continue;
                    }
                    if (inputArr[1].isEmpty() || inputArr[2].isEmpty() || inputArr[3].isEmpty() || inputArr[4].isEmpty())
                    {
                        System.out.println("Not enough options given for 'PLACE'. Please try again.");
                        continue;
                    }

                    //Does the word given contain any invalid characters such as numbers or special characters.
                    String word = inputArr[1];
                    word = word.toUpperCase();
                    boolean invalid = false;
                    for (int i = 0; i < word.length(); i++)
                    {
                        if (!Character.isLetter(word.charAt(i)) && word.charAt(i) != '_')
                        {
                            System.out.println("Word contains invalid characters. Please try again.");
                            invalid = true;
                            break;
                        }
                    }
                    if (invalid)
                    {
                        continue;
                    }

                    //Are the row and column options within the valid range (0-15)?
                    int row = Integer.parseInt(inputArr[2]);
                    int col = Integer.parseInt(inputArr[3]);
                    if (row > 15 || row < 1)
                    {
                        System.out.println("Given row coordinate does not fall between 1-15. Please try again.");
                        continue;
                    }
                    if (col > 15 || col < 1)
                    {
                        System.out.println("Given column coordinate does not fall between 1-15. Please try again.");
                        continue;
                    }

                    //Is the V/H option valid
                    if (!inputArr[4].equals("V") && !inputArr[4].equals("H"))
                    {
                        System.out.println("Invalid vertical or horizontal option given. Must be 'V' or 'H'.");
                        continue;
                    }

                    //All inputs have been checked. Now parse the inputs into the args array.
                    args[0] = Integer.toString(Scrabble.PLACE_WORD);
                    args[1] = word;
                    //Flip the row coordinate for internal use.
                    if (row < 8)
                    {
                        row += (8 - row) * 2;
                    }
                    else if (row > 8)
                    {
                        row -= (row - 8) * 2;
                    }
                    //Decrement the row and column coordinates to the range 0-14 for internal use.
                    args[2] = Integer.toString(--row);
                    args[3] = Integer.toString(--col);
                    if (inputArr[4].equals("V"))
                    {
                        args[4] = "true";
                    }
                    else
                        {
                        args[4] = "false";
                    }
                    return args;

                case "PASS":
                    //First check no. of inputs.
                    if (inputArr.length > 1)
                    {
                        System.out.println("Too many arguments for PASS command. Please try again");
                        continue;
                    }
                    args[0] = Integer.toString(Scrabble.PASS);
                    return args;
                case "REFILL":
                    //First check no. of inputs.
                    if (inputArr.length > 1)
                    {
                        System.out.println("Too many arguments for REFILL command. Please try again");
                        continue;
                    }
                    args[0] = Integer.toString(Scrabble.REFILL);
                    return args;
                case "QUIT":
                    //First check no. of inputs.
                    if (inputArr.length > 1)
                    {
                        System.out.println("Too many arguments for CHALLENGE command. Please try again");
                        continue;
                    }
                    args[0] = Integer.toString(Scrabble.QUIT);
                    return args;
                case "HELP":
                    help();
                    break;
                case "CHALLENGE":
                    //First check no. of inputs.
                    if (inputArr.length > 1)
                    {
                        System.out.println("Too many arguments for CHALLENGE command. Please try again");
                        continue;
                    }
                    args[0] = Integer.toString(Scrabble.CHALLENGE);
                    return args;
                case "NAME":
                    //First check no. of inputs.
                    if (inputArr.length < 2)
                    {
                        System.out.println("Too few arguments for CHALLENGE command. Please try again");
                        continue;
                    }
                    if (inputArr.length > 2)
                    {
                        System.out.println("Too many arguments for CHALLENGE command. Please try again");
                        continue;
                    }
                    args[0] = Integer.toString(Scrabble.NAME);
                    args[1] = inputArr[1];
                    return args;
                default:
                    System.out.println("Invalid input, please try again. Type 'HELP' to see instructions again.");
                    break;
            }
        }
    }

    /*
    Notifies the user of an error in placing a word and prompts them to try again.
     */
    public void error(int error)
    {
        if (error == Board.OUT_OF_BOUNDS)
        {
            System.out.println("That word falls out of the boards bounds. Please try again.");
        }
        else if (error == Board.NO_CONNECTION)
        {
            System.out.println("That word does not link up with other tiles on the board or the center square. Please try again.");
        }
        else if (error == Board.INSUFFICIENT_TILES)
        {
            System.out.println("That word cannot be made up using tiles from your frame or tiles already on the board. Please try again.");
        }
        else if (error == Board.ONE_LETTER)
        {
            System.out.println("Enter a valid word (more than one character).");
        }
        else
        {
            throw new IllegalArgumentException("Given int is not a valid error code.");
        }
    }

    public boolean endGame()
    {
        System.out.println("Are you sure you want to end the game? (Y/N)");
        String input;
        while (true)
        {
            input = in.nextLine();
            if (input.equals("Y"))
            {
                return true;
            } else if (input.equals("N"))
            {
                return false;
            } else
                {
                System.out.println("Invalid input.");
                System.out.println("Are you sure you want to end the game? (Y/N)");
            }
        }
    }

    /*
    Prints instructions to the screen on how the user is to make their move.
     */
    public void help()
    {
        System.out.println("                            ---------- INSTRUCTIONS FOR PLAYING ----------                  ");
        System.out.println("To place a word type 'PLACE' followed by the following (space separated) options:");
        System.out.println("    1) The desired word using letters from your frame and '_' to indicate use of a blank tile.");
        System.out.println("    2) Your desired row coordinate starting from the bottom-up (1-15)");
        System.out.println("    3) Your desired column coordinate starting from left-right (1-15)");
        System.out.println("    4) Whether you would like the word to be placed 'V' for vertically OR 'H' for horizontally");
        System.out.println("To skip your go type 'PASS'");
        System.out.println("To refill your board type 'REFILL'");
        System.out.println("To forfeit your game type 'QUIT'");
        System.out.println();
    }

    public void announceScore(Player player, int score)
    {
        System.out.println("That word scored you " + score + " points!");
        System.out.println(player.getPlayerName() + "'s score is now: " + player.getPlayerScore());
    }
}
