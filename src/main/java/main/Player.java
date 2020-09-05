package main;


public class Player implements Cloneable
{
	private String playerName;
	private int playerScore;
	private Frame frame;
	
	public Player(String playerName, Pool pool) //Constructor
	{
		this.playerName = playerName;
		frame = new Frame(pool);
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

	public void setPlayerName(String name)
	{
		this.playerName = name;
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
	protected Player clone() throws CloneNotSupportedException {
		try {
			return (Player) super.clone();
		} catch (CloneNotSupportedException e) {
			Player clone = new Player(this.playerName, new Pool());
			clone.playerScore = this.playerScore;
			clone.frame = this.frame.clone();
			return clone;
		}
	}

	@Override
	public String toString() //Prints string PlayerName
	{
		return "Player: " + playerName + " Score: " + playerScore;
	}

}
