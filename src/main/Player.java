package scrabble;


public class Player 
{
	
	//	A class called Player that:
	//	o Allows the player data to be reset ~
	//	o Allows the name of the player to be set ~
	//	o Allows a player’s score to be increased ~
	//	o Allows access to their score ~
	//	o Allows access to a player’s frame (tiles) ~
	//	o Allows display of a players name 
	
	String playerName;
	int playerScore;
	Frame frame;
	
	public Player(String playerName) //Constructor
	{
		this.playerName = playerName;
		frame = new Frame(this);
		playerScore = 0;
	}
	
	public void incScore(int amount) //method used to increment score
	{
		this.playerScore += amount;
	}
	
	public String getPlayerName() //PlayerName getter
	{
		return this.playerName;
	}
	
	public int getPlayerScore() //PlayerScore getter
	{
		return this.playerScore;
	}
	
	public void resetData() //Method to reset data
	{
		playerScore = 0;
		playerName = null;
	}
	
	public Frame getFrame() //Returns frame
	{
		return this.frame;
	}
	
	public String toString() //Prints string PlayerName
	{
		return "Player: " + playerName + " Score: " + playerScore;
	}

}
