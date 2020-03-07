package main;

public class Board
{
    //Error codes:
    static final int OUT_OF_BOUNDS = 0;
    static final int NO_CONNECTION = 1;
    static final int INSUFFICIENT_TILES = 2;
    static final int SUCCESS = 5;

    //Board dimensions:
    private static final int ROWS = 15;
    private static final int COLS = 15;

    public Square[][] squares = new Square[ROWS][COLS];

    public Board()
    {
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

    int score = 0;
    int wordmultiplier = 1;

    public int scoring(int row, int col, int i)
    {

        int lettermultiplier = squares[row + i][col].getType().getValue();

        if(lettermultiplier == 4) //eg if tile is placed on double word tile
        {
            wordmultiplier *= 2;
            lettermultiplier = 1;
        }
        else if(lettermultiplier == 5) //eg if tile is placed on triple word tile
        {
            wordmultiplier *= 1;
            lettermultiplier = 1;
        }

        return score += lettermultiplier * squares[row + i][col].getTile().getValue();
    }

    public int placeWord(int row, int col, String word, Frame frame, boolean verticle)
    {
        word = word.toUpperCase();

        //First perform all tests to make sure this is a valid move.
        if(!checkBounds(row, col, verticle, word))
        {
            System.out.println("That word is out of bounds!");
            return OUT_OF_BOUNDS;
        }

        Tile[] intersectingTiles = checkIntersection(row, col, word, verticle);

        //Check that a tile is either being placed at the origin (the first play of the game) or being placed adjacent with another tile (the only other legal play)
        if(!intersectsCenter(row, col, word, verticle) && intersectingTiles[0] == null)
        {
            //The word isn't being placed at the origin and it does not connect with other words on the board.
            System.out.println("That word doesn't go through the origin or another word on the board");
            return NO_CONNECTION;
        }

        if(!hasTiles(intersectingTiles, frame, word))
        {
            System.out.println("You don't have the tiles for that word");
            return INSUFFICIENT_TILES;
        }

        // All checks are complete, physical placing of the word on the board proceeds..

        int score = 0;

        char[] letters = word.toCharArray();

        if (verticle)
        {
            for (int i = 0; i < word.length(); i++)
            {
                if(squares[row + i][col].isEmpty())
                {
                    squares[row + i][col].setTile(frame.removeTile(letters[i]));
                    scoring(row, col, i);
                }
            }
        }
        else
            {
            for (int i = 0; i < word.length(); i++)
            {
                if (squares[row][col + i].isEmpty())
                {
                    squares[row][col + i].setTile(frame.removeTile(letters[i]));
                    scoring(row, col, i);
                }
            }
        }

        score = score*wordmultiplier;

        return SUCCESS;
    }

    /*
    Simply checks if the word placed at the coordinates fits the bounds of the board.
    If it fits, returns true.
    If it overlaps the edge of the board it returns false.
     */
    protected boolean checkBounds(int row, int col, boolean verticle, String word)
    {
        if(verticle)
        {
            return word.length() + row <= ROWS;
        }
        else
            {
                return word.length() + col <= COLS;
        }
    }

    /*
    Checks to see if the word intersects any tiles.
    If it does not, it returns null
    If it does, it returns an array of tiles containing that it intersects.
     */
    public Tile[] checkIntersection(int row, int col, String word, boolean verticle) {
        Tile[] intersectTiles = new Tile[word.length()];
        int j = 0;

        if (verticle)
        {
            for (int i = 0; i < word.length() ; i++)
            {
                if (!squares[row+i][col].isEmpty())
                {
                    intersectTiles[j] = squares[row+i][col].getTile();
                    j++;
                }
            }
        }
        else
        {
            for (int i = 0; i < word.length() ; i++)
            {
                if (!squares[row][col+i].isEmpty())
                {
                    intersectTiles[j] = squares[row][col+i].getTile();
                    j++;
                }
            }
        }
        return intersectTiles;
    }

    protected boolean intersectsCenter(int row, int col, String word, boolean verticle) {
        if (row == 7 || col == 7) {
            if (verticle) {
                for (int i = 0; i < word.length(); i++)
                {
                    if (row+i == 7 && col == 7) {
                        return true;
                    }
                }
            }
            else
            {
                for (int i = 0; i < word.length(); i++)
                {
                    if (row == 7 && col+i == 7) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    /*
    Checks if the word can be made from the tiles in the frame and any intersecting tiles.
    Returns true if the word can be made.
    Returns false otherwise.
    NOTE check must fail if all tiles in intersectingTiles haven't been used in the making of the word.
     */
    protected boolean hasTiles(Tile[] intersectingTiles, Frame frame, String word)
    {
        word = word.toUpperCase();

        //Perform a check to make sure all intersecting tiles are used in the desired word.
        boolean pass = false;

        if (intersectingTiles[0] != null) {
            for (Tile t:intersectingTiles)
            {
                if (t == null)
                {
                    break;
                }
                for (int i = 0; i < word.length(); i++)
                {
                    if (word.charAt(i) == t.getLetter())
                    {
                        pass = true;
                        //If the intersecting tile is part of the word, remove it from the string as it is not needed in the Frame part of the test.
                        word = word.replace(word.charAt(i), ' ');
                    }
                }
                if (!pass)
                {
                    return false;
                }
                pass = false;
            }
        }
        //Now check that the frame has all remaining required letters to finish making up the word.
        return frame.hasString(word);
    }


    @Override
    public String toString()
    {
        StringBuilder board = new StringBuilder();
    	board.append("                             Scrabble Board\n");
        for (int i = 0; i < ROWS; i++)
        {
        	
            board.append(" ----------------------------------------------------------------------------\n");
            for (int j = 0; j < COLS; j++)
            {
            	board.append(" | ");

                if(squares[i][j].getTile() != null)
                {
                    board.append(squares[i][j].getTile().toString());
                }
                else if (squares[i][j].getType() == SquareType.CENTRE)
                {
                    board.append("**");
                } 
                else if (squares[i][j].getType() == SquareType.DL)
                {
                    board.append("DL");
                } 
                else if (squares[i][j].getType() == SquareType.TL)
                {
                    board.append("TL");
                }
                else if (squares[i][j].getType() == SquareType.DW)
                {
                    board.append("DW");
                } 
                else if (squares[i][j].getType() == SquareType.TW)
                {
                    board.append("TW");
                } 
                else 
                {
                    board.append("  ");
                }
               
            }
            board.append(" |\n");
        }
        board.append(" ----------------------------------------------------------------------------\n");
        return board.toString();
    }



}