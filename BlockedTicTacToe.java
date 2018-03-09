/*
 * This class implements the methods needed by algorithm computerPlay. 
 *  
 */

public class BlockedTicTacToe { 
	
	private char[][] gameBoard; 			//two-dimensional array used to store character symbols at given columns and rows
	private int board_size; 				//length of the board (same length vertically and horizontally)
	private int inline; 					//number of symbols in a row needed for a win
	private int max_levels; 				//maximum level of the game tree that will be explored by the program

	
	
	public BlockedTicTacToe (int board_size, int inline, int max_levels) { 	//constructor that creates a game board (2D array filled with spaces) with dimensions given board_size x given board_size, given number of symbols needed for a win (inline), and given maximum levels of game tree to be explored (max_levels)
		gameBoard = new char[board_size][board_size]; 						//create game board, two-dimensional array to store character symbols at given columns and rows
		this.board_size = board_size; 										//size of the board is the specified size
		this.inline = inline; 												//the number of symbols in a row needed for a win is the given inline
		this.max_levels = max_levels; 										//the depth of the game tree to be explored is the given max_levels
		int rows = 0; 														//start with the first row
		int columns = 0; 													//start with the first column
		while (rows < board_size) { 											//while rows being filled are within bounds of the board
			columns = 0; 													//start with the first column (for iterations of while loop with different rows)
			while (columns < board_size) { 									//while columns being filled are within bounds of the board
				gameBoard[rows][columns] = ' '; 								//fill the current square at the current row and current column with a space (representing empty)
				columns += 1; 												//to iterate through the rest of the columns
			}
			rows += 1; 														//to iterate through the rest of the rows
		}
	}
	
	
	
	public TTTDictionary createDictionary() { 							//returns an empty TTTDictionary of size 4999
		TTTDictionary emptytttdictionary = new TTTDictionary(4999); 		//4999 is chosen for length of hash table because it is the largest prime between 4000 and 5000
		return emptytttdictionary;
	}
	

	
	public int repeatedConfig(TTTDictionary configurations) { 	//checks whether the configuration of the current game board is in the given dictionary "configurations" -- returns its associated score if found and -1 if not
		String configstring = ""; 								//initialize the configuration string of the current game board so that we can represent content of current gameBoard as a string
		int column = 0; 											//start with the first column
		while (column < board_size) { 							//while columns, from which symbols are used to add to the configuration string, are within bounds of the board
			int row = 0; 										//start with the first row
			while (row < board_size) { 							//while rows, from which symbols are used to add to the configuration string, are within bounds of the board
				configstring += gameBoard[row][column]; 			//add the character symbol in the current square at the current row and current column to the configuration string of the current game board
				row += 1; 										//to iterate through the rest of the rows
			}
			column +=1; 											//to iterate through the rest of the columns
		}
		if (configurations.get(configstring) == null) { 			//if a record associated with the configuration string of the current game board cannot be found in the configurations dictionary...
			return -1; 											//return -1
		}
		else { 													//otherwise...
			return configurations.get(configstring).getScore(); 	//return the score of the record, associated with the configuration string of the current game board, in the configurations dictionary
		}
	}
	
	
	
	public void insertConfig(TTTDictionary configurations, int score, int level) { 		//inserts the configuration of the current game board, current score, and current level into the given dictionary "configurations"
		String configstring = ""; 														//representing content of current gameBoard as a string, like in the first half of the repeatedConfig method
		int column = 0; 																	//same as above
		while (column < board_size) { 													//same as above
			int row = 0; 																//same as above
			while (row < board_size) { 													//same as above
				configstring += gameBoard[row][column]; 									//same as above
				row += 1;																//same as above
			} 																			//same as above
			column +=1; 																	//same as above
		}
		TTTRecord recordtobeinserted = new TTTRecord(configstring, score, level); 		//create a new TTTRecord containing information about the current game board (configuration, score, level)
		try{
			configurations.put(recordtobeinserted); 										//puts this new TTTRecord, containing information about the current game board, into the given dictionary "configurations"
		} catch (DuplicatedKeyException e) { 											//if error
		}
	}
	
	
	
	public void storePlay(int row, int col, char symbol) {		//stores the given symbol character in the game board at given position [row][column]
		gameBoard[row][col] = symbol;							//stores the given symbol in the game board at the given position
	}
	
	
	
	public boolean squareIsEmpty (int row, int col) {			//returns true if a position on a given spot of the current game board is empty
		if (gameBoard[row][col] == ' '){							//if the square at column "col" and row "row" of the current game board is empty (a space)...
				return true;										//then the current square is empty,so return true
		}
		else {													//otherwise...
			return false;										//return false
		}
	}
	
	
	
