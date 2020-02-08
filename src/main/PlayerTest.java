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
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        //Tests the creation and display of a players frame.
        System.out.println("Player 1's frame: " + p1.getFrame());
        System.out.println("Player 2's frame: " + p2.getFrame());
        System.out.println();

        //Tests the refilling function of a players frame
        p1.getFrame().refill();
        System.out.println("Player 1's frame after being refilled: " + p1.getFrame());
        System.out.println();

        //Testing the hasString method of a players frame
        System.out.println("The word 'as' is in player 2's frame (true or false): " + p2.getFrame().hasString("as"));
        //Adds a fake 'A' and 'S' tile to player 2's frame for testing purposes.
        p2.getFrame().getTiles().add(new Tile('A', 1));
        p2.getFrame().getTiles().add(new Tile('S', 1));
        System.out.println("Adding a fake 'A' and 'S' to player 2's frame we get: " + p2.getFrame());
        //Testing the hasString method of a players frame
        System.out.println("The word 'as' is in player 2's frame (true or false): " + p2.getFrame().hasString("as"));
        System.out.println();

        //Testing the isEmpty method of a player frame
        System.out.println("Player 1's frames isEmpty function returns: " + p1.getFrame().isEmpty());
        System.out.println();
    }

}
