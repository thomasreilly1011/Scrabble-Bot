package main;

public class Board
{
    private static final int ROWS = 15;
    private static final int COLS = 15;

    private Square[][] squares = new Square[ROWS][COLS];

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
                        squares[i][j] = new Square(SquareType.CENTRE);
                    }
                    else
                    {
                        squares[i][j] = new Square(SquareType.TW);
                    }
                }
                //DOUBLE WORD CONDITIONS
                else if ((i == 1 || i == 13) && (j == 1 || j == 13)) {
                    squares[i][j] = new Square(SquareType.DW);
                } else if ((i == 2 || i == 12) && (j == 2 || j == 12)) {
                    squares[i][j] = new Square(SquareType.DW);
                } else if ((i == 3 || i == 11) && (j == 3 || j == 11)) {
                    squares[i][j] = new Square(SquareType.DW);
                } else if ((i == 4 || i == 10) && (j == 4 || j == 10)) {
                    squares[i][j] = new Square(SquareType.DW);
                }
                //TRIPLE LETTER CONDITIONS
                else if ((i == 1 || i == 13) && (j == 5 || j == 9))
                {
                    squares[i][j] = new Square(SquareType.TL);
                } else if ((i == 5 || i == 9) && (j == 1 || j == 5 || j == 9 || j == 13))
                {
                    squares[i][j] = new Square(SquareType.TL);
                }
                //DOUBLE LETTER CONDITIONS
                else if ((i == 0 || i == 14) && (j == 3 || j == 11))
                {
                    squares[i][j] = new Square(SquareType.DL);
                }
                else if ((i == 3 || i == 11) && (j == 0 || j == 14))
                {
                    squares[i][j] = new Square(SquareType.DL);
                }
                else if ((i == 2 || i == 12) && (j == 6 || j == 8))
                {
                    squares[i][j] = new Square(SquareType.DL);
                }
                else if ((i == 6 || i == 8) && (j == 2 || j == 12))
                {
                    squares[i][j] = new Square(SquareType.DL);
                }
                else if ((i == 3 && j == 7) || (i == 7 && (j == 3 || j == 11)) || (i == 11 && j == 7))
                {
                    squares[i][j] = new Square(SquareType.DL);
                }
                else if ((i == 6 || i == 8) && (j == 6 || j == 8))
                {
                    squares[i][j] = new Square(SquareType.DL);
                }
                else
                {
                    squares[i][j] = new Square(SquareType.BLANK);
                }
            }
        }
    }

    public void resetBoard()
    {
        for(int i=0; i<15; i++)
        {
            for(int j=0; j<15; j++)
            {
                squares[i][j].setTile(null);
            }
        }
    }

    public boolean placeWord(int row, int col, String word, Frame frame, boolean verticle)
    {
        //First perform all tests
        if(!checkBounds(row, col, verticle, word))
        {
            return false;
        }

        Tile[] intersectingTiles = checkIntersection(row, col, word, verticle);

        //Check that a tile is either being placed at the origin (the first play of the game) or being placed adjacent with another tile (the only other legal play)
        if((row != 7 || col != 7) && intersectingTiles == null)
        {
            //The word isn't being placed at the origin and it does not connect with other words on the board.
            return false;
        }

        if(!hasTiles(intersectingTiles, frame, word))
        {
            if (!hasTiles(intersectingTiles, frame, word))
            {
                return false;
            }
            if (!squares[row][col].isEmpty()) //Whats this
            {
                return false;
            }

            /////////////////////////////////////////////////////////////////////// checks are complete, main body proceeds

            char[] letters = word.toCharArray();

            if (verticle)
            {
                for (int i = 0; i < word.length(); i++)
                {
                    if(squares[row + i][col].isEmpty())
                    {
                        squares[row + i][col].setTile(frame.removeTile(letters[i]));
                    }
                    /*else //else may become redundant if hasTiles can implement the intersecting tiles part
                    {
                        if (!(squares[row + i][col].getTile().getLetter() == letters[i]))
                        {
                            return false; //returns false if the tile the word is intersecting is not apart of the word
                        }
                    }*/
                }
            }
            else
                {
                for (int i = 0; i < word.length(); i++)
                {
                    if (squares[row][col + i].isEmpty())
                    {
                        squares[row][col + i].setTile(frame.removeTile(letters[i]));
                    }
                    /*else //else may become redundant if hasTiles can implement the intersecting tiles part
                    {
                        if (!(squares[row][col + i].getTile().getLetter() == letters[i]))
                        {
                            return false; //returns false if the tile the word is intersecting is not apart of the word
                        }
                    }*/
                }
            }
        }
        return true;
    }
    /*
    Simply checks if the word placed at the coordinates fits the bounds of the board.
    If it fits, returns true.
    If it overlaps the edge of the board it returns false.
     */
    private boolean checkBounds(int row, int col, boolean verticle, String word)
    {
        return false;
    }

    /*
    Checks to see if the word intersects any tiles.
    If it does not, it returns null
    If it does, it returns an array of tiles containing that it intersects.
     */
    private Tile[] checkIntersection(int row, int col, String word, boolean verticle) {
        Tile[] iTiles = new Tile[word.length()];
        int j = 0;

        if (verticle)
        {
            for (int i = 0; i < word.length() ; i++)
            {
                if (squares[row][col+i].hasTile())
                {
                    iTiles[j] = squares[row][col+1].getTile();
                    j++;
                }
            }
        }
        else
        {
            for (int i = 0; i < word.length() ; i++)
            {
                if (squares[row+i][col].hasTile())
                {
                    iTiles[j] = squares[row+i][col].getTile();
                    j++;
                }
            }
        }
        return iTiles;
    }

    /*
    Checks if the word can be made from the tiles in the frame and any intersecting tiles.
    Returns true if the word can be made.
    Returns false otherwise.
    NOTE check must fail if all tiles in intersectingTiles haven't been used in the making of the word.
     */

    private boolean hasTiles(Tile[] intersectingTiles, Frame frame, String w)
    {
        String word = w;
        //Perform a check to make sure all intersecting tiles are used in the desired word.
        boolean pass = false;
        for (Tile t:intersectingTiles)
        {
            for (int i = 0; i < word.length(); i++)
            {
                if (word.charAt(i) == t.getLetter())
                {
                    pass = true;
                    //If the intersecting tile is part of the word, remove it from the string as it is not needed in the Frame part of the test.
                    word = word.replace(word.charAt(i), ' ');
                    break;
                }
            }
            if (!pass)
            {
                return false;
            }
            pass = false;
        }
            //Now check that the frame has all remaining required letters to finish making up the word.
        return frame.hasString(word);
    }


    @Override
    public String toString()
    {
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < squares.length; i++)
        {
            //board.append("\t" + "\t").append(i - 1);
            for (int j = 0; j < squares.length; j++)
            {
                //TODO Add an if for if there's a tile on a square (Display the letter & value of the tile).
                if (squares[i][j].getType() == SquareType.CENTRE)
                {
                    board.append("**");
                } else if (squares[i][j].getType() == SquareType.DL)
                {
                    board.append("DL");
                } else if (squares[i][j].getType() == SquareType.TL)
                {
                    board.append("TL");
                } else if (squares[i][j].getType() == SquareType.DW)
                {
                    board.append("DW");
                } else if (squares[i][j].getType() == SquareType.TW)
                {
                    board.append("TW");
                } else {
//                    if (i == 0) {
//                        board.append("\t").append(j - 1);
//                    } else {
                    board.append("  ");
//                    }
                }
            }
            board.append("\n");
        }
        return board.toString();
    }



}