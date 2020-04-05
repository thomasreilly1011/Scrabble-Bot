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
    //IO Objects
    static final IO.CLI cli = new CLI();
    static final IO.UI ui = new UI();

    //Game Objects
    public static Pool pool;
    public static Board board;
    public static Dictionary dictionary;

    //Move Type Constants:
    public static final int PLACE_WORD = 0;
    public static final int PASS = 1;
    public static final int REFILL = 2;
    public static final int QUIT = 3;
    public static final int CHALLENGE = 4;
    public static final int NAME = 5;

    public static boolean gameOver = false;

    //Holds the word placed in the last PLACE_WORD move. (For use in CHALLENGE move)
    private static String wordBuffer;

    /*
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
        Player player1 = cli.playerInit();
        Player player2 = cli.playerInit();
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

    public static void move(String[] commandArgs, Player player)
    {
        if(parseInt(commandArgs[0]) == PLACE_WORD)
        {
            int i = board.placeWord(parseInt(commandArgs[2]), parseInt(commandArgs[3]), commandArgs[1], player.getFrame(), parseBoolean(commandArgs[4]));
            if(i != Board.SUCCESS)
            {
                cli.error(i);
                move(cli.playerMove(player), player);
            } else {
                wordBuffer = commandArgs[1];
                player.incScore(board.score);
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
        else if (parseInt(commandArgs[0]) == CHALLENGE)
        {
            boolean challenge = dictionary.challenge(wordBuffer);
            if (challenge) {
                System.out.println("That is a valid word!");
            } else {
                System.out.println("Thats an invalid word!");
            }
        }
        else if (parseInt(commandArgs[0]) == NAME)
        {
            player.setPlayerName(commandArgs[1]);
        }
    }
}