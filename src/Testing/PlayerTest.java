package Testing;

import main.Player;
import main.Pool;

public class PlayerTest {
    public static void main(String[] args) {
        Pool.set();

        System.out.println("-------------Player Class Tests-------------");
        testPlayerClass();
        System.out.println("-------------Player Frame Class Tests-------------");
        testPlayerFrame();
    }

    private static void testPlayerClass()
    {
        //Testing the construction of a player's name
        Player p1 = new Player("Daniel Byrne");
        Player p2 = new Player("Thomas Reilly");
        System.out.println("Player 1's name: " + p1.getPlayerName());
        System.out.println("Player 2's name: " + p2.getPlayerName());
        System.out.println();

        //Attempts to create player with invalid characters.
        try
        {
            Player p3 = new Player("Sean Lacey!");
        } catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
        try
        {
            Player p4 = new Player("John Keegan@@@");
        } catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
        try
        {
            Player p5 = new Player("Ciarán Cuddihy");
        } catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
        System.out.println();

        //Tests the increment function of a player's score
        System.out.println(p1.getPlayerName() + "'s score before being incremented: " + p1.getPlayerScore());
        p1.incScore(10);
        System.out.println(p1.getPlayerName() + "'s score after being incremented: " + p1.getPlayerScore());
        System.out.println();

        //Tests for a negative amount inputted
        try
        {
            p1.incScore(-10);
        } catch (IllegalArgumentException e)
        {
            System.out.println(e);
        }
        System.out.println();

        //Tests the reset data function of a player's data
        System.out.println("Player 1's data before being reset: ");
        System.out.println("Player name: " + p1.getPlayerScore() + "    Player score: " + p1.getPlayerName());
        p1.resetData();
        System.out.println("Player 1's data after being reset: ");
        System.out.println("Player name: " + p1.getPlayerScore() + "    Player score: " + p1.getPlayerName());
        System.out.println();

    }

    private static void testPlayerFrame() {
        //Tests the creation and display of a players frame.
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        System.out.println("Player 1's frame: " + p1.getFrame());
        System.out.println("Player 2's frame: " + p2.getFrame());
        System.out.println();

        //Tests the refilling function of a players frame
        p1.getFrame().refill();
        System.out.println("Player 1's frame after being refilled: " + p1.getFrame());
        System.out.println();

        //Testing the hasString method of a players frame
        System.out.println("The word 'as' is in player 2's frame (true or false): " + p2.getFrame().hasString("as"));
        System.out.println("The letter 'a' is in player 2's frame (true or false): " + p2.getFrame().hasString("a"));
        System.out.println("The letter 's' is in player 2's frame (true or false): " + p2.getFrame().hasString("s"));
        System.out.println();

        //Testing the isEmpty method of a players frame
        System.out.println("Player 1's frames isEmpty function returns: " + p1.getFrame().isEmpty());
        System.out.println();

        //Testing the getTile method of a players frame
        System.out.println("Player 1's frame: " + p1.getFrame());
        System.out.println("Getting player 1's tile at index number 3 returns: " + p1.getFrame().getTile(3));
        System.out.println("Getting player 1's tile at index number 6 returns: " + p1.getFrame().getTile(6));
        System.out.println("Getting player 1's tile at index number 0 returns: " + p1.getFrame().getTile(0));
        System.out.println();

        //Testing the removeTile method of a player frame
        System.out.println("Player 1's frame: " + p1.getFrame());
        p1.getFrame().removeTile(p1.getFrame().getTile(0).getLetter());
        System.out.println("The tile at index 0 has been removed and replaced. Player 1's frame now looks like: " + p1.getFrame());
        System.out.println("Attempting to remove the letter a from player 1's frame returns: " + p1.getFrame().removeTile('A'));
        System.out.println();
        //Attempts to call Frame methods with invalid arguments.
        try {
            p1.getFrame().removeTile('3');
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        try {
            p1.getFrame().hasString("@ucd.ie");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        try {
            p1.getFrame().hasString("12345");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }

    }

}
