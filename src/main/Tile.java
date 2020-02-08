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
        return this.letter;
    }

    public int getValue()
    {
        return this.value;
    }
}