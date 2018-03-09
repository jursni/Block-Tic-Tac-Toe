/*
 * This class represents a record containing the configuration, score, and level of a certain board.
 * The configuration is a string concatenating all characters on the board in columns downwards, from left to right.
 * The score is the score given to a certain configuration, representing the outcome of that configuration.
 * The level is the number of levels, or depth of the game tree that the algorithm will consider. 
 */

public class TTTRecord {

	private String config; 		//configuration
	private int score; 			//score
	private int level; 			//level

	public TTTRecord(String config, int score, int level) { 		//constructor which returns a new TTTRecord with the specified configuration, score, and level
		this.config = config; 
		this.score = score;
		this.level = level;
	}

	public String getConfiguration() { 		//method that returns the configuration stored in the TTTRecord
		return this.config;
	}

	public int getScore() { 					//method that returns the score stored in the TTTRecord
		return this.score;
	}

	public int getLevel() { 					//method that returns the level stored in the TTTRecord
		return this.level;
	}

}