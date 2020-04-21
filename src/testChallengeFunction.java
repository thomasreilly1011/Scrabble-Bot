import java.io.FileNotFoundException;
import java.util.ArrayList;

public class testChallengeFunction
{
    private static Board board = new Board();
    private static int tilesRemaining = 7;
    private static Pool pool = new Pool();
    private static Frame botsFrame = new Frame();
    private static Dictionary dictionary;

    static {
        try {
            dictionary = new Dictionary();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        testChallengeFunction run = new testChallengeFunction();

        ////////////------------Test 1---------------//////////
        board.getSquare(7, 7).add(new Tile('H'));
        board.getSquare(7, 8).add(new Tile('E'));
        board.getSquare(7, 9).add(new Tile('L'));
        board.getSquare(7, 10).add(new Tile('L'));
        board.getSquare(7, 11).add(new Tile('O'));

        ////////////-------------Test 2---------------//////////
        board.getSquare(7, 7).add(new Tile('H'));
        board.getSquare(8, 7).add(new Tile('E'));
        board.getSquare(9, 7).add(new Tile('L'));
        board.getSquare(10, 7).add(new Tile('L'));
        board.getSquare(11, 7).add(new Tile('O'));

        ////////////-------------Test 3---------------//////////
        board.getSquare(1, 1).add(new Tile('B'));
        board.getSquare(1, 2).add(new Tile('I'));
        board.getSquare(1, 3).add(new Tile('N'));
        board.getSquare(1, 4).add(new Tile('G'));
        board.getSquare(1, 5).add(new Tile('E'));

        ////////////-------------Test 4---------------//////////
        board.getSquare(1, 1).add(new Tile('B'));
        board.getSquare(2, 1).add(new Tile('A'));
        board.getSquare(3, 1).add(new Tile('B'));
        board.getSquare(4, 1).add(new Tile('O'));
        board.getSquare(5, 1).add(new Tile('O'));
        board.getSquare(6, 1).add(new Tile('N'));

        if(run.callChallenge())
        {
            System.out.println("The bot should indeed challenge the last placed word");
        }
        else
        {
            System.out.println("Don't be silly, the last placed word is correct!");
        }
    }


    /**
     Function to determine whether the bot should CHALLENGE the last word placed before moving.
     */
    public boolean callChallenge()
    {
        ArrayList<Word> wordsToCheck = new ArrayList<>();
        int row = 0;
        int col = 0;
        boolean isHorizontal = true;
        StringBuilder found = new StringBuilder();

        for (int i = 0; i < Board.BOARD_SIZE; i++)
        {
            for (int j = 0; j < Board.BOARD_SIZE; j++)
            {
                if (board.getSquareCopy(i, j).isOccupied())
                {
                    row = i;
                    col = j;
                    System.out.println("Coordinates for the first letter in this word is ["+row+", "+col+"]");
                    char letter;
                    if(board.getSquareCopy(i, j+1).isOccupied() && board.getSquareCopy(i+1, j).isOccupied())
                    {
                        while(board.getSquareCopy(row, j).isOccupied())
                        {
                            isHorizontal = true;
                            letter = board.getSquareCopy(row, j).getTile().getLetter();
                            System.out.println("Letter to be added to the word string is: '"+letter+"'");
                            found.append(letter);
                            System.out.println("String for word so far contains '"+found+"'");
                            if(i==Board.BOARD_SIZE)
                            {
                                break;
                            }
                            j++;
                        }
                        wordsToCheck.add(new Word(row, col, isHorizontal, found.toString()));
                        found = new StringBuilder();
                        while(board.getSquareCopy(i, col).isOccupied())
                        {
                            isHorizontal = false;
                            letter = board.getSquareCopy(i, col).getTile().getLetter();
                            System.out.println("Letter to be added to the word string is: '"+letter+"'");
                            found.append(letter);
                            System.out.println("String for word so far contains '"+found+"'");
                            if(i==Board.BOARD_SIZE)
                            {
                                break;
                            }
                            i++;
                        }
                    }
                    if(board.getSquareCopy(i, j+1).isOccupied())
                    {
                        isHorizontal = true;
                        while(board.getSquareCopy(row, j).isOccupied())
                        {
                            letter = board.getSquareCopy(row, j).getTile().getLetter();
                            System.out.println("Letter to be added to the word string is: '"+letter+"'");
                            found.append(letter);
                            System.out.println("String for word so far contains '"+found+"'");
                            if(j==Board.BOARD_SIZE)
                            {
                                break;
                            }
                            j++;
                        }
                    }
                    if(board.getSquareCopy(i+1, j).isOccupied())
                    {
                        isHorizontal = false;
                        while(board.getSquareCopy(i, col).isOccupied())
                        {
                            letter = board.getSquareCopy(i, col).getTile().getLetter();
                            System.out.println("Letter to be added to the word string is: '"+letter+"'");
                            found.append(letter);
                            System.out.println("String for word so far contains '"+found+"'");
                            if(i==Board.BOARD_SIZE)
                            {
                                break;
                            }
                            i++;
                        }
                    }
                    wordsToCheck.add(new Word(row, col, isHorizontal, found.toString()));
                    System.out.println("\nArrayList of all words currently on the board:");
                    for(int k=0; k<wordsToCheck.size(); k++)
                    {
                        System.out.println("\tWordsToCheck["+k+"]: "+wordsToCheck.get(k));
                    }
                    found = new StringBuilder();
                    System.out.println();
                }
            }
        }
        if ((dictionary.areWords(wordsToCheck)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
