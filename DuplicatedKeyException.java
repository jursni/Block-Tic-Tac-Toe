/*
* This is a class implementing the classes of exceptions thrown by the put method of TTTDictionary:
* when the put method tries to put a certain record into the dictionary but the corresponding configuration already exists in the dictionary.
*  
 */

public class DuplicatedKeyException extends Exception {
 
	public DuplicatedKeyException() { //constructor
	}
}