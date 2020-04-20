import java.util.*;

public class BotChristianCoders implements BotAPI {
    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;

    private int tilesRemaining;

    public BotChristianCoders(Player me, Player opponent, Board board, UserInterface ui, Dictionary dictionary) {
        this.me = me;
        this.opponent = opponent;
        this.board = board;
        this.info = ui;
        this.dictionary = dictionary;
    }

    @Override
    public String getCommand() {
        /*
                            <--- Algorithm from 'hints' on Brightspace --->
        1. Search the board for all possible word places soring them in a list in the form H8 A L****** where *'s
            represent possible letters.
        2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
            from the frame.
        3. Score the word Produced.
        4. Place the highest scored word.
         */
        updateTilesRemaining();
//
//        if(callChallenge())
//        {
//            return "CHALLENGE";
//        }

        ArrayList<PossibleWord> possibleWords = findPossibleWords();
        ArrayList<Word> legalWords = findLegalWords(possibleWords);
        Word word = mostValuableWord(legalWords);
        assert word != null;
        return "PLACE " + word.toString();
        //TODO This algorithm only ever places words. It may be better to refill at some stages? It should also pass if there are no possible words?
    }

    private void updateTilesRemaining() {
        String frame = me.getFrameAsString();
        char[] frameCharArray= frame.toCharArray();
        int count = 0;
        for (char c:frameCharArray)
        {
            if (Character.isLetter(c))
            {
                count++;
            }
        }
        tilesRemaining = count;
    }

    /**
     * 1. Search the board for all possible word places soring them in a list in the form H8 A L****** where *'s
     * represent possible letters.
     * @return ArrayList<String> of all possible word placements int the form H8 A L****** where *'s represent possible letters.
     */
    private ArrayList<PossibleWord> findPossibleWords()
    {
        ArrayList<PossibleWord> possibleWords = new ArrayList<>();
        if (board.isFirstPlay())
        {
            possibleWords.addAll(generateHorizontalPlacements(Board.BOARD_CENTRE, Board.BOARD_CENTRE));
            possibleWords.addAll(generateVerticalPlacements(Board.BOARD_CENTRE, Board.BOARD_CENTRE));
            return possibleWords;
        }
        for (int i = 0; i < Board.BOARD_SIZE; i++)
        {
            for (int j = 0; j < Board.BOARD_SIZE; j++)
            {
                if (board.getSquareCopy(i, j).isOccupied())
                {
                    if (checkHorizontalSpace(i, j))
                    {
                        possibleWords.addAll(generateHorizontalPlacements(i, j));
                    } else if (checkVerticalSpace(i, j))
                    {
                        possibleWords.addAll(generateVerticalPlacements(i, j));
                    }
                }
            }
        }
        return possibleWords;
    }

    /*
    Checks if there is space above or below of the given coordinates.
    If there is space, it then checks if there is any words directly left or right that space.
    Returns false if it fails either of these tests.
    Returns true if and only if it passes both of them.
     */
    private boolean checkVerticalSpace(int row, int col)
    {
        if (!board.getSquareCopy(row +1, col).isOccupied() && !board.getSquareCopy(row -1, col).isOccupied())
        {
            if (board.getSquareCopy(row -1, col +1).isOccupied() || board.getSquareCopy(row -1, col -1).isOccupied())
            {
                return !board.getSquareCopy(row + 1, col + 1).isOccupied() && !board.getSquareCopy(row + 1, col - 1).isOccupied();
            }
            else if (board.getSquareCopy(row +1, col +1).isOccupied() || board.getSquareCopy(row +1, col -1).isOccupied())
            {
                return !board.getSquareCopy(row - 1, col + 1).isOccupied() && !board.getSquareCopy(row - 1, col - 1).isOccupied();
            } else {
                return true;
            }
        }
        return false;
    }

    /*
    Checks if there is space either side of the given coordinates.
    If there is space, it then checks if there is any words directly above or below that space.
    Returns false if it fails either of these tests.
    Returns true if and only if it passes both of them.
     */
    private boolean checkHorizontalSpace(int row, int col)
    {
        if (!board.getSquareCopy(row, col +1).isOccupied() && !board.getSquareCopy(row, col -1).isOccupied())
        {
            if (board.getSquareCopy(row +1, col +1).isOccupied() || board.getSquareCopy(row -1, col +1).isOccupied())
            {
                return !board.getSquareCopy(row + 1, col - 1).isOccupied() && !board.getSquareCopy(row - 1, col - 1).isOccupied();
            }
            else if (board.getSquareCopy(row +1, col -1).isOccupied() || board.getSquareCopy(row -1, col -1).isOccupied())
            {
                return !board.getSquareCopy(row - 1, col + 1).isOccupied() && !board.getSquareCopy(row + 1, col + 1).isOccupied();
            } else {
                return true;
            }
        }
        return false;
    }

