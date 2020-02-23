package main;

public class Tile
{
    char letter;
    int value;

    public Tile(char letter, int value)
    {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter()
    {
        return letter;
    }

    public int getValue()
    {
        return value;
    }

    public String toString()
    {
        return letter + Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        Tile t;
        try {
            t = (Tile) obj;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Given object is not a tile");
        }
        if (t.getLetter() == letter && t.getValue() == value) {
            return true;
        } else {
            return false;
        }
    }
}
