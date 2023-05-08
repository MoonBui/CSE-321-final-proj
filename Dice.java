/**
 * Dice Class
 */
public class Dice {
	public int val;
	
	/**
	 * Constructor for Dice
	 */
	public Dice() {
		val = (int) (Math.random() * 6) + 1;
	}
	
	/**
	 * Get Dice value
	 * @return int a Dice value
	 */
	public int getVal() {
		return val;
	}
}
