package main;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class UI extends Application
{
    private static Scanner in = new Scanner(System.in);

    static Player playerInit()
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
    static String[] playerMove(Player player)
    {
        String[] args = new String[5];
        String input;


        System.out.println("It is " + player.getPlayerName() + "'s turn!");
        System.out.println("Here is your frame: " + player.getFrame());

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
                    if (inputArr[1].isEmpty() || inputArr[2].isEmpty() || inputArr[3].isEmpty() || inputArr[4].isEmpty()) {
                        System.out.println("Not enough options given for 'PLACE'. Please try again.");
                        continue;
                    }

                    //Does the word given contain any invalid characters such as numbers or special characters.
                    String word = inputArr[1];
                    word = word.toUpperCase();
                    boolean invalid = false;
                    for (int i=0; i<word.length(); i++)
                    {
                        if (!Character.isLetter(word.charAt(i)) && word.charAt(i) != '_')
                        {
                            System.out.println("Word contains invalid characters. Please try again.");
                            invalid = true;
                            break;
                        }
                    }
                    if (invalid) {
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
                    args[0] = "0";
                    args[1] = word;
                    if(row < 8) {
                        row+= (8-row)*2;
                    } else if (row > 8) {
                        row -= (row-8)*2;
                    }
                    args[2] = Integer.toString(--row);
                    args[3] = Integer.toString(--col);
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
                    continue;
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
        }
        else if (error == Board.NO_CONNECTION) {
            System.out.println("That word does not link up with other tiles on the board or the center square. Please try again.");
        }
        else if (error == Board.INSUFFICIENT_TILES) {
            System.out.println("That word cannot be made up using tiles from your frame or tiles already on the board. Please try again.");
        }
        else
        {
            throw new IllegalArgumentException("Given int is not a valid error code.");
        }
    }

    static boolean endGame() {
        System.out.println("Are you sure you want to end the game? (Y/N)");
        String input;
        while (true) {
            input = in.nextLine();
            if (input == "Y") {
                return true;
            } else if (input == "N") {
                return false;
            } else {
                System.out.println("Invalid input.");
                System.out.println("Are you sure you want to end the game? (Y/N)");
            }
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
        System.out.println("    2) Your desired row coordinate starting from the bottom-up (1-15)");
        System.out.println("    3) Your desired column coordinate starting from left-right (1-15)");
        System.out.println("    4) Whether you would like the word to be placed 'V' for vertically OR 'H' for horizontally");
        System.out.println("To skip your go type 'PASS'");
        System.out.println("To refill your board type 'REFILL'");
        System.out.println("To forfeit your game type 'QUIT'");
        System.out.println();
    }

    static void printBoard()
    {
        System.out.print(Scrabble.board);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //JAVAFX IMPLEMENTATION TO BE SIMPLIFIED TO INCLUDE 2D SQUARE ARRAY//////////////////////////////////////////////////////////////////////

    private static final int ROWS = 15;
    private static final int COLS = 15;

    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(750, 750);


        List<UI.Tile> tiles = new ArrayList<>();

        for (int i=0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                //TRIPLE WORD & CENTER CONDITIONS
                if((i == 0 || i == 14 || i == 7) && (j == 0 || j == 14 || j == 7))
                {
                    if (i == 7 && j == 7)
                    {
                        //border.setFill(Color.RED);
                        tiles.add(new UI.Tile("* *"));
                    }
                    else
                    {
                        tiles.add(new UI.Tile("TW"));
                    }
                }
                //DOUBLE WORD CONDITIONS
                else if ((i == 1 || i == 13) && (j == 1 || j == 13))
                {
                    tiles.add(new UI.Tile("DW"));
                } else if ((i == 2 || i == 12) && (j == 2 || j == 12))
                {
                    tiles.add(new UI.Tile("DW"));
                } else if ((i == 3 || i == 11) && (j == 3 || j == 11))
                {
                    tiles.add(new UI.Tile("DW"));
                } else if ((i == 4 || i == 10) && (j == 4 || j == 10))
                {
                    tiles.add(new UI.Tile("DW"));
                }
                //TRIPLE LETTER CONDITIONS
                else if ((i == 1 || i == 13) && (j == 5 || j == 9))
                {
                    tiles.add(new UI.Tile("TL"));
                } else if ((i == 5 || i == 9) && (j == 1 || j == 5 || j == 9 || j == 13))
                {
                    tiles.add(new UI.Tile("TL"));
                }
                //DOUBLE LETTER CONDITIONS
                else if ((i == 0 || i == 14) && (j == 3 || j == 11))
                {
                    tiles.add(new UI.Tile("DL"));
                }
                else if ((i == 3 || i == 11) && (j == 0 || j == 14))
                {
                    tiles.add(new UI.Tile("DL"));
                }
                else if ((i == 2 || i == 12) && (j == 6 || j == 8))
                {
                    tiles.add(new UI.Tile("DL"));
                }
                else if ((i == 6 || i == 8) && (j == 2 || j == 12))
                {
                    tiles.add(new UI.Tile("DL"));
                }
                else if ((i == 3 && j == 7) || (i == 7 && (j == 3 || j == 11)) || (i == 11 && j == 7))
                {
                    tiles.add(new UI.Tile("DL"));
                }
                else if ((i == 6 || i == 8) && (j == 6 || j == 8))
                {
                    tiles.add(new UI.Tile("DL"));
                }
                else
                {
                    tiles.add(new UI.Tile("  "));
                }
            }
        }

        for(int k=0; k<tiles.size(); k++)
        {
            UI.Tile tile = tiles.get(k);
            tile.setTranslateX(50 * (k / ROWS));
            tile.setTranslateY(50 * (k % ROWS));
            root.getChildren().add(tile);

        }
        return root;
    }

    private class Tile extends StackPane
    {
        public Tile(String value)
        {
            /*JavaFX Construction*/
            Rectangle border = new Rectangle(50, 50);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            Text text = new Text(String.valueOf(value));
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

    @Override
    public void start(Stage scrabbleBoard) throws Exception
    {
        scrabbleBoard.setScene(new Scene(createContent()));
        scrabbleBoard.setTitle("Scrabble Board");
        scrabbleBoard.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }

}