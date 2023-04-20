import java.util.Random;

/**
 * Die Class
 * @author Jenn Pham
 *
 */
public class Die {
	private int sides; // number of sides of a die
	private int val; // value of the die being rolled
	
	/**
	 * Constructor
	 */
	public Die() {
		this.sides = 0;
		this.val = 0;
	}
	
	public Die(int sides) {
		this.sides = sides;
		this.val = 0;
	}
	
	public int roll() {
		Random rand = new Random();
		val = rand.nextInt(sides) + 1;
		return val;
	}

	/**
	 * @return the sides
	 */
	public int getSides() {
		return sides;
	}

	/**
	 * @param sides the sides to set
	 */
	public void setSides(int sides) {
		this.sides = sides;
	}

	/**
	 * @return the val
	 */
	public int getVal() {
		return val;
	}
}
