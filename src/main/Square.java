package main;

public class Square
{
    SquareType e;
    Tile t;

    public Square(SquareType e) //constructor
    {
        this.e = e;
        this.t = null;
    }

    public SquareType getType() //type getter
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

    public void removeTile() 
    {
        this.t = null;
    }

    public Tile getTile() //tile getter
    {
        return t;
    }

    public boolean isEmpty() //checks to make sure there is no tile already on the square
    {
        return t == null;
    }

}