    private ArrayList<PossibleWord> generateHorizontalPlacements(int row, int col) {
        // Step 1): Define a row with start and end limits where we have space to place a word.
        // A) Start with setting the limits $tilesRemaining spaces either side of the word.
        int start = col - tilesRemaining;
        int end = col + tilesRemaining;

        // B) Cut these limits off if they overlap the edge of the Board
        if (start < 0)
        {
            start = 0;
        } else if (start > 14)
        {
            start = 14;
        }
        if (end < 0)
        {
            end = 0;
        } else if (end > 14)
        {
            end = 14;
        }

        // C) Check for any other letters in the row space and also if there are any letters directly above or below it.
        //    Where letters are found. Cut off the start and end limits.
        for (int i=col-1; i>=start; )
        {
            if (board.getSquareCopy(row, i).isOccupied()) {
                start = i+2;
                break;
            }
            else if (board.getSquareCopy(row-1, i).isOccupied() || board.getSquareCopy(row+1, i).isOccupied())
            {
                start = i+1;
                break;
            } else {
                i--;
            }
        }
        for (int i=col+1; i<=end; )
        {
            if (board.getSquareCopy(row, i).isOccupied()) {
                end = i-2;
                break;
            }
            else if (board.getSquareCopy(row-1, i).isOccupied() || board.getSquareCopy(row+1, i).isOccupied())
            {
                end = i-1;
                break;
            } else {
                i++;
            }
        }

        // Step 2: start and end are well defined. Now generate possible words of size frameTiles+1 down to 2.
        ArrayList<PossibleWord> placements = new ArrayList<>();
        int maxWordLength = Math.min((end - start) + 1, tilesRemaining+1);
        for (int wordLength = maxWordLength; wordLength > 1; wordLength--)
        {
            //Choose starting point of the row
            int startColIndex;
            if (start < col)
            {
                startColIndex = start+(maxWordLength-wordLength);
            } else {
                startColIndex = start;
            }
            //Generate placements along this row until the starting point reaches the (column) word intersection.
            while (startColIndex <= col)
            {
                if (startColIndex+(wordLength-1) > end)
                {
                    //If we hit the end limit we set earlier, stop.
                    break;
                }
                //Generate Placement
                char letter = '\0';
                int letterIndex = -1;
                for (int i = startColIndex; i <= (startColIndex+(wordLength-1)); i++)
                {
                    if (board.getSquareCopy(row, i).isOccupied())
                    {
                        letter = board.getSquareCopy(row, i).getTile().getLetter();
                        letterIndex = i-startColIndex;
                        break;
                    }
                }
                if (letterIndex != -1) {

                    placements.add(new PossibleWord(row, startColIndex, true, wordLength, letter, letterIndex));
                } else
                {
                    placements.add(new PossibleWord(row, startColIndex, true, wordLength));
                }
                startColIndex++;
            }
        }
        return placements;
    }

    private ArrayList<PossibleWord> generateVerticalPlacements(int row, int col) {
        // Step 1): Define a row with start and end limits where we have space to place a word.
        // A) Start with setting the limits $tilesRemaining spaces either side of the word.
        int start = row - tilesRemaining;
        int end = row + tilesRemaining;

        // B) Cut these limits off if they overlap the edge of the Board
        if (start < 0)
        {
            start = 0;
        } else if (start > 14)
        {
            start = 14;
        }
        if (end < 0)
        {
            end = 0;
        } else if (end > 14)
        {
            end = 14;
        }

        // C) Check for any other letters in the row space and also if there are any letters directly above or below it.
        //    Where letters are found. Cut off the start and end limits.
        for (int i=row-1; i>=start; )
        {
            if (board.getSquareCopy(i, col).isOccupied()) {
                start = i+2;
                break;
            }
            else if (board.getSquareCopy(i, col-1).isOccupied() || board.getSquareCopy(i, col+1).isOccupied())
            {
                start = i+1;
                break;
            } else {
                i--;
            }
        }
        for (int i=row+1; i<=end; )
        {
            if (board.getSquareCopy(i, row).isOccupied()) {
                end = i-2;
                break;
            }
            else if (board.getSquareCopy(i, col-1).isOccupied() || board.getSquareCopy(i, col-1).isOccupied())
            {
                end = i-1;
                break;
            } else {
                i++;
            }
        }

        //Step 2: start and end are well defined. Now generate possible words of size frameTiles+1 down to 2.
        ArrayList<PossibleWord> placements = new ArrayList<>();
        int maxWordLength = Math.min((end - start) + 1, tilesRemaining+1);
        for (int wordLength = maxWordLength; wordLength > 1; wordLength--)
        {
            //Choose starting point of the row
            int startRowIndex;
            if (start < row)
            {
                startRowIndex = start+(maxWordLength-wordLength);
            } else {
                startRowIndex = start;
            }
            while (startRowIndex <= row)
            {
                if (startRowIndex+(wordLength-1) > end)
                {
                    //If we hit the end limit we set earlier, stop.
                    break;
                }
                char letter = '\0';
                int letterIndex = -1;
                for (int i = startRowIndex; i <= (startRowIndex+(wordLength-1)); i++)
                {
                    if (board.getSquareCopy(i, col).isOccupied())
                    {
                        letter = board.getSquareCopy(i, col).getTile().getLetter();
                        letterIndex = i-startRowIndex;
                        break;
                    }
                }
                if (letterIndex != -1)
                {
                    placements.add(new PossibleWord(startRowIndex, col, false, wordLength, letter, letterIndex));
                } else
                {
                    placements.add(new PossibleWord(startRowIndex, col, false, wordLength));
                }
                startRowIndex++;
            }
        }
        return placements;
    }

