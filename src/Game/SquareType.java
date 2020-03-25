package Game;

public enum SquareType
{
    CENTRE(1), //centre square
    DL(2),     //double letter
    TL(3),     //triple letter
    DW(4),     //double word
    TW(5),      //triple word
    BLANK(1);

    private int value = 0;
    private SquareType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}