package Testing;

import main.Board;
import main.Pool;
import main.Tile;
import main.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardUnitTest extends Board
{

    Board board;
    Frame frame;

    @BeforeEach
    void init() {
        Pool.set();

        board = new Board();
        frame = new Frame();
        frame.createTestableFrame();
    }

    @Test
    void testResetBoard()
    {
        Tile t1 = new Tile('A', 1);
        Tile t2 = new Tile('D', 2);
        Tile t3 = new Tile('J', 8);
        Tile t4 = new Tile('Z', 10);

        board.squares[2][3].setTile(t1);
        board.squares[11][2].setTile(t2);
        board.squares[1][12].setTile(t3);
        board.squares[13][10].setTile(t4);

        System.out.println("The tiles on the selected squares of the board before resetBoard() is called:");
        System.out.print(board.squares[2][3].getTile() + " | ");
        System.out.print(board.squares[11][2].getTile() + " | ");
        System.out.print(board.squares[1][12].getTile() + " | ");
        System.out.print(board.squares[13][10].getTile());

        board.resetBoard();

        System.out.println();
        System.out.println("The tiles on the selected squares of the board after resetBoard() is called:");

        System.out.print(board.squares[2][3].getTile() + " | ");
        System.out.print(board.squares[11][2].getTile() + " | ");
        System.out.print(board.squares[1][12].getTile() + " | ");
        System.out.print(board.squares[13][10].getTile());

        assertNull(board.squares[2][3].getTile());
        assertNull(board.squares[11][2].getTile());
        assertNull(board.squares[1][12].getTile());
        assertNull(board.squares[13][10].getTile());

    }

    @Test
    void testPlaceWord()
    {
        System.out.println(frame);

        assertTrue(board.placeWord(3, 7, "HELLO", frame, true));
        assertTrue(board.placeWord(7, 6, "NOT", frame, false));

        assertEquals(new Tile('H', 4), board.squares[3][7].getTile());
        assertEquals(new Tile('E', 1), board.squares[4][7].getTile());
        assertEquals(new Tile('L', 1), board.squares[5][7].getTile());
        assertEquals(new Tile('L', 1), board.squares[6][7].getTile());
        assertEquals(new Tile('O', 1), board.squares[7][7].getTile());

        assertEquals(new Tile('N', 1), board.squares[7][6].getTile());
        assertEquals(new Tile('T', 1), board.squares[7][8].getTile());

        System.out.println(board);
    }

    @Test
    void testCheckBounds() // TODO might not be thorough enough, I just got bored. ~Spawn.
    {
        assertTrue(checkBounds(0, 0, true, "Oxyphenbutazone"));
        assertTrue(checkBounds(14, 0, false, "Oxyphenbutazone"));
        assertTrue(checkBounds(7, 7, true, "Eighteen"));
        assertTrue(checkBounds(7, 7, false, "Eighteen"));

        assertFalse(checkBounds(7, 7, false, "Something"));
        assertFalse(checkBounds(0, 0, true, "Oxyphenbutazones"));
        assertFalse(checkBounds(14, 0, false, "Oxyphenbutazones"));
    }

    @Test
    void testCheckIntersection()
    {
    	board.placeWord(3, 7, "HELLO", frame, true);

    	Tile[] E = {new Tile('E', 1), null ,null};
    	
    	assertEquals(Arrays.toString(E), Arrays.toString(board.checkIntersection(4, 6, "HEY", false)));

    	Tile[] L = {new Tile('L', 1), null, null, null, null};

    	assertEquals(Arrays.toString(L), Arrays.toString(board.checkIntersection(5, 7, "LEMON", false)));

        Tile[] O = {new Tile('O', 1), null, null, null};
    	
    	assertEquals(Arrays.toString(O), Arrays.toString(board.checkIntersection(7, 5, "GOLD", false)));
    	 
    	board.resetBoard();
    }

    @Test
    void testIntersectsCenter()
    {
        //Expected true cases:
        assertTrue(intersectsCenter(7, 7, "Hello", true));
        assertTrue(intersectsCenter(7, 7, "Hello", false));
        assertTrue(intersectsCenter(5, 7, "Hello", true));
        assertTrue(intersectsCenter(7, 5, "Hello", false));

        //Expected false cases:
        assertFalse(intersectsCenter(7, 0, "Hi", true));
        assertFalse(intersectsCenter(1, 7, "Hi", true));
        assertFalse(intersectsCenter(6, 7, "Hi", false));

    }

    @Test
    void testHasTiles()
    {
        System.out.println(frame);

        //Mock 'intersectingTiles' arrays for testing purposes:
        Tile[] iT1 = {new Tile('O', 1)};
        Tile[] iT2 = {null, null, null, null, null};
        Tile[] iT3= {new Tile('C', 3)};
        Tile[] iT4= {new Tile('T', 1), new Tile('R', 1)};
        Tile[] iT5= {new Tile('T', 1)};

        //Expected true cases:
        assertTrue(hasTiles(iT2, frame, "Hello"));
        assertTrue(hasTiles(iT1, frame, "Not"));
        assertTrue(hasTiles(iT3, frame, "Cot"));
        assertTrue(hasTiles(iT4, frame, "Hotter"));

        //Expected false cases:
        assertFalse(hasTiles(iT2, frame, "Supercalifragilisticexpialidocious"));//Missing almost all tiles
        assertFalse(hasTiles(iT5, frame, "Hotter")); //Missing 'R' tile
        assertFalse(hasTiles(iT5, frame, "Rate")); //Missing 'A' tile

    }

    @Test
    void testToString()
    {
        System.out.println("Plain board displaying square types.\n" + board);
        String board1 = "                             Scrabble Board\n" +
                " ----------------------------------------------------------------------------\n" +
                " | TW |    |    | DL |    |    |    | TW |    |    |    | DL |    |    | TW |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | DW |    |    |    | TL |    |    |    | TL |    |    |    | DW |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DW |    |    |    | DL |    | DL |    |    |    | DW |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | DL |    |    | DW |    |    |    | DL |    |    |    | DW |    |    | DL |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    |    |    | DW |    |    |    |    |    | DW |    |    |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | TL |    |    |    | TL |    |    |    | TL |    |    |    | TL |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DL |    |    |    | DL |    | DL |    |    |    | DL |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | TW |    |    | DL |    |    |    | ** |    |    |    | DL |    |    | TW |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DL |    |    |    | DL |    | DL |    |    |    | DL |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | TL |    |    |    | TL |    |    |    | TL |    |    |    | TL |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    |    |    | DW |    |    |    |    |    | DW |    |    |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | DL |    |    | DW |    |    |    | DL |    |    |    | DW |    |    | DL |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DW |    |    |    | DL |    | DL |    |    |    | DW |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | DW |    |    |    | TL |    |    |    | TL |    |    |    | DW |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | TW |    |    | DL |    |    |    | TW |    |    |    | DL |    |    | TW |\n" +
                " ----------------------------------------------------------------------------\n";
        assertEquals(board1, board.toString());

        String board2 = "                             Scrabble Board\n" +
                " ----------------------------------------------------------------------------\n" +
                " | TW |    |    | DL |    |    |    | TW |    |    |    | DL |    |    | TW |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | DW |    |    |    | TL |    |    |    | TL |    |    |    | DW |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DW |    |    |    | DL |    | DL |    |    |    | DW |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | DL |    |    | DW |    |    |    | H4 |    |    |    | DW |    |    | DL |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    |    |    | DW |    |    | E1 |    |    | DW |    |    |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | TL |    |    |    | TL |    | L1 |    | TL |    |    |    | TL |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DL |    |    |    | DL | L1 | DL |    |    |    | DL |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | TW |    |    | DL |    |    |    | O1 |    |    |    | DL |    |    | TW |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DL |    |    |    | DL |    | DL |    |    |    | DL |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | TL |    |    |    | TL |    |    |    | TL |    |    |    | TL |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    |    |    | DW |    |    |    |    |    | DW |    |    |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | DL |    |    | DW |    |    |    | DL |    |    |    | DW |    |    | DL |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    |    | DW |    |    |    | DL |    | DL |    |    |    | DW |    |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " |    | DW |    |    |    | TL |    |    |    | TL |    |    |    | DW |    |\n" +
                " ----------------------------------------------------------------------------\n" +
                " | TW |    |    | DL |    |    |    | TW |    |    |    | DL |    |    | TW |\n" +
                " ----------------------------------------------------------------------------\n";
        board.placeWord(3,7,"HELLO",frame,true);
        System.out.println("Board after placing word 'hello'\n" + board);
        assertEquals(board2, board.toString());

    }

}