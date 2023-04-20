/**
 * Class Dice to implement a dice's behaviors.
 * 
 * @author Jenn Pham
 * @since 2023
 */
public class DiceSimulator {
	public static void main(String[] args) {
		Die die = new Die(6);
		System.out.println(die.roll());
		
		// 5 dice per turn.
		Dice dice = new Dice(5, 6);
		System.out.println("Total this round: " + dice.roll());
	}
}
