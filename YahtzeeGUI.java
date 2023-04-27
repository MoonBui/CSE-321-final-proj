/**
 * Yahtzee Console for GUI Implementation
 * Needs to be changed.
 * Printing to console for now.
 * 
 * @author Jenn Pham
 */
import java.util.Arrays;
import java.util.Scanner;

public class YahtzeeGUI {
	// Private variables
	private YahtzeeEngine game;
	private Scanner scanner;
	private String[] names = { "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three of a Kind", "Four of a Kind",
			"Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance" };

	/**
	 * Constructor for GUI
	 */
	public YahtzeeGUI() {
		game = new YahtzeeEngine();
		scanner = new Scanner(System.in);
	}

	public void play() {
		while (!game.isOver()) {
			playARound();
		}
		showScoreboard();
		System.out.println("Game over! Your total score is: " + game.getTotalScore());
		System.out.print("Play another game? (y/n): ");
		String playOption = scanner.next();
		scanner.nextLine();
		if (playOption.equalsIgnoreCase("y")) {
			game = new YahtzeeEngine();
			play();
		}
	}

	private void playARound() {
		game.resetDice();
		System.out.println("Round " + (game.getRound() + 1) + ":");
		boolean[] reroll = new boolean[5];
		for (int roll = 0; roll < 3; roll++) {
			game.rerollDice(reroll);
			showDice();
			if (roll < 2) {
				System.out.print("Enter the dice, separated by space, to reroll (1-5), or 0 to keep all. \nFor example, if you want to keep the 2nd and 3rd die, enter 2 3: \n");
				String rerollInput = scanner.next();
                for (int i = 0; i < 5; i++) {
                    reroll[i] = rerollInput.contains(String.valueOf(i + 1));
                }
			}
		}
		System.out.println("Scoring board:");
		showScoreOption();
		int category;
		do {
			System.out.print("Enter ONE category to score (0-12): ");
			category = scanner.nextInt();
		} while (!game.scorable(category));
		game.score(category);
	}

	public void showDice() {
		System.out.print("Roll: ");
		for (Dice d : game.getDice()) {
			System.out.print(d.getVal() + " ");
		}
		System.out.println();
	}

	public void showScoreOption() {
		for (int i = 0; i < 13; i++) {
			if (game.scorable(i)) {
				System.out.printf("%2d. %s: %d%n", i, getCategory(i), game.computeScore(i));
			}
		}
	}

	public void showScoreboard() {
		int upperTotal = Arrays.stream(game.getUpper()).sum();
	    int lowerTotal = Arrays.stream(game.getLower()).sum();

	    System.out.println("Scoreboard:");
	    for (int i = 0; i < 6; i++) {
	        System.out.printf("%2d. %s: %d%n", i, getCategory(i), game.getUpper()[i]);
	    }
	    System.out.println("Upper section total: " + upperTotal);

	    for (int i = 6; i < 13; i++) {
	        System.out.printf("%2d. %s: %d%n", i, getCategory(i), game.getLower()[i - 6]);
	    }
	    System.out.println("Lower section total: " + lowerTotal);

	    int grandTotal = game.getTotalScore();
	    System.out.println("Grand total: " + grandTotal);
	}

	private String getCategory(int category) {
		return names[category];
	}
	
}
