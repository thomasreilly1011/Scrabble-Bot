# Scrabble Game &  Bot
![Image of Scrabble Board](./scrabble-project.jpg)

My Software Engineering Project (COMP20050) completed as part of my Stage 2 studies in UCD. 
A Scrabble game with JavaFX UI and programmed bot that you can play against.
The Game features a fully functional Scrabble Board that you and another player can play on. The game automatically 
allocates tiles to your frames and has a built in feature for challenging your opponents word placements.

## The Bot
The master branch currently only holds the Scrabble Game. To see the bot and read more about it, switch to the bot branch.

## Getting Started & Pre-requisites
### Running The Game Launcher
The easiest way to start running the game is to: 
* Download the ```scrabble-launcher.zip``` located in the root of this repository.
* Unzip the package and navigate to the ```bin``` directory.
* Launch the application from the terminal using ```./launcher.sh```  
* No JRE is required to run the game using the launcher.

### Playing The Game
To get started the two players must enter their names. 

Once entered, the scrabble board window will appear, both players will be allocated scrabble tiles to their frames and Player 1 will be asked to make a move.

Moves are made by typing commands into the command-line.
```
                            ---------- INSTRUCTIONS FOR PLAYING ----------  
                                            
To place a word, type 'PLACE' followed by the following (space separated) options:
    1) The desired word using letters from your frame.
        If you wish to use a blank tile, type an underscore followed by your desired letter.
    2) Your desired row coordinate starting from the bottom-up (1-15)
    3) Your desired column coordinate starting from left-right (1-15)
    4) Whether you would like the word to be placed 'V' for vertically OR 'H' for horizontally
        For Example: To place the word 'hello' on the middle tile horizontally with e replaced by a blank tile,your command should be:
        PLACE H_ELLO 8 8 H
To skip your go, type 'PASS'
To refill your board, type 'REFILL'
To forfeit your game, type 'QUIT'
To challenge a the previous players word placement, type 'CHALLENGE'.
To change your name, type 'NAME' followed by the desired name.
```

### Running The Source Code
If you wish to play around with the source code and re-run the application the easiest way to do so is by cloning the repository with your favourite IDE. 
The included `pom.xml` can be used to install all javafx and junit dependencies with Maven. JDK 13 or above is required to compile the project.

## Authors
Thomas Reilly (thomas.reilly@ucdconnect.ie)

Sean Lacey (sean.lacey@ucdconnect.ie)

Daniel Portela Byrne (daniel.portelabyrne@ucdconnect.ie)