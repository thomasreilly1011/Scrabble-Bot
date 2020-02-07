package main;

import java.util.ArrayList;

public class Frame {
    private static final int NUM_TILES = 7;

    private ArrayList<Tile> tiles = new ArrayList<>();

    Frame() {
        this.refill();
    }

    private void refill() {
        //Needs Pool class
        //First clear all elements from tiles.

        for(int i=0; i<tiles.size(); i++) {
            //Send tiles back to pool.
            Pool.returnTile(tiles.get(i));
            tiles.remove(i);
        }
        //Then, fill it with random tiles from Pool.
        for(int i=0; i<NUM_TILES; i++) {
            tiles.add(PlayerTest.pool.drawRandomTile());
        }
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
        if (!this.hasString(Character.toString(letter))) {
            throw new IllegalArgumentException("Given letter does not exist in this frame");
        }

        //Then, find that letter and remove it.
        for (int i=0; i<tiles.size(); i++) {
            if(tiles.get(i).getLetter() == letter){
                tiles.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return tiles.isEmpty();
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

    /*
    Takes a string as input and checks to see if the letters of the frame can make up the word in the string.
     */
    public boolean hasString(String word) {
        //First, exception handling..
        word = word.trim();
        if (word.contains(" ")) {
            throw new IllegalArgumentException("hasString should only take a singular word as argument");
        }
        if (word.matches("^a-z A-Z")) {
            throw new IllegalArgumentException("Input has characters that do not respond to a scabble tile");
        }
        word = word.toLowerCase();

        //Then, check if the letters of the word against the letters in the tiles of the frame..
        boolean hasString = false;
        char[] wordCharArray;
        wordCharArray = word.toCharArray();
        ArrayList<Tile> tempTiles = new ArrayList<Tile>();
        tempTiles = (ArrayList<Tile>) tiles.clone();
        for (char c : wordCharArray) {
            for (int j = 0; j < tempTiles.size(); j++) {
                if (tempTiles.get(j).getLetter() == c) {
                    tempTiles.remove(j);
                    hasString = true;
                    break;
                }
            }
            if (!hasString) {
                return false;
            }
        }
        return false;
    }

    /*
    Allows access to the tiles of the array and therefore also the letters.
     */
    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
