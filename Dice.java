/**
 * 
 * @author anhph
 *
 */
public class Dice {
	// Private variables
	private int numSides;
	private int numDice;
	private Die[] rollArr;
	/**
	 * Creates an empty set of dice
	 */
	public Dice() {
		this.numDice = 0;
		this.numSides = 0;
		rollArr = null;
	}
	
	/**
	 * Creates a set of dice with params
	 * 
	 */
	public Dice(int numDice, int numSides) {
		this.numDice = numDice;
		this.numSides = numSides;
		this.rollArr = new Die[numDice]; // array to hold all the dice
		
		// Generate the dice array 
		for (int i = 0; i < numDice; i++) {
			rollArr[i] = new Die(numSides);
		}
	}
	
	/**
	 * Roll all dice
	 * @return
	 */
	public int roll() {
		int sum = 0;
		for (int i = 0; i < numDice; i++) {
			sum += rollDie(i);
		}
		
		return sum;
	}
	
	
	/**
	 * Roll one die
	 * @param numDie
	 * @return
	 */
	public int rollDie(int numDie) {
		return rollArr[numDie].roll();
	}
	
	/**
	 * Get the die's value.
	 * @param numDie
	 * @return
	 */
	public int dieValue(int numDie) {
		return rollArr[numDie].getVal();
	}
	
	/**
	 * Get total dice values
	 * @return sum of dice values
	 */
	public int totalDiceValue() {
		int sum = 0;
		for (int i = 0; i < numDice; i++) {
			sum += dieValue(i);
		}
		return sum;
	}

	/**
	 * @return the numDice
	 */
	public int getNumDice() {
		return numDice;
	}

	/**
	 * @param numDice the numDice to set
	 */
	public void setNumDice(int numDice) {
		this.numDice = numDice;
	}

	/**
	 * @return the numSides
	 */
	public int getNumSides() {
		return numSides;
	}

	/**
	 * @param numSides the numSides to set
	 */
	public void setNumSides(int numSides) {
		this.numSides = numSides;
	}
}
