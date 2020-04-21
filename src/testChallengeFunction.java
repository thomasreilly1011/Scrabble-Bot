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

        System.out.println("\n-------------Test 1---------------");
        board.getSquare(7, 7).add(new Tile('H'));
        board.getSquare(7, 8).add(new Tile('E'));
        board.getSquare(7, 9).add(new Tile('L'));
        board.getSquare(7, 10).add(new Tile('L'));
        board.getSquare(7, 11).add(new Tile('O'));

        System.out.println("\n-------------Test 3---------------");
        board.getSquare(1, 1).add(new Tile('B'));
        board.getSquare(1, 2).add(new Tile('A'));
        board.getSquare(1, 3).add(new Tile('N'));
        board.getSquare(1, 4).add(new Tile('G'));

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
                    else
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
                    for(Word word : wordsToCheck)
                    {
                        System.out.println(word);
                    }
                    found = new StringBuilder();
                }
            }
        }
        if (!(dictionary.areWords(wordsToCheck)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
