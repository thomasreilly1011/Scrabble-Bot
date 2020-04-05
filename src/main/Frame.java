package main;

import java.util.ArrayList;

public class Frame implements Cloneable
{
    private static final int NUM_TILES = 7;

    private ArrayList<Tile> tiles = new ArrayList<>();

    private final Pool pool;

    public Frame(Pool pool)
    {
        this.pool = pool;
        this.refill();
    }

    public void refill()
    {
        //Needs Pool class
        //First clear all elements from tiles.
        for (Tile tile : tiles)
        {
            //Send tiles back to pool.
            pool.returnTile(tile);
        }
        //Then remove them all from the frame.
        tiles.removeAll(tiles);
        //Then, fill it with random tiles from Pool.
        for(int i=0; i<NUM_TILES; i++)
        {
            tiles.add(pool.getRandomTile());
        }
    }

    public void createTestableFrame() //for the use of testing placeWord in BoardTest
    {
        tiles.removeAll(tiles);

        tiles.add(new Tile('H', 4));
        tiles.add(new Tile('E', 1));
        tiles.add(new Tile('L', 1));
        tiles.add(new Tile('L', 1));
        tiles.add(new Tile('O', 1)); //adds the letters to create Hello

        tiles.add(new Tile('N', 1));
        tiles.add(new Tile('T', 1)); //adds the letters to create Not off the O in Hello
    }

    /*
    Allows access to the tiles of the array and therefore also the letters.
     */
    public Tile getTile(int i)
    {
        return tiles.get(i);
    }

    /*
    Returns true if tile has successfully been removed from the frame.
    Returns false if the tile could not be found in the frame.
     */
    public Tile removeTile(char letter)
    {
        Scrabble.newTiles = null;
        //First, exception handling
        if (!Character.isLetter(letter) && !(letter == '_'))
        {
            throw new IllegalArgumentException("removeTile can only take a letter as input. Digits and special characters are invalid.");
        }

        letter = Character.toUpperCase(letter);

        //Then, find that letter and return it.
        for (int i=0; i<tiles.size(); i++)
        {
            if(tiles.get(i).getLetter() == letter)
            {
                Tile temp = tiles.remove(i);
                //Then, add a new random tile from the pool.
                Tile newTile = pool.getRandomTile();
                Scrabble.newTiles[i] = newTile;
                tiles.add(pool.getRandomTile());
                return temp;
            }
        }

        return null;
    }

    /*
    Returns the specified tiles back to the Pool.
     */
    public void removeTiles(Tile[] tiles) {
        for (Tile tile: this.tiles) {
            for (Tile newTile: tiles) {
                if (newTile.equals(tile)) {
                    pool.returnTile(tile);
                }
            }
        }
    }

    /*
    Adds given tile to the frame.
     */
    public void returnTile(Tile tile) {
        tiles.add(tile);
    }

    public boolean isEmpty()
    {
        return tiles.isEmpty();
    }


    /*
    Takes a string as input and checks to see if the letters of the frame can make up the word in the string.
     */
    public boolean hasString(String w)
    {
        //First, exception handling..
        String word = w.toUpperCase();
        for (int i=0; i<word.length(); i++)
        {
            if (!Character.isLetter(word.charAt(i)) && word.charAt(i) != ' ' && word.charAt(i) != '_')
            {
                throw new IllegalArgumentException("Input has characters that do not respond to a scrabble tile");
            }
        }

        word = word.toUpperCase();

        //Then, check if the letters of the word against the letters in the tiles of the frame..
        boolean hasChar = false;
        char[] wordCharArray;
        wordCharArray = word.toCharArray();
        ArrayList<Tile> tempTiles;
        tempTiles = (ArrayList<Tile>) tiles.clone();
        for (char c : wordCharArray)
        {
            for (int j = 0; j < tempTiles.size(); j++)
            {
                //NOTE if c == ' ', then you can ignore it as ' ' represents a tile that is already on the board.
                if (tempTiles.get(j).getLetter() == c)
                {
                    tempTiles.remove(j);
                    hasChar = true;
                    break;
                }
                else if (c == ' ')
                {
                    hasChar = true;
                    break;
                }
            }
            if (!hasChar)
            {
                return false;
            }
            hasChar = false;
        }
        return true;
    }

    @Override
    protected Frame clone() throws CloneNotSupportedException {
        Frame clone = new Frame(new Pool());
        clone.tiles = (ArrayList<Tile>) this.tiles.clone();
        return clone;
    }


//    @Override
//    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }

    /*
            Used to display the letters of a frame. (and the value of each letter)
             */
    public String toString()
    {
        //Used to display the contents of a Frame.
        String s = "[";
        for (int i = 0; i < tiles.size(); i++)
        {
            s += tiles.get(i).getLetter();
            s += ":" + tiles.get(i).getValue();
            if (i+1 != tiles.size())
            {
                s += ", ";
            }
        }
        s += "]";
        return s;
    }
}