	public boolean wins (char symbol) {																										//returns true if the player using the given character "symbol" wins, in other words if there are enough consecutive instances of the symbol in a line (horizontal, vertical, or diagonal lines of length inline) in the current gameboard -- returns false otherwise
		boolean winstatus = false;																											//default status is false (no win)
		boolean horizontalwin = false;																										//default status is false (no win)
		boolean verticalwin = false;																											//default status is false (no win)
		boolean diagonalDRwin = false;																										//default status is false (no win)
		boolean diagonalDLwin = false;																										//default status is false (no win)
		if (winstatus == false) {																											//if no win has been established...
			for (int row = 0 ; row<board_size ; row ++ ) {																					//for all rows of the current game board...
				if (winstatus ==false) {																										//if no win has been established...
					for (int column = 0 ; column<board_size ; column ++ ){																		//for all columns of the current game board...
						char symbolatspace = gameBoard[row][column];																			//variable to refer to the symbol currently at this current square (this column and row) of the game board
						if (symbolatspace == symbol) {																						//if the symbol at this current square is the same as the given symbol...
							
							int horizontalcounter=0;																							//horizontalcounter counts the number of squares to the right of the initial square (whatever is at [row][column]) matching the symbol 
							while (((column+horizontalcounter) < board_size) && (horizontalcounter < inline)) { 								//while the square to be assessed is within bounds of the game board and the number of consecutive matching symbols isn't already sufficiently long for a "win"...
								if (gameBoard[row][column+horizontalcounter] == symbol) {														//if the square being assessed contains the same symbol as the given symbol...
									horizontalcounter +=1;																					//move on to the next square on the right (to be assessed in the next iteration of the while loop)
								}																											
								else {																										//if the square being assessed doesn't contain the samesymbol as the given symbol...
									break;																									//break the while loop
								}
							}
							if (horizontalcounter == inline) {																				//if there have been enough number of matched symbols in succession for a win, horizontalcounter should equal inline. if so...
								horizontalwin = true;																						//there has been a win due to a completed horizontal line
							}
							else {																											//if there have not been enough matched symbols in succession...
								horizontalwin = false;																						//there has not been a win due to a completed horizontal line
							}
							
							
							int verticalcounter=0;																							//same as checking for horizontal wins, but checking for squares extending below the initial square
							int newrow=0;
							while ((row+newrow) < board_size) { 
								if (gameBoard[row+newrow][column] == symbol) {
									newrow +=1;
									verticalcounter +=1;
								}
								else {
									break;
								}
							}
							if (verticalcounter == inline) {
								verticalwin = true;
							}
							else {
								verticalwin = false;
							}
							
							
							int diagonalDRcounter=0;																							//same as checking for horizontal wins, but checking for squares extending below and to the right of the initial square
							int newcolumn=0;
							newrow=0;
							while (((row+newrow)<board_size)&&((column+newcolumn)<board_size)) {
								if (gameBoard[row+newrow][column+newcolumn] == symbol) {
									newrow +=1;
									newcolumn +=1;
									diagonalDRcounter +=1;
								}
								else {
									break;
								}
							}
							if (diagonalDRcounter == inline) {
								diagonalDRwin = true;
							}
							else {
								diagonalDRwin = false;
							}
							
							
							int diagonalDLcounter=0;																							//same as checking for horizontal wins, but checking for squares extending below and to the left of the initial square
							newcolumn=0;
							newrow=0;
							while (((row+newrow)<board_size)&&((column+newcolumn)>=0)) {
								if (gameBoard[row+newrow][column+newcolumn] == symbol) {
									newrow +=1;
									newcolumn -=1;
									diagonalDLcounter +=1;
								}
								else {
									break;
								}
							}
							if (diagonalDLcounter == inline) {
								diagonalDLwin = true;
							}
							else {
								diagonalDLwin = false;
							}
						}
						if ((verticalwin == true) || (horizontalwin == true) || (diagonalDLwin == true) || (diagonalDRwin == true)) {			//if there has been a win in any direction (horizontal, vertical, diagonal)...
							winstatus = true;																								//then there has been a win
						}
					}
				}
			}
		}
		return winstatus;																													//return the status of the win
	}
	
	
	
	public boolean isDraw() { 								//returns true if the board has no more empty positions left and no player has won the game -- false if a player has won and/or there are still empty positions left
		boolean draw = false; 								//set the default to "no draw"
		if ((wins('x') || wins('o')) == true){ 				//if either the computer or human wins (o or x, respectively) then...
			draw = false; 									//it is not a draw
		}
		if ((wins('x') || wins('o')) == false){ 				//if neither the computer nor the human win then...
			draw = true; 									//there is possibly a "draw" -- this will be verified later and changed to false only if there isn't actually a draw
			int row = 0; 									//starting with first row
			while (row < board_size) { 						//while rows being checked are within bounds of the board
				int column = 0; 								//starting with the first column
				while (column < board_size) { 				//while columns being checked are within bounds of the board
					if (gameBoard[row][column] == ' ') { 		//if the current square at the current column and current row is empty (just a space)...
						draw = false; 						//there actually is no draw
					}
					column += 1; 							//to iterate through the rest of the columns
				}
				row += 1; 									//to iterate through the rest of the rows
			}
		}
		return draw;
	}
	
	
	
	public int evalBoard() { 			//returns 0, 1, 2, or 3 if the human has won, there is a draw, the game is still undecided, or the computer has won (respectively)
		int currentscore = 2; 			//sets 2 as the default score
		if ((wins('o')) == true) { 		//if computers (who use "o" symbols) win, the score is 2
			currentscore = 3;
		}
		if ((wins('x')) == true) { 		//if humans (who use "x" symbols) win, the score is 2
			currentscore = 0;
		}
		if ((isDraw()) == true) { 		//if there is a draw, the score is 2
			currentscore = 1;
		}
		return currentscore;
	}
}