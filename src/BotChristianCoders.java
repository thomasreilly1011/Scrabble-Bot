import java.util.*;

public class BotChristianCoders implements BotAPI {
    private final PlayerAPI me;
    private final BoardAPI board;
    private final DictionaryAPI dictionary;

    private int tilesRemaining;

    Frame frame = new Frame();

    public BotChristianCoders(PlayerAPI me, OpponentAPI opponent, BoardAPI board, UserInterfaceAPI ui, DictionaryAPI dictionary) {
        this.me = me;
        this.board = board;
        this.dictionary = dictionary;

        String frameLetters = frameToString();
        ArrayList<Tile> tileArrayList = new ArrayList<Tile>(7);
        for(int i=0; i<frameLetters.length(); i++)
        {
            tileArrayList.add(new Tile(frameLetters.charAt(i)));
        }
        frame.addTiles(tileArrayList);
    }

    @Override
    public String getCommand() {
        // First, See if the last world should be challenged
        System.out.println("\nChallenge??");
        if(callChallenge())
        {
            return "CHALLENGE";
        }

        System.out.println("\nFinding Words");
        // Otherwise, See what words we can place...
        updateTilesRemaining();
        System.out.println("Tiles updated");
        ArrayList<PossibleWord> possibleWords = findPossibleWords();
        System.out.println("Possible Words Found");
        ArrayList<Word> legalWords = findLegalWords(possibleWords);
        System.out.println("Tiles Updated");

        // If we have no legal words, we should refill our FRAME
        if (legalWords.isEmpty()) {
            return "REFILL";
        }

        // Otherwise, find the best possible word and place that.
        Word word = mostValuableWord(legalWords);

        System.out.println("\nWord found!");
        return createPlaceCommand(word);
    }

    private String createPlaceCommand(Word word) {
        char col = (char) (((int ) 'A') + word.getFirstColumn());
        StringBuilder command = new StringBuilder();
        command.append("PLACE ");
        command.append(col);
        command.append(word.getFirstRow());
        if (word.isHorizontal()) {
            command.append(" A ");
        } else {
            command.append(" D ");
        }
        command.append(word.getLetters());
        return command.toString();
    }

    /**
     Function to scan entire board for placed tiles and scan them into an Arraylist<>
     Determines whether the bot should CHALLENGE the last word placed before moving.
     */
    public boolean callChallenge()
    {
        ArrayList<Word> wordsToCheck = new ArrayList<>();
        int row;
        int col;
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
                    char letter;
                    if(board.getSquareCopy(i, j+1).isOccupied() && board.getSquareCopy(i+1, j).isOccupied())
                    {
                        while(board.getSquareCopy(row, j).isOccupied())
                        {
                            isHorizontal = true;
                            letter = board.getSquareCopy(row, j).getTile().getLetter();
                            found.append(letter);
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
                            found.append(letter);
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
                            found.append(letter);
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
                            found.append(letter);
                            if(i==Board.BOARD_SIZE)
                            {
                                break;
                            }
                            i++;
                        }
                    }
                    wordsToCheck.add(new Word(row, col, isHorizontal, found.toString()));
                    found = new StringBuilder();
                }
            }
        }
        return !(dictionary.areWords(wordsToCheck));
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

    public static ArrayList<String> getPermutations(String frame)
    {
        // If string is empty
        if (frame.length() == 0)
        {

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

        for (String string : prevResult)
        {
            for (int i = 0; i <= string.length(); i++)
            {
                result.add(string.substring(0, i) + ch + string.substring(i));
            }
        }
        return result;
    }

    private boolean fitsOnBoard(Word word)
    {
        if(word.isHorizontal())
        {
            if(word.getRow()+word.length()-1 > 14)
            {
                return false;
            } else {
                return true;
            }
        }
        else {

            if(word.getColumn()+word.length()-1 > 14)
            {
                return false;
            } else {
                return true;
            }
        }
    }

    private Set<Word> stringToWord(Set<String> set, PossibleWord possibleWord, Set<Word> wordList)
    {
        for(String s : set)
        {
            Word word = new Word(possibleWord.row, possibleWord.column, possibleWord.isHorizontal, s); //creating a new word object using the

            if(fitsOnBoard(word)) //catch any words that may not fit on the board.
            {
                wordList.add(word);
            }

            //TODO delete usage of isLegalPlay here if the new fitsOnBoard function sorts the problem, also delete the frame created as we no longer need it.
            /*if(board.isLegalPlay(frame, word)) //catch any illegal words that are passed (such as words that would not fit on the board)
            {
                wordList.add(word);
            }*/
        }
        return wordList;
    }

    private String frameToString()
    {
        StringBuilder frameLetters = new StringBuilder();
        String frame = me.getFrameAsString();
        char[] frameCharArray= frame.toCharArray();
        int count = 0;
        for (char c:frameCharArray)
        {
            if (Character.isLetter(c))
            {
                frameLetters.append(c);
            }
        }
        return frameLetters.toString();
    }

    /**
     * 2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
     * from the frame.
     * @param possibleWords ArrayList<String> of possible word placements in the form H8 A L******
     * @return ArrayList<Word> of all legal word word objects.
     * defined, legal word.
     */

    private ArrayList<Word> findLegalWords(ArrayList<PossibleWord> possibleWords)
    {
        Set<Word> wordList = new HashSet<Word>();

        for (PossibleWord word : possibleWords)
        {
            String frame = frameToString();

            ArrayList<String> arrayList = getPermutations(frame);

            arrayList.remove("");

            int wordLength = word.length;
            int index = word.existingLetterIndex;
            char ch = word.existingLetter;

            Set<String> set = new HashSet<String>();
            for (String s : arrayList)
            {
                StringBuilder stringbuilder = new StringBuilder(s);
                if(index!=-1 && !(index>stringbuilder.length()))
                {
                    stringbuilder.insert(index, ch);
                }
                if(!(index>stringbuilder.length()))
                {
                    for (int i = 0; i < stringbuilder.length() - wordLength; i++)
                    {
                        stringbuilder.deleteCharAt(stringbuilder.length() - 1); //trims the strings to only output the permutations that fit in wordLength
                    }

                    ArrayList<Word> singleWord = new ArrayList<Word>();
                    Word dictionaryTest = new Word(0, 0, true, stringbuilder.toString());
                    singleWord.add(dictionaryTest);

                    if(dictionary.areWords(singleWord)) //if the word is an actual word, add it to the set
                    {
                        set.add(stringbuilder.toString()); //set does not allow for duplicates therefore gets rid of our dupes (which come from the non-perfect trimming system)
                    }
                }
            }
            stringToWord(set, word, wordList);
        }
        return new ArrayList<Word>(wordList);
    }

    private int getWordPoints(Word word)
    {
        final int[] TILE_VALUE = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
        ArrayList<Coordinates> oldLetterCoords = new ArrayList<>();
        int r = word.getFirstRow();
        int c = word.getFirstColumn();
        System.out.println();
        for (int i = 0; i<word.length(); i++)
        {
            if (board.getSquareCopy(r,c).isOccupied())
            {
                oldLetterCoords.add(new Coordinates(r,c));
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

        for (int i = 0; i<word.length(); i++)
        {
            char letter = word.getLetter(i);
            letter = Character.toUpperCase(letter);
            int letterValue = TILE_VALUE[(int) letter - (int) 'A'];
            if (oldLetterCoords.contains(new Coordinates(row,col)))
            {
                wordValue = wordValue + letterValue;
            }
            else
            {
                wordValue = wordValue + letterValue * board.getSquareCopy(row, col).getLetterMuliplier();
                wordMultiplier = wordMultiplier * board.getSquareCopy(row, col).getWordMultiplier();
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
        Word bestWord;

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
        bestWord = legalWords.get(bestWordIndex);
        return bestWord;
    }
}
