package main;

public class Square
{
    private SquareType e;
    private Tile t;

    //constructor
    Square(SquareType e)
    {
        this.e = e;
        this.t = null;
    }

    //type getter
    SquareType getType()
    {
        return e;
    }

    public void setTile(Tile t)
    {
        this.t = t;
    }

    public void clearMultipliers()
    {
        this.e = null;
    }

    public Tile removeTile()
    {
        Tile temp = this.t;
        this.t = null;
        return temp;
    }

    //tile getter
    public Tile getTile()
    {
        return t;
    }

    //checks to make sure there is no tile already on the square
    boolean isEmpty()
    {
        return t == null;
    }

}
