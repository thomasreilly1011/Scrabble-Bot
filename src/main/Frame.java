package main;

import java.util.ArrayList;

public class Frame {
    private static final int NUM_TILES = 7;

    private ArrayList<Tile> tiles = new ArrayList<>();

    Frame() {
        this.refill();
    }

    public void refill() {
        //Needs Pool class
        //First clear all elements from tiles.
        for(int i=0; i<tiles.size(); i++) {
            //Send tiles back to pool.
            Pool.returnTile(tiles.get(i));
        }
        //Then remove them all from the frame.
        tiles.removeAll(tiles);
        //Then, fill it with random tiles from Pool.
        for(int i=0; i<NUM_TILES; i++) {
            tiles.add(Pool.drawRandomTile());
        }
    }

    /*
    Allows access to the tiles of the array and therefore also the letters.
     */
    public Tile getTile(int i) {
        return tiles.get(i);
    }

    /*
    Returns true if tile has successfully been removed from the frame.
    Returns false if the tile could not be found in the frame.
     */
    public boolean removeTile(char letter) {
        //First, exception handling
        if (!Character.isLetter(letter)) {
            throw new IllegalArgumentException("removeTile can only take a letter as input. Digits and special characters are invalid.");
        }

        //Then, find that letter and return it.
        for (int i=0; i<tiles.size(); i++) {
            if(tiles.get(i).getLetter() == letter){
                Pool.returnTile(tiles.get(i));
                tiles.remove(i);
                //Then, add a new random tile from the pool.
                tiles.add(Pool.drawRandomTile());
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
    }


    /*
    Takes a string as input and checks to see if the letters of the frame can make up the word in the string.
     */
    public boolean hasString(String w) {
        //First, exception handling..
        String word = w.toUpperCase();
        if (word.matches("^a-z A-Z")) {
            throw new IllegalArgumentException("Input has characters that do not respond to a scabble tile");
        }
        word = word.toLowerCase();

        //Then, check if the letters of the word against the letters in the tiles of the frame..
        boolean hasChar = false;
        char[] wordCharArray;
        wordCharArray = word.toCharArray();
        ArrayList<Tile> tempTiles = new ArrayList<Tile>();
        tempTiles = (ArrayList<Tile>) tiles.clone();
        for (char c : wordCharArray) {
            for (int j = 0; j < tempTiles.size(); j++) {
                if (tempTiles.get(j).getLetter() == Character.toUpperCase(c)) {
                    tempTiles.remove(j);
                    hasChar = true;
                    break;
                }
            }
            if (!hasChar) {
                return false;
            }
            hasChar = false;
        }
        return true;
    }

    /*
    Used to display the letters of a frame. (and the value of each letter)
     */
    public String toString() {
        //Used to display the contents of a Frame.
        String s = "[";
        for (int i = 0; i < tiles.size(); i++) {
            s += tiles.get(i).getLetter();
            s += ":" + tiles.get(i).getValue();
            if (i+1 != tiles.size()) {
                s += ", ";
            }
        }
        s += "]";
        return s;
    }
}
