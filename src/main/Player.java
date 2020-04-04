package main;


public class Player 
{
	private String playerName;
	private int playerScore;
	private Frame frame;
	
	public Player(String playerName) //Constructor
	{
		this.playerName = playerName;
		frame = new Frame();
		playerScore = 0;

		//Error handling for illegal characters
		for(int i = 0; i < playerName.length(); i++)
		{
			if(!Character.isDigit(playerName.charAt(i)) && !Character.isLetter(playerName.charAt(i)) && !Character.isWhitespace(playerName.charAt(i)))
			{
				throw ( new IllegalArgumentException("Illegal Character in String"));
			}
		}//end for loop
	}
	
	public void incScore(int amount) //method used to increment score
	{
		this.playerScore += amount;

		//Error handling for an incorrect amount value
		if(amount < 0)
		{
			throw ( new IllegalArgumentException("Score cannot be incremented by a negative value"));
		}
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

	@Override
	public String toString() //Prints string PlayerName
	{
		return "Player: " + playerName + " Score: " + playerScore;
	}

}
