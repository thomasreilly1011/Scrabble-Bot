package Testing;

import main.Board;
import main.Pool;
import main.Tile;
import main.Frame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest extends Board
{

    Board board = new Board();

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
        Pool.set();

        Frame frame = new Frame();

        frame.createTestableFrame();

        System.out.println(frame);

        System.out.println(placeWord(3, 7, "HELLO", frame, true));

        //assertTrue(placeWord(3, 7, "HELLO", frame, true));
        //assertTrue(placeWord(7, 6, "NOT", frame, false));


        assertEquals(new Tile('H', 4), board.squares[3][7].getTile());
        assertEquals(new Tile('E', 1), board.squares[4][7].getTile());
        assertEquals(new Tile('L', 1), board.squares[5][7].getTile());
        assertEquals(new Tile('L', 1), board.squares[6][7].getTile());
        assertEquals(new Tile('O', 1), board.squares[7][7].getTile());

        assertEquals(new Tile('N', 1), board.squares[7][6].getTile());
        assertEquals(new Tile('T', 1), board.squares[7][8].getTile());
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

    }

    @Test
    void testHasTiles()
    {
    }

    @Test
    void testToString()
    {
    }

}