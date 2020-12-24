package guessme;

/**
 * A LinkedList-based implementation of the Guess-A-Number game
 */
public class LinkedListGame {

	// TODO: declare data members as necessary
	public int guess = 1000;
	public int numberGuesses;
	public boolean gameOver;
	public LLIntegerNode head;
	public LLIntegerNode priorHead;
	public LLIntegerNode priorLast;
	public String priorGuesses;
	
	/********************************************************
	 * NOTE: for this project you must use linked lists
	 * implemented by yourself. You are NOT ALLOWED to use
	 * Java arrays of any type, or any class in the java.util
	 * package (such as ArrayList).
	 *******************************************************/	 
	
	/********************************************************
	 * NOTE: you are allowed to add new methods if necessary,
	 * but DO NOT remove any provided method, and do NOT add
	 * new files (as they will be ignored by the autograder).
	 *******************************************************/
	
	// LinkedListGame constructor method
	public LinkedListGame() {
		reset();
	}
	
	// Resets data members and game state so we can play again
	public void reset() {
		guess = 1000;
		priorGuesses = "";
		numberGuesses = 0;
		priorHead = null;
		priorLast = null;
		LLIntegerNode tail = null;
		for(int i=1000;i<10000;i++) {
			LLIntegerNode node = new LLIntegerNode(i);
			if(i==1000) {
				head = node;
				tail = node;
			}
			tail.setLink(node);
			tail = node;
		}
	}
	
	// Returns true if n is a prior guess; false otherwise.
	public boolean isPriorGuess(int n) {
		LLIntegerNode node = priorHead;
		
		while(node != null) {
			if(node.getInfo() == n) {
				return true;
			}	
			node = node.getLink();
		}
		
		return false;
	}
	
	// Returns the number of guesses so far.
	public int numGuesses() {
		return numberGuesses;
	}
	
	/**
	 * Returns the number of matches between integers a and b.
	 * You can assume that both are 4-digits long (i.e. between 1000 and 9999).
	 * The return value must be between 0 and 4.
	 * 
	 * A match is the same digit at the same location. For example:
	 *   1234 and 4321 have 0 match;
	 *   1234 and 1114 have 2 matches (1 and 4);
	 *   1000 and 9000 have 3 matches (three 0's).
	 */
	public static int numMatches(int a, int b) {
		int matches = 0;
		if((a % 10) == (b % 10)) {
			matches += 1;
		}
		if(((a % 100)/10) == ((b % 100)/10)) {
			matches += 1;
		}
		if(((a % 1000)/100) == ((b % 1000)/100)) {
			matches += 1;
		}
		if((a/1000) == (b/1000)){
			matches += 1;
		}
		return matches;
	}
	
	/**
	 * Returns true if the game is over; false otherwise.
	 * The game is over if the number has been correctly guessed
	 * or if no candidate is left.
	 */
	public boolean isOver() {
		return gameOver;
	}
	
	/**
	 * Returns the guess number and adds it to the list of prior guesses.
	 * The insertion should occur at the end of the prior guesses list,
	 * so that the order of the nodes follow the order of prior guesses.
	 */	
	public int getGuess() {
		LLIntegerNode prior = new LLIntegerNode(guess);
		if(numGuesses() == 0) {
			priorHead = prior;
			priorLast = prior;
			priorLast.setLink(null);
		}else{
			priorLast.setLink(prior);
			priorLast = prior;
			priorLast.setLink(null);
		}
		numberGuesses++;
		return guess;
	}
	
	/**
	 * Updates guess based on the number of matches of the previous guess.
	 * If nmatches is 4, the previous guess is correct and the game is over.
	 * Check project description for implementation details.
	 * 
	 * Returns true if the update has no error; false if no candidate 
	 * is left (indicating a state of error);
	 */
	public boolean updateGuess(int nmatches) {
		// TODO
		LLIntegerNode node = head;
		LLIntegerNode last = new LLIntegerNode(0);
		boolean listEmpty = true;
		
		if(nmatches == 4) {
			gameOver = true;
			return true;
		}
		
		while(node != null) {
			if(numMatches(node.getInfo(),guess) != nmatches) {
				node = node.getLink();
			}else {
				LLIntegerNode validNode = new LLIntegerNode(node.getInfo());
				listEmpty = false;
				if(last.getInfo() == 0) {
					head =validNode;
					last = validNode;
				}else{
					last.setLink(validNode);
					last = validNode;
					
				}
				node = node.getLink();
			}
		}
		
		if(listEmpty) {
			return false;
		}else{
		guess = head.getInfo();
		return true;
		}
	}
	
	// Returns the head of the prior guesses list.
	// Returns null if there hasn't been any prior guess
	public LLIntegerNode priorGuesses() {
		return priorHead;
	}
	
	/**
	 * Returns the list of prior guesses as a String. For example,
	 * if the prior guesses are 1000, 2111, 3222, in that order,
	 * the returned string should be "1000, 2111, 3222", in the same order,
	 * with every two numbers separated by a comma and space, except the
	 * last number (which should not be followed by either comma or space).
	 *
	 * Returns an empty string if here hasn't been any prior guess
	 */
	public String priorGuessesString() {
		LLIntegerNode node = priorHead;
		String str = "";
		
		if(node != null) {
			if(node.getLink() != null) {
				while(node.getLink() != null) {
					str += node.getInfo() + ", ";
					node = node.getLink();
				}
				str += node.getInfo();
			}else {
				str += node.getInfo();
			}
		}
		
		return str;
	}
	
}
