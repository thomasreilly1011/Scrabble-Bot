package main;

public class Board
{
    public static final int ROWS = 15;
    public static final int COLS = 15;

    Square[][] squares = new Square[ROWS][COLS];

    public Board()
    {
        //TODO Test the following code once toString is finished for bugs.
        for (int i=0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                //TRIPLE WORD & CENTER CONDITIONS
                if((i == 0 || i == 14 || i == 7) && (j == 0 || j == 14 || j == 7))
                {
                    if (i == 7 && j == 7)
                    {
                        squares[i][j] = new Square(SquareType.CENTRE, null);
                    }
                    else
                    {
                        squares[i][j] = new Square(SquareType.TW, null);
                    }
                }
                //DOUBLE WORD CONDITIONS
                else if ((i == 1 || i ==  2 || i == 3 || i == 4) && (j == 1 || j == 2 || j == 3 || j == 4))
                {
                    squares[i][j] = new Square(SquareType.DW, null);
                }
                //TRIPLE LETTER CONDITIONS
                else if ((i == 1 || i == 13) && (j == 5 || j == 9))
                {
                    squares[i][j] = new Square(SquareType.TL, null);
                } else if ((i == 5 || i == 9) && (j == 1 || j == 5 || j == 9 || j == 13))
                {
                    squares[i][j] = new Square(SquareType.TL, null);
                }
                //DOUBLE LETTER CONDITIONS
                else if ((i == 0 || i == 14) && (j == 3 || j == 11))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else if ((i == 3 || i == 11) && (j == 0 || j == 14))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else if ((i == 3 || i == 12) && (j == 6 || j == 8))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else if ((i == 2 || i == 12) && (j == 6 || j == 8))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else if ((i == 6 || i == 8) && (j == 2 || j == 12))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else if ((i == 3 && j == 7) || (i == 7 && (j == 3 || j == 10)) || (i == 10 && j == 7))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else if ((i == 6 || i == 8) && (j == 6 || j == 8))
                {
                    squares[i][j] = new Square(SquareType.DL, null);
                }
                else
                {
                    squares[i][j] = new Square(SquareType.BLANK, null);
                }
            }
        }
    }

}
