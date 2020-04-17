package main;

public class Square implements Cloneable
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
    public SquareType getType()
    {
        return e;
    }
    void setType(SquareType e)
    {
        this.e = e;
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return (Square) super.clone();
        } catch (CloneNotSupportedException e){
            Square clone = new Square(this.e);
            if (!this.isEmpty()) {
                clone.setTile((Tile) this.t.clone());
            }
            return clone;
        }

    }
}
