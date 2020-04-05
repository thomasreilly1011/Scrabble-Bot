package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private final Scanner dictionaryReader;

    /**
     * Constructor method that sets up the dictionary with the given File.
     */
    public Dictionary(String fileName) throws FileNotFoundException {
        File dictionary = new File(fileName);
        dictionaryReader = new Scanner(dictionary);
        //TODO Do we want to read the File into a Trie Data Structure for quicker validity checking
        // (possibly create a branch for this)
    }

    /**
     Function to check the validity of a given word according to the Dictionary.
     @param word The word to be checked.
     @return true if given word is valid, false if given word is invalid.
     */
    public boolean challenge(String word)
    {
        //Dictionary run = new Dictionary();
        while(dictionaryReader.hasNextLine())
        {
            String in = dictionaryReader.nextLine();
            in = in.toUpperCase();
            if(in.equals(word))
            {
                return true;
            }
        }
        return false;
    }


    /*
    TEMP NOTE FOR DANYUL:
    All of the words of the dictionary file are on separate lines. So we should read them line by line rather than having
    to use any fancy whitespace separating stuff.
     */
}
