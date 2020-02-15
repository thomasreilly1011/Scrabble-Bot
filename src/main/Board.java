package main;

public class Board
{
    public static final int ROWS = 15;
    public static final int COLS = 15;

    //Square[][] squares = new Square()[ROWS][COLS];

    public Board()
    {
        //TODO Test the following code once toString is finished for bugs.
        for (int i=0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                //TRIPLE WORD CONDITIONS
                if((i == 0 || i == 14 || i == 7) && (j == 0 || j == 14 || j == 7)) {
                    if (i == 7 && j == 7) {
                        //Center Square
                    } else {
                        //Triple word
                    }
                }

                //DOUBLE WORD CONDITIONS
                if ((i == 1 || i ==  2 || i == 3 || i == 4) && (j == 1 || j == 2 || j == 3 || j == 4)) {
                    //Double word
                }

                //TRIPLE LETTER CONDITIONS
                if ((i == 1 || i == 13) && (j == 5 || j == 9)) {
                    //Triple Letter
                } else if ((i == 5 || i == 9) && (j == 1 || j == 5 || j == 9 || j == 13)) {
                    //Triple Letter
                }

                //DOUBLE LETTER CONDITIONS
                if ((i == 0 || i == 14) && (j == 3 || j == 11)){
                    //Double Letter
                }
                if ((i == 3 || i == 11) && (j == 0 || j == 14)) {
                    //Double Letter
                }
                if ((i == 3 || i == 12) && (j == 6 || j == 8)) {
                    //Double Letter
                }
                if ((i == 2 || i == 12) && (j == 6 || j == 8)) {
                    //Double Letter
                }
                if ((i == 6 || i == 8) && (j == 2 || j == 12)) {
                    //Double Letter
                }
                if ((i == 3 && j == 7) || (i == 7 && (j == 3 || j == 10)) || (i == 10 && j == 7)) {
                    //Double Letter
                }
                if ((i == 6 || i == 8) && (j == 6 || j == 8)) {
                    //Double Letter
                }

            }
        }
    }

}
