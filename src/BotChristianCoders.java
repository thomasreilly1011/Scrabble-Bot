import java.util.ArrayList;

public class BotChristianCoders implements BotAPI {
    private PlayerAPI me;
    private OpponentAPI opponent;
    private BoardAPI board;
    private UserInterfaceAPI info;
    private DictionaryAPI dictionary;

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
        ArrayList<String> possibleWords = findPossibleWords();
        ArrayList<Word> legalWords = findLegalWords(possibleWords);
        Word word = mostValuableWord(legalWords);
        return "PLACE " + word.toString();
        //TODO This algorithm only ever places words. It may be better to refill at some stages? It should also pass if there are no possible words?
    }

    /**
     * 1. Search the board for all possible word places soring them in a list in the form H8 A L****** where *'s
     * represent possible letters.
     * @return ArrayList<String> of all possible word placements int the form H8 A L****** where *'s represent possible letters.
     */
    private ArrayList<String> findPossibleWords()
    {
        return null;
    }

    /**
     * 2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
     * from the frame.
     * @param possibleWords ArrayList<String> of possible word placements in the form H8 A L******
     * @return ArrayList<Word> of all legal word word objects.
     * defined, legal word.
     */
    private ArrayList<Word> findLegalWords(ArrayList<String> possibleWords) {
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
