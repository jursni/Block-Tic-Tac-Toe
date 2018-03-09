/*
 * This class implements a dictionary using a hash table with separate chaining. 
 *  
 */

import java.util.LinkedList; //imports tools to create and work with linked lists



public class TTTDictionary implements TTTDictionaryADT { 
	private int size; 							//length of hash table
	private LinkedList<TTTRecord>[] hashtable; 			//array called hashtable that holds linked lists which hold TTTRecords
	private int numberofelements; 				//number of elements in the dictionary
	private int collisionstatus; 					//status of whether there is a collision occurred as a result of a put -- 0 if no collision, 1 if collision
	
	
	
	public TTTDictionary(int size){ 				//constructor method that creates an empty TTTDictionary of the specified size
		this.size = size; 						//length of the hash table is the specified size
		this.hashtable = new LinkedList[size]; 	//create hash table, array of length size containing containing linked lists
		this.numberofelements = 0;				//sets the number of elements as 0 to start with
	}
	
	
	
	private int hashfunction(String configstring){ 										//function that returns a hash key for a given configuration
		char[] characterarray = configstring.toCharArray(); 								//puts each character of the given configuration string to separate indexes of a new array
		int counter = 0; 
		int hashkey = 0;
		while (counter < characterarray.length){ 											//while we have not iterated through the entire array (configuration) yet
			hashkey = (hashkey * 37 + (int)characterarray[counter]) % this.size; 			//add the integer value of each character to the hash key using the hash function -- modulus of the hash table size is used with each iteration of the while loop because we want to avoid numbers that are too large for the system to work with AND because we want to have a final hash key that corresponds to an index in the hash table
			counter += 1;
		}			
		return hashkey; 																	//return the hash key
	}	

	
	
	public int put(TTTRecord record) throws DuplicatedKeyException { 				//puts a given TTTRecord into the dictionary
		
		int hashkey = hashfunction(record.getConfiguration()); 					//create a hash key for the configuration of the given TTTRecord
		if(this.hashtable[hashkey] == null) { 									//if the hash table is empty at the index corresponding to the hash key...
			this.hashtable[hashkey] = new LinkedList<TTTRecord>(); 				//at the index (corresponding to the hash key) of the hash table, create a new linked list (separate chain) that holds TTTRecords
			this.hashtable[hashkey].add(record); 									//add the given TTTRecord to the linked list (separate chain)
			numberofelements += 1; 												//there is now one more element in the dictionary
			collisionstatus = 0; 												//no collision occurred
		}
		else {																	//if the hash table is not empty at the index correspoding to the hash key...
			if (get(record.getConfiguration()) != null) { 						//if something comes up when we look for, in the dictionary, the configuration corresponding to this given TTTRecord...
				collisionstatus = 1; 											//collision occurred
				throw new DuplicatedKeyException(); 								//the TTTRecord corresponding to the given configuration is already in the dictionary, so throw to DuplicatedKeyException class
			}
			else { 																//otherwise, if something doesn't come up when we look for, in the dictionary, the configuration corresponding to this given TTTRecord...
				collisionstatus = 1; 											//collision occurred
				LinkedList<TTTRecord>separatechain = this.hashtable[hashkey]; 		//create a variable referring to the linked list (separate chain) at the index (corresponding to the hash key) of the hash table
				separatechain.add(record); 										//add the given TTTRecord to the linked list (separate chain)
				numberofelements += 1; 											//there is now one more element in the dictionary
				}
			}
		return collisionstatus; 													//return 0 if no collision, 1 if collision occurred
		}

	
	
	public void remove(String config) throws InexistentKeyException { 		//from the dictionary, removes the TTTRecord corresponding to the specified configuration
		TTTRecord recordtoberemoved = get(config); 						//creates a variable referring to the TTTRecord, from the dictionary, corresponding to the given configuration
		int hashkey = hashfunction(config); 								//creates a hash key for the given configuration
		if (recordtoberemoved != null) { 									//if the TTTRecord from the dictionary and corresponding to the given configuration exists...
			this.hashtable[hashkey].remove(recordtoberemoved); 			//remove the TTTRecord from the linked list (separate chain) at the hash key location of the hash table
		}
		else {
			throw new InexistentKeyException(); 							//if no TTTRecord corresponding to the given configuration is found, throw to InexistentKeyException class
		}
	}

	
	
	public TTTRecord get(String config) { 										//from the dictionary, returns the TTTRecord corresponding to the given configuration
		int hashkey = hashfunction(config); 										//creates a hash key for the given configuration
		if (this.hashtable[hashkey] != null) { 									//if the hash table is filled at the given hash key
			LinkedList<TTTRecord> separatechain = this.hashtable[hashkey]; 		//creates a variable referring to the linked list (separate chain) at the index (corresponding to the hash key) of the hash table
	        for (int i = 0; i < separatechain.size(); i++) { 						//for each TTTRecord in the linked list (separate chain)...
	            if (separatechain.get(i).getConfiguration().equals(config)) { 		//if the given configuration is the same as the configuration of the current TTTRecord///
					return separatechain.get(i); 									//return the current TTTRecord
	            }
	        }
	    }
		return null; 															//returns null if the hash table is empty at the given hash key or, in the linked list (separate chain), there is no record corresponding to the specified configuration 
	}

	
	
	public int numElements() { 		//returns the number of elements in a dictionary
		return numberofelements;
	}
}
