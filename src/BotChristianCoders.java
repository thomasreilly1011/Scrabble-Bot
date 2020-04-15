import java.util.ArrayList;

public class BotChristianCoders implements BotAPI {

    @Override
    public String getCommand() {
        /*
                            <--- Algorithm from 'hints' on Brightspace --->
        1. Search the board for all possible word places soring them in a list in the form H8 A L******
        2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
            from the frame.
        3. Score the word Produced.
        4. Place the highest scored word.
         */
        ArrayList<String> possibleWords = findPossibleWords();
        ArrayList<String> legalWords = findLegalWords(possibleWords);
        String word = mostValuableWord(legalWords);
        return "PLACE " + word;
        //TODO This algorithm only ever places words. It may be better to refill at some stages? It should also pass if there are no possible words?
    }

    private ArrayList<String> findPossibleWords() {
        return null;
    }

    private ArrayList<String> findLegalWords(ArrayList<String> possibleWords) {
        return null;
    }

    private String mostValuableWord(ArrayList<String> possibleWords) {
        return null;
    }
}
