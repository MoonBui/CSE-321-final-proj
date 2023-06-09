/**
 * Yahtzee Console for GUI Implementation
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class YahtzeeGUI {
	// Private variables
	private YahtzeeEngine game;
	private String[] names = { "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes", "Three of a Kind", "Four of a Kind",
			"Full House", "Small Straight", "Large Straight", "Yahtzee", "Chance" };
	private JFrame frame;
	private JTextArea ta;
	private JTextField tf;
	private String input;
	

	/**
	 * Constructor for GUI
	 */
	public YahtzeeGUI() {
		game = new YahtzeeEngine();
		frame = new JFrame("Yahtzee Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        JLabel label = new JLabel("Enter Text");
        JButton send = new JButton("Send");
        ta = new JTextArea();
        tf = new JTextField(10); // accepts up to 10 characters
        
        //
        JPanel panel = new JPanel(); // the panel is not visible in output
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.setVisible(true);
        
        send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				input = tf.getText(); 
			}
        });
        
	}

	/**
	 * Main play function to initiate game
	 */
	public void play() {
		while (!game.isOver()) {
			playARound();
		}
		showTotal();
		waitInput();
		String playOption = input;
		if (playOption.equalsIgnoreCase("y")) {
			input = null;
			game = new YahtzeeEngine();
			play();
		} else if (playOption.equalsIgnoreCase("n")) {
			frame.dispose();
		}
	}

	/**
	 * Function to excute rounds of the game
	 */
	private void playARound() {
		game.resetDice();
		ta.setText("Round " + (game.getRound() + 1) + ":\n");
		boolean[] reroll = new boolean[5];
		
		// roll the round
		for (int roll = 0; roll < 3; roll++) {
			game.rerollDice(reroll);
			ta.append(showDice());

			if (roll < 2) {
				ta.append("Enter the dice to reroll (1-5) with no spaces, or 0 to keep all. "
						+ "\nFor example, if you want to reroll the 2nd and 3rd die, enter 23: \n");

				// Wait for user input before proceeding
				waitInput();			
				String rerollInput = input;
				
				if (Integer.parseInt(input) != 0) {
	                for (int i = 0; i < 5; i++) {
	                    reroll[i] = rerollInput.contains(String.valueOf(i + 1));
	                }
				} else 
					roll = 2;
				
                input = null;
			}
		}
		
		// get scoring board
		ta.append("Scoring board: \n");
		showScoreOption();
		int category;
		do {
			ta.append("Enter only ONE category to score (0-12): \n");
			waitInput();
			category = Integer.parseInt(input);
			input = null;
		} while (!game.scorable(category));
			game.score(category);
			
	}

	/**
	 * Function to grab dice rolls
	 * @return a total of 5 dice rolls
	 */
	public String showDice() {
		String result = "Roll: ";
		for (Dice d : game.getDice()) {
			result += d.getVal() + " ";
		}

		return result += "\n";
		
	}

	/**
	 * Function to show score
	 */
	public void showScoreOption() {
		String result = "";
		for (int i = 0; i < 13; i++) {
			if (game.scorable(i)) {
				if (i == 11) {
					if (game.isYahtzee()) result += i + ". " + getCategory(i) + ": 50" + "\n";
					else result += i + ". " + getCategory(i) + ": 0" + "\n";
				}
				else result += i + ". " + getCategory(i) + ": " + game.computeScore(i) + "\n";
			}
		}
		
		ta.append(result);
	}

	/**
	 * Function to show score board
	 */
	public void showTotal() {
	    String upper = "";
	    String lower = "";

	    ta.append("FINAL SCORE BOARD: \n");
	    for (int i = 0; i < 6; i++) {
	        upper += i + ". " + getCategory(i) + ": " + game.getUpper()[i] + "\n";
	    }
	    ta.append(upper);

	    for (int i = 6; i < 13; i++) {
	        lower += i + ". " + getCategory(i) + ": " + game.getLower()[i - 6] + "\n";
	    }
	    ta.append(lower);
	    ta.append("Number of Yahtzee scored: " + game.getBonusYahtzee() + "\n");
	    ta.append("Total score: " + game.getTotalScore() + "\n"); 
		ta.append("Play another game? (y/n): ");
	}

	/****PRIVATE METHODS***/
	
	/**
	 * 
	 * @param category
	 * @return
	 */
	private String getCategory(int category) {
		return names[category];
	}
	
	/**
	 * Helper function to sleep a thread until input value is not null
	 */
	private void waitInput() {
		while (input == null) {
	        try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            // ignore
	        }
	    }
	}
}
