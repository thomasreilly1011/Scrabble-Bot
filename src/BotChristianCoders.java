import java.util.*;

public class BotChristianCoders implements BotAPI {

    Scrabble scrabble;

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
        ArrayList<String> legalWords = findLegalWords(possibleWords);
        String word = mostValuableWord(legalWords);
        return "PLACE " + word;
        //TODO This algorithm only ever places words. It may be better to refill at some stages? It should also pass if there are no possible words?
    }

    /**
     * 1. Search the board for all possible word places soring them in a list in the form H8 A L****** where *'s
     * represent possible letters.
     * @return ArrayList<String> of all possible word placements.
     */
    private ArrayList<String> findPossibleWords() {
        return null;
    }

    /**
     * 2. Search through the dictionary word tree using the letters found and replacing the *'s with letter permutations
     * from the frame.
     * @param possibleStrings ArrayList<String> of possible word placements in the form H8 A L******
     * @return ArrayList<String> of all legal word word placements in the form H8 A legalword, where legalword is a fully
     * defined, legal word. //TODO need to change this at some point - Seáááán.
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

    Frame botFrame = new Frame();

    private Set<Word> findLegalWords(ArrayList<String> possibleStrings, Frame botFrame, int[] row, int[] col, boolean[] isHorizontal)
    {                 //0123456
        //String frame = "ozflxud"; //theoretical letters of the bots frame
        String frame = botFrame.toString();

        ArrayList<String> arrL = getPermutations(frame);

        arrL.remove("");

        int wordLength = 6; //rudolf is 6 letters long
        int wordLengthIndex = wordLength-1; //just for me to easier visualise the wordLength
        int index = 0; //TODO SET THIS (this example is J9) (0 because r is at the beginning of the possible word to be formed at J9)
        char ch = 'r'; //TODO SET THIS (this example is J9)

        Set<String> set = new LinkedHashSet<String>();

        for (String s : arrL) {
            //TODO hoping to find a r udolf. If you run the program and ctrl F, it finds rudolf, one time. Yay. Using example of J9 from google doc (existing R)
            StringBuilder stringbuilder = new StringBuilder(s);
            stringbuilder.insert(index, ch);

            for(int i=0; i< stringbuilder.length()-wordLengthIndex; i++) {
                stringbuilder.deleteCharAt(stringbuilder.length()-1); //trims the strings to only output the permutations that fit in wordLength
            }

            set.add(stringbuilder.toString()); //set does not allow for duplicates therefore gets rid of our dupes (which come from the non-perfect trimming system)
        }

        //System.out.println(set);

        for(int i=0; i<set.size(); i++) {

        }
    }

    /**
     * 3. Score the word Produced.
     * @param legalWords ArrayList<String> of legal word placements in the form H8 A legalword
     * @return A single legal word placement that is the highest scoring option.
     */
    private String mostValuableWord(ArrayList<String> legalWords) {
        //TODO, seems relatively simple, arraylist of the words is given, method must calculate all their scores and play the highest one. Need to delve into his code to figure out how scoring works.
        return null;
    }
}