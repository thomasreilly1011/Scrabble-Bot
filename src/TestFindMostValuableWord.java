import java.util.ArrayList;

public class TestFindMostValuableWord {

    static Board board = new Board();

    public static void main(String[] args) {
        TestFindMostValuableWord test = new TestFindMostValuableWord();

        ArrayList<Word> testWords = new ArrayList<>();
        testWords.add(new Word(7, 7, true, "Hello"));
        testWords.add(new Word(7, 7, true, "Beans"));
        testWords.add(new Word(7, 3, true, "Xylophone"));

//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));
//        board.getSquare(7, 7).add(new Tile('H'));


        Word mvw = test.mostValuableWord(testWords);

        System.out.println(mvw.toString());
    }

    private ArrayList<Coordinates> newLetterCoords;

    private int getWordPoints(Word word)
    {

        int wordValue = 0;
        int wordMultiplier = 1;
        int r = word.getFirstRow();
        int c = word.getFirstColumn();
        for (int i = 0; i<word.length(); i++)
        {
            int letterValue = board.getSquareCopy(r, c).getTile().getValue();
            if (newLetterCoords.contains(new Coordinates(r,c)))
            {
                wordValue = wordValue + letterValue * board.getSquareCopy(r, c).getLetterMuliplier();
                wordMultiplier = wordMultiplier * board.getSquareCopy(r, c).getWordMultiplier();
            }
            else
            {
                wordValue = wordValue + letterValue;
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
        return wordValue * wordMultiplier;
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
