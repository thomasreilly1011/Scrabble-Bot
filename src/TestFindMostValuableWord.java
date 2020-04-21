import java.util.ArrayList;

public class TestFindMostValuableWord {

    static Board board = new Board();

    public static void main(String[] args) {
        TestFindMostValuableWord test = new TestFindMostValuableWord();

        ArrayList<Word> testWords = new ArrayList<>();
        testWords.add(new Word(7, 7, true, "Hello"));
        testWords.add(new Word(7, 7, false, "Beans"));
        testWords.add(new Word(7, 3, true, "Xylophone"));

        Word mvw = test.mostValuableWord(testWords);

        System.out.println("\nMost valuable word to place is '"+mvw.toString()+"'");
    }

    private ArrayList<Coordinates> newLetterCoords;
    private static final int[] TILE_VALUE = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};

    private int getWordPoints(Word word)
    {
        // place precondition: isLegal is true
        newLetterCoords = new ArrayList<>();
        int r = word.getFirstRow();
        int c = word.getFirstColumn();
        System.out.println();
        for (int i = 0; i<word.length(); i++)
        {
            if (!board.getSquareCopy(r,c).isOccupied())
            {
                System.out.println("This square is not occupied, adding the coords ["+r+", "+c+"] of '"+word.getLetter(i)+"' to the Coord ArrayList");
                newLetterCoords.add(new Coordinates(r,c));
            }
            if (word.isHorizontal())
            {
                c++;
            }
            else
            {
                r++;
            }
        }
        int score = 0;
        int wordValue = 0;
        int wordMultiplier = 1;
        int row = word.getFirstRow();
        int col = word.getFirstColumn();

        System.out.println("\nWord is: " + word.toString());
        System.out.println("Length of word is " + word.length());
        for (int i = 0; i<word.length(); i++)
        {
            char letter = word.getLetter(i);
            letter = Character.toUpperCase(letter);
            System.out.println("\nLetter at position [" + i + "] of " + word.toString() + " is '" + letter + "'");

            int letterValue = TILE_VALUE[(int) letter - (int) 'A'];
            System.out.println("Value of letter at position " + i + " of " + word.toString() + " is '" + letterValue + "'\n");

            if (!newLetterCoords.contains(new Coordinates(row,col)))
            {
                System.out.println("newLetterCoords contains the coordinates: ["+row+", "+col+"]");
                wordValue = wordValue + letterValue * board.getSquareCopy(row, col).getLetterMuliplier();
                wordMultiplier = wordMultiplier * board.getSquareCopy(row, col).getWordMultiplier();
                System.out.println("WordValue at iteration " + i + " is: " + wordValue);
                System.out.println("WordMultiplier value at iteration " + i + " is: " + wordMultiplier);
            }
            else
            {
                System.out.println("newLetterCoords does not contain the coordinates: ["+row+", "+col+"]");
                wordValue = wordValue + letterValue;
                System.out.println("WordValue at iteration " + i + " is: " + wordValue);
            }
            if (word.isHorizontal())
            {
                col++;
            }
            else
            {
                row++;
            }
        }
        score = wordValue * wordMultiplier;
        System.out.println("Score for word '"+word.toString()+"' is : "+score);
        return score;
    }


    /**
     * 3. Score the words Produced.
     * @param legalWords ArrayList<Word> of legal Word objects.
     * @return A single legal Word object that is the highest scoring option.
     */
    private Word mostValuableWord(ArrayList<Word> legalWords)
    {
        int highestScore = 0;
        int score = 0;
        int bestWordIndex = 0;
        int index = 0;

        for(Word word: legalWords)
        {
            int wordPoints = getWordPoints(word);
            score += wordPoints;
            if(score >= highestScore)
            {
                highestScore = score;
                bestWordIndex = index;
            }
            index++;
        }
        return legalWords.get(bestWordIndex);
    }
}