    private class PossibleWord
    {
        private final int row;
        private final int column;
        private final boolean isHorizontal;
        private final int length;
        private char existingLetter = '\0';
        private int existingLetterIndex = -1;

        public PossibleWord(int row, int column, boolean isHorizontal, int length, char existingLetter, int existingLetterIndex)
        {
            this.row = row;
            this.column = column;
            this.isHorizontal = isHorizontal;
            this.length = length;
            this.existingLetter = existingLetter;
            this.existingLetterIndex = existingLetterIndex;
        }

        public PossibleWord(int row, int column, boolean isHorizontal, int length)
        {
            this.row = row;
            this.column = column;
            this.isHorizontal = isHorizontal;
            this.length = length;
        }

        public boolean hasExistingLetter() { return existingLetter != '\0'; }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Row:" + row + " Column:" + column);
            if (isHorizontal) {
                sb.append(" H ");
            } else {
                sb.append(" V ");
            }
            for (int i = 0; i < length; i++) {
                if (i==existingLetterIndex)
                {
                    sb.append(existingLetter);
                } else {
                    sb.append('*');
                }
            }
            return sb.toString();
        }
    }


    /*
     *  2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
     *  from the frame.
     *  @param possibleWords ArrayList<String> of possible word placements in the form H8 A L**
     *  @return ArrayList<Word> of all legal word word objects.
     *  defined, legal word.
     */

    static int wordLength = 3;
    static int count=0;

    public static ArrayList<String> getPermutations(String frame)
    {
        // If string is empty
        if (frame.length() == 0) {

            // Return an empty arraylist
            ArrayList<String> empty = new ArrayList<>();
            empty.add("");
            return empty;
        }

        char ch = frame.charAt(0); //first char

        String subStr = frame.substring(1); //rest of string

        //recursive call
        ArrayList<String> prevResult = getPermutations(subStr);

        ArrayList<String> result = new ArrayList<>();

        for (String string : prevResult) {
            for (int i = 0; i <= string.length(); i++) {
                result.add(string.substring(0, i) + ch + string.substring(i));
            }
        }
        return result;
    }

    ArrayList<Word> wordList = new ArrayList<Word>();

    private ArrayList<Word> stringToWord(ArrayList<String> arrList, PossibleWord possibleWord)
    {

        for(String s : arrList)
        {
            Word word = new Word(possibleWord.row, possibleWord.column, possibleWord.isHorizontal, s); //creating a new word object using the
            wordList.add(word);
        }
        return wordList;
    }

    private ArrayList<Word> findLegalWords(ArrayList<PossibleWord> possibleWords)
    {
        for (PossibleWord word : possibleWords) {

            String frame = me.getFrameAsString();

            ArrayList<String> arrayList = getPermutations(frame);

            arrayList.remove("");

            int wordLength = word.length;
            int index = word.existingLetterIndex;
            char ch = word.existingLetter;

            Set<String> set = new LinkedHashSet<String>();

            for (String s : arrayList) {
                StringBuilder stringbuilder = new StringBuilder(s);
                stringbuilder.insert(index, ch);

                for (int i = 0; i < stringbuilder.length() - wordLength; i++) {
                    stringbuilder.deleteCharAt(stringbuilder.length() - 1); //trims the strings to only output the permutations that fit in wordLength
                }

                ArrayList<Word> singleWord = new ArrayList<Word>();
                Word dictionaryTest = new Word(0, 0, true, stringbuilder.toString());
                singleWord.add(dictionaryTest);

                if(dictionary.areWords(singleWord)) //if the word is an actual word, add it to the set
                {
                    set.add(stringbuilder.toString()); //set does not allow for duplicates therefore gets rid of our dupes (which come from the non-perfect trimming system)
                }

                //boardAPI.isLegalPlay?
            }

            ArrayList<String> legalPermutationsForWord = new ArrayList<>(set);

            stringToWord(legalPermutationsForWord, word);
        }
        return wordList;
    }


    private ArrayList<Coordinates> newLetterCoords;

    private int getWordPoints(Word word)
    {
        newLetterCoords = new ArrayList<>();
        int wordValue = 0;
        int wordMultiplier = 1;
        int r = word.getFirstRow();
        int c = word.getFirstColumn();
        newLetterCoords.add(new Coordinates(r,c));
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

/*    *//**
     Function to determine whether the bot should CHALLENGE the last word placed before moving.
     *//*
    public boolean callChallenge()
    {
        ArrayList<Word> word = new ArrayList<>();
        word = scrabble.getLatestWords();
        if (!(dictionary.areWords(word)))
        {
           return true;
        }
        else
        {
            return false;
        }
    }*/
}
