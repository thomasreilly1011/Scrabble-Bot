package main;

import IO.CLI;
import IO.UI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Integer.parseInt;
import static java.lang.Boolean.parseBoolean;

public class Scrabble extends Application
{
    //Move Type Constants:
    public static final int PLACE_WORD = 0;
    public static final int PASS = 1;
    public static final int REFILL = 2;
    public static final int QUIT = 3;
    public static final int CHALLENGE = 4;
    public static final int NAME = 5;

    //IO Objects
    static final IO.CLI cli = new CLI();
    static final IO.UI ui = new UI();

    //Game Objects
    public static Pool pool;
    public static Board board;
    public static Player player1;
    public static Player player2;
    public static Dictionary dictionary;

    //Backup of game objects from previous move (for use in revertGame).
    private static Pool poolBuffer;
    private static Board boardBuffer;
    private static Player player1Buffer;
    private static Player player2Buffer;

    //Backup of previous word played (for use in Dictionary.challenge() call)
    public static String wordBuffer;

    public static boolean gameOver = false;
    public static boolean allowChallenge = false;
    /**
    Main function (Launches the JavaFX application calling start())
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //Initialise all game objects
        pool = new Pool();
        board = new Board();
        dictionary = new Dictionary("src/Files/sowpods.txt");
        player1 = new Player(cli.playerInit(), pool);
        player2 = new Player(cli.playerInit(), pool);
        cli.help();

        //Start up the UI
        Scene scrabbleBoard = new Scene(ui.createContent(board));
        stage.setScene(scrabbleBoard);
        stage.setTitle("Scrabble Board");
        stage.show();

        //Start up the CLI on a background Thread.
        Thread cliThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                gameLoop(board, player1, player2);
            }
        });
        cliThread.setDaemon(true);
        cliThread.start();

        //Update the UI every second
        //TODO Maybe make this more efficient? Implement a wait() on the cliThread?
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                scrabbleBoard.setRoot(ui.createContent(board));
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * Handles the running of the game loop. Calls a move for given player1 and player2 turn by turn until the game is over.
     * @param board
     * @param player1
     * @param player2
     */
    public void gameLoop(Board board, Player player1, Player player2)
    {
        while (!Scrabble.gameOver)
        {
            Scrabble.move(cli.playerMove(player1), player1);
            System.out.println();

            Scrabble.move(cli.playerMove(player2), player2);
            System.out.println();
        }
    }

    /**
     * Handles a range of moves that corresponding to move type constants declared above.
     * @param commandArgs arguments from the command line parsed as follows:
     *     commandArgs[0] = The Command Type Constant (an int)
     *
     *     If the command is of type PLACE_WORD then,
     *     commandArgs[1] = desired word
     *     commandArgs[2] = internal row coordinate
     *     commandArgs[3] = internal column coordinate
     *     commandArgs[4] = vertical boolean
     *
     *     If the command is of type NAME then,
     *     commandArgs[1] = the desired name
     *
     * @param player The player who is making the move.
     */
    public static void move(String[] commandArgs, Player player)
    {
        if (parseInt(commandArgs[0]) == CHALLENGE) {
            boolean positive = dictionary.challenge(wordBuffer);
            if (positive)
            {
                cli.announceValid(player);

                allowChallenge = false;
                return;
            } else {
                cli.announceInvalid(player);

                revertGame();
                allowChallenge = false;
                move(cli.playerMove(player), player);
            }
        } else {
            updateBuffers();
        }
        if(parseInt(commandArgs[0]) == PLACE_WORD)
        {
            int errorNumber = board.placeWord(parseInt(commandArgs[2]), parseInt(commandArgs[3]), commandArgs[1], player.getFrame(), parseBoolean(commandArgs[4]));
            if(errorNumber != Board.SUCCESS)
            {
                cli.error(errorNumber);
                move(cli.playerMove(player), player);
            } else {
                wordBuffer = commandArgs[1];
                player.incScore(board.score);
                allowChallenge = true;
                cli.announceScore(player, board.score);
            }
        }
        else if(parseInt(commandArgs[0]) == REFILL)
        {
            player.getFrame().refill();
        }
        else if(parseInt(commandArgs[0]) == QUIT)
        {
            gameOver = cli.endGame();
            if (!gameOver)
            {
                move(cli.playerMove(player), player);
            }
        }
        else if (parseInt(commandArgs[0]) == NAME)
        {
            player.setPlayerName(commandArgs[1]);
            move(cli.playerMove(player), player);
        }
    }

    /**
     * FUnction that updates the buffers of the game with the latest game data from Game objects.
     */
    public static void updateBuffers() {
        try {
            poolBuffer = pool.clone();
            boardBuffer = board.clone();
            System.out.println("Board Buffer:\n" + boardBuffer);
            player1Buffer = player1.clone();
            System.out.println("player1 Buffer's Frame: " + player1Buffer.getFrame());
            player2Buffer = player2.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
    }

    /**
     * Function that reverts the game to its previous state by storing a backup of all key Game variables.
     */
    public static void revertGame() {
        try {
            pool = poolBuffer.clone();
            board = boardBuffer.clone();
            player1 = player1Buffer.clone();
            player2 = player2Buffer.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
    }


}