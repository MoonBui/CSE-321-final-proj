/**
 * Code for a Yahtzee's logic implementation
 * 
 * @author Jenn Pham
 *
 */
public class YahtzeeEngine {
	// private variables
	private Dice[] dice; // the array of the rolled dice
	private int round, bonusPoint; // 100 bonus for Yahtzee
	private int[] upper, lower; // arrays for upper and lower boards; use for entering scores.
	private boolean[] scoredUpper, scoredLower; // arrays for scored upper/lower sections
	
	// Final variables
	private final int MAX_ROUNDS = 13; // number of rounds in a game
	
	/**
	 * Constructor YahtzeeEngine
	 */
	public YahtzeeEngine() {
		// roll the Dice
		resetDice();
		
		// Reset the variables for a new Game
		upper = new int[6];
		lower = new int[7];
		scoredUpper = new boolean[6];
		scoredLower = new boolean[7];
		round = bonusPoint = 0;
	}

	/**
	 * Roll method for each round.
	 * Take in a boolean array to check for which die is rerolled.
	 * 
	 * @param reroll
	 */
	public void rerollDice(boolean[] reroll) {
		for (int i = 0; i < 5; i++) {
			if (reroll[i]) {
				dice[i] = new Dice();
			}
		}
	}

	/**
	 * Check if the category can be scored. True if empty, else false.
	 * @param category
	 * @return boolean
	 */
	public boolean scorable(int category) {		
        if (getSection(category) == 1) {
        	if (!scoredUpper[category]) return true;
        } else if (getSection(category) == 2) {
        	if (!scoredLower[category - 6]) return true;
        }
        
        return false;
	}
	
	/**
	 * update category with score if it's scorable.
	 * updates the bonus Yahtzee counter if needed.
	 *
	 * @param category the index of the category to score.
	 */
	public void updateScore(int category) {
		int score = 0;
		if (scorable(category)) {
			score = computeScore(category);
			if (getSection(category) == 1) {
				upper[category] = score;
				scoredUpper[category] = true;
			} else {
				lower[category - 6] = score;
				scoredLower[category - 6] = true;
			}
			round++;
		}
	}

	/**
	 * Calculate Score for a category
	 * @param category
	 * @return
	 */
	public int computeScore(int category) {
		int[] counts = new int[6]; // number of counts for each face value of a die
		for (Dice d : dice) {
			int index = d.getVal() - 1;
			counts[index]++;
		}

		if (getSection(category) == 1) {
			return counts[category] * (category + 1);
		} else {
			switch (category) {
			case 6: // three of a kind
				for (int count : counts) {
					if (count >= 3) return getDiceSum(dice);
				}
				break;
			case 7: // four of a kind
				for (int count : counts) {
					if (count >= 4) return getDiceSum(dice);
				}
				break;
			case 8: // full house
				boolean threeOfAKind = false;
				boolean pair = false;
				for (int count : counts) {
					if (count == 3) threeOfAKind = true;
					else if (count == 2) pair = true;
				}
				
				if (pair && threeOfAKind) return 25;
				else return 0;
			case 9: // small straight
				if (consecutiveCount(counts) >= 4) return 30;
				break;
			case 10: // large straight
				if (consecutiveCount(counts) == 5) return 40;
                break;
			case 11: // yahtzee
				for (int count : counts) {
					if (count == 5) {
						bonusPoint++; // update yathzee count
						return 50;
					}
				}
				break;
			case 12: // Chance
				return getDiceSum(dice);
			default:
				break;
			}
		}
		
		return 0;
	}
	
	/**
	 * Get total score for the game.
	 * 
	 * @return int total score.
	 */
	public int getTotalScore() {
		// Upper deck sum	    
	    int upperSum = 0;
	    for (int i = 0; i < upper.length; i++) {
	        upperSum += upper[i];
	    }
	    
	    // check for bonus points
	    int upperBonus = 0;
        if (upperSum >= 63) upperBonus = 35;
        
        // Lower deck sum
	    int lowerSum = 0;
	    for (int i = 0; i < lower.length; i++) {
	        lowerSum += lower[i];
	    }
	    
	    // check for Yahtzee bonus
        int bonusScore = 0;
        if (bonusPoint > 0) bonusScore = (bonusPoint - 1) * 100;
        
        // Sum everything
	    return upperSum + upperBonus + lowerSum + bonusScore;
	}
	
	/*****HELPER METHODS****/
	/**
	 * Get the section
	 * @param category
	 * @return
	 */
	private int getSection(int category) {
		if (category >= 0 && category < 6) return 1;
		else if (category >= 6 && category < 13) return 2;
		
		return 0;
	}
	/**
	 * Get total sum of Dice array
	 * @param dice
	 * @return
	 */
	private int getDiceSum(Dice[] dice) {
		int result = 0;
		for (Dice d : dice) {
	        result += d.getVal();
	    }
		return result;
	}
	
	/**
	 * Count consecutive values in a dice array
	 * @param counts
	 * @return
	 */
	private int consecutiveCount(int[] counts) {
	    int consecutive = 0;
	    int maxConsecutive = Integer.MIN_VALUE;
	    for (int count : counts) {
	        if (count >= 1) {
	            consecutive++;
	            maxConsecutive = Math.max(maxConsecutive, consecutive);
	        } else {
	            consecutive = 0;
	        }
	    }
	    return maxConsecutive;
	}
	
	/**
	 * Reset the dice for each round (not reroll).
	 */
	public void resetDice() {
		dice = new Dice[5];
        for (int i = 0; i < 5; i++) {
            dice[i] = new Dice();
        }
	}
	
	/**
	 * Get Dice Array
	 * @return Dice[]
	 */
	public Dice[] getDice() {
		return dice;
	}

	/**
	 * Check if the game is over.
	 * @return boolean
	 */
	public boolean isOver() {
		return round >= MAX_ROUNDS;
	}
	
	/**
	 * Get Upper Section Potential Score
	 * @return int[]
	 */
	public int[] getUpper() {
		return upper;
	}

	/**
	 * Get Lower Section Potential Score
	 * @return int[]
	 */
	public int[] getLower() {
		return lower;
	}

	/**
	 * Get the current round
	 * @return int
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Get the Bonus point
	 * @return int
	 */
	public int getBonusYahtzee() {
		return bonusPoint;
	}
}
