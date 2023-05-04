/**
 * Code for a Yahtzee's logic implementation
 * 
 * @author Jenn Pham
 *
 */
public class YahtzeeEngine {
	// private variables
	private Dice[] dice; // the array of the rolled dice
	private int round, bonusPoint; // 100 bonus for each Yahtzee after the first one
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
	public void rerollDice(boolean[] rerolledDice) {
		for (int i = 0; i < 5; i++) {
			if (rerolledDice[i]) {
				dice[i] = new Dice();
			}
		}
	}

	/**
	 * Check if the category can be scored. True if empty, else false.
	 * @param category
	 * @return boolean
	 */
	public boolean canScore(int category) {		
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
		if (canScore(category)) {
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
		// 0 1 2 3 4 5 = 1 2 3 4 5 6
		int[] countForEachNum = new int[6]; // number of counts for each face value of a die
		
		for (Dice d : dice) {
			int index = d.getVal() - 1;
			countForEachNum[index]++;
		}

		if (getSection(category) == 1) {
			return countForEachNum[category] * (category + 1);
		} else {
			switch (category) {
			case 6: // three of a kind
				for (int count : countForEachNum) {
					if (count >= 3) return getDiceSum(dice);
				}
				break;
			case 7: // four of a kind
				for (int count : countForEachNum) {
					if (count >= 4) return getDiceSum(dice);
				}
				break;
			case 8: // full house
				boolean threeOfAKind = false;
				boolean pair = false;
				for (int count : countForEachNum) {
					if (count == 3) threeOfAKind = true;
					else if (count == 2) pair = true;
				}
				
				if (pair && threeOfAKind) return 25;
				else return 0;
			case 9: // small straight
				if (consecutiveCount(countForEachNum) >= 4) return 30;
				break;
			case 10: // large straight
				if (consecutiveCount(countForEachNum) == 5) return 40;
                break;
			case 11: // Yahtzee
				for (int count : countForEachNum) {
					if (count == 5) {
						bonusPoint++; // update Yahtzee count
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
	    
	    // if the user has over 63 points in the upper section, then they receive a bonus of 35 points.
        if (upperSum >= 63) upperSum += 35;
        
        // Lower deck sum
	    int lowerSum = 0;
	    for (int i = 0; i < lower.length; i++) {
	        lowerSum += lower[i];
	    }
	    
	    // add Yahtzee bonus
        if (bonusPoint > 0) lowerSum += (bonusPoint - 1) * 100;
        
	    return upperSum + lowerSum;
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
	private int consecutiveCount(int[] countForEachNum) {
	    int consecutive = 0;
	    int maxConsecutive = Integer.MIN_VALUE;
	    
	    for (int count : countForEachNum) {
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