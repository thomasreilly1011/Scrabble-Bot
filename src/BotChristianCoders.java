import java.util.ArrayList;

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
        return null;
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
            }
        }
        return false;
    }

    private ArrayList<PossibleWord> generateHorizontalPlacements(int row, int col) {
        //Step One: Find the furthest points you can generate horizontal words from.
        int start = col - tilesRemaining;
        int end = col + tilesRemaining;

        //Make sure the start and end don't spill over the board.
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

        //Check the horizontal space and cut off the start/end wherever you hit any other words.
        for (int i=start; i<col; )
        {
            if (board.getSquareCopy(row, i).isOccupied()) {
                start = i+2;
                i = start;
            }
            else if (board.getSquareCopy(row-1, i).isOccupied() || board.getSquareCopy(row+1, i).isOccupied())
            {
                start = i+1;
                i++;
            } else {
                i++;
            }
        }
        for (int i=col+1; i<end+1; )
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

        //Step Two: start and end are well defined. Now generate possible words of size frameTiles+1 down to 2.
        ArrayList<PossibleWord> placements = new ArrayList<>();
        for (int wordLength = tilesRemaining; wordLength > 1; wordLength--)
        {
            for (int startIndex = 0; startIndex <= col; startIndex++)
            {
                char letter = '\0';
                int letterIndex = -1;
                for (int i = 0; i < wordLength; i++)
                {
                    if (board.getSquareCopy(row, i).isOccupied())
                    {
                        letter = board.getSquareCopy(row, i).getTile().getLetter();
                        letterIndex = i;
                        break;
                    }
                }
                if (letterIndex != -1) {

                    placements.add(new PossibleWord(row, startIndex, true, wordLength, letter, letterIndex));
                } else
                    {
                    placements.add(new PossibleWord(row, startIndex, true, wordLength));
                }

            }
        }
        return placements;
    }

    private ArrayList<PossibleWord> generateVerticalPlacements(int row, int col) {
        //Step One: Find the furthest points you can generate horizontal words from.
        int start = row - tilesRemaining;
        int end = row + tilesRemaining;

        //Make sure the start and end don't spill over the board.
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

        //Check the horizontal space and cut off the start/end wherever you hit any other words.
        for (int i=start; i<row; )
        {
            if (board.getSquareCopy(i, col).isOccupied()) {
                start = i+2;
                i = start;
            }
            else if (board.getSquareCopy(i, col-1).isOccupied() || board.getSquareCopy(i, col+1).isOccupied())
            {
                start = i+1;
                i++;
            } else {
                i++;
            }
        }
        for (int i=row+1; i<end+1; )
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

        //Step Two: start and end are well defined. Now generate possible words of size frameTiles+1 down to 2.
        ArrayList<PossibleWord> placements = new ArrayList<>();
        for (int wordLength = tilesRemaining; wordLength > 1; wordLength--)
        {
            for (int startIndex = 0; startIndex <= row; startIndex++)
            {
                char letter = '\0';
                int letterIndex = -1;
                for (int i = 0; i < wordLength; i++)
                {
                    if (board.getSquareCopy(i, col).isOccupied()) {
                        letter = board.getSquareCopy(i, col).getTile().getLetter();
                        letterIndex = i;
                        break;
                    }
                }
                if (letterIndex != -1)
                {
                    placements.add(new PossibleWord(startIndex, col, true, wordLength, letter, letterIndex));
                } else {
                    placements.add(new PossibleWord(startIndex, col, true, wordLength));
                }
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

        public int getRow() { return row; }
        public int getColumn() { return column; }
        public boolean isHorizontal() { return isHorizontal; }
        public int getLength() { return length; }
        public char getExistingLetter() { return existingLetter; }
        public int getExistingLetterIndex() { return existingLetterIndex; }

        public boolean hasExistingLetter() { return existingLetter != '\0'; }
    }

    /**
     * 2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
     * from the frame.
     * @param possibleWords ArrayList<String> of possible word placements in the form H8 A L******
     * @return ArrayList<Word> of all legal word word objects.
     * defined, legal word.
     */
    private ArrayList<Word> findLegalWords(ArrayList<PossibleWord> possibleWords) {
        return null;
    }

    /**
     * 3. Score the word Produced.
     * @param legalWords ArrayList<Word> of legal Word objects.
     * @return A single legal Word object that is the highest scoring option.
     */
    private Word mostValuableWord(ArrayList<Word> legalWords) {
        //TODO, seems relatively simple, arraylist of the words is given, method must calculate all their scores and play the highest one. Need to delve into his code to figure out how scoring works.
        return null;
    }
}
