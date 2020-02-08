package main;

public class PlayerTest {

    public static void main(String[] args) {
        Pool.set();

        testPlayerClass();
        testPlayerFrame();
    }

    public static void testPlayerClass() {

    }

    public static
    void testPlayerFrame() {
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
    }

}
