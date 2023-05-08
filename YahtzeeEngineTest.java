//package yahtzee;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YahtzeeEngineTest {
	YahtzeeEngine engine; 

	@BeforeEach
	void setUp() throws Exception {
		engine = new YahtzeeEngine(); 
	}//end setUp

	@AfterEach
	void tearDown() throws Exception {
		engine = null; 
	}//end tearDown
	
	@Test
	void testScorable() {
		//ISP
		//test when upper section is true & lower section is false 
		assertEquals(engine.scorable(0), true); //edge pair: [1, 2, 4] 
		assertEquals(engine.scorable(1), true); 
		assertEquals(engine.scorable(2), true); 
		assertEquals(engine.scorable(3), true); 
		assertEquals(engine.scorable(4), true); 
		assertEquals(engine.scorable(5), true); 
		//test when lower section is true & upper section is false 
		assertEquals(engine.scorable(6), true); //edge pair: [1, 3 ,5]
		assertEquals(engine.scorable(7), true); 
		assertEquals(engine.scorable(8), true); 
		assertEquals(engine.scorable(9), true); 
		assertEquals(engine.scorable(10), true); 
		assertEquals(engine.scorable(11), true); 
		assertEquals(engine.scorable(12), true); 
		//test when both sections are false  
		assertEquals(engine.scorable(-1), false); 
		assertEquals(engine.scorable(13), false); 
		
		//edge pair when already scored 
		engine.scoredUpper[1] = true;
		engine.scoredLower[4] = true;
		assertEquals(engine.scorable(1), false); //[1, 2, 6] 
		assertEquals(engine.scorable(10), false); //[1, 3, 6] 
		//edge pair when category is out of bounds 
		assertEquals(engine.scorable(13), false); //[1,8] 
	}//end testScorable

	@Test 
	void testComputeScore() {
		//ISP test for upper categories 
		engine.dice[0].val = 1;
		engine.dice[1].val = 1; 
		engine.dice[2].val = 2; 
		engine.dice[3].val = 3; 
		engine.dice[4].val = 6; 
		assertEquals(engine.computeScore(0), 2); //aces
		assertEquals(engine.computeScore(1), 2); //twos
		assertEquals(engine.computeScore(2), 3); //threes
		engine.dice[0].val = 4;
		engine.dice[1].val = 4; 
		engine.dice[2].val = 5; 
		engine.dice[3].val = 5; 
		assertEquals(engine.computeScore(3), 8); //fours
		assertEquals(engine.computeScore(4), 10); //fives
		//adupc: [1, 2, 3, 2, 4, 5] 
		assertEquals(engine.computeScore(5), 6); //sixes 
		//ISP test for lower categories
		engine.dice[0].val = 1;
		engine.dice[1].val = 1; 
		engine.dice[2].val = 1; 
		engine.dice[3].val = 3; 
		engine.dice[4].val = 4; 
		//adupc: [1, 2, 4, 6, 7, 15, 16, 17]
		assertEquals(engine.computeScore(6), 10); //3 of a kind
		engine.dice[3].val = 1; 
		//adupc:  [1, 2, 4, 6, 8, 20, 21, 22]
		assertEquals(engine.computeScore(7), 8);  //4 of a kind 
		engine.dice[3].val = 3; 
		engine.dice[4].val = 3;
		//adupc: [1, 2, 4, 6, 9, 25, 26, 27, 28, 29, 30, 31]
		assertEquals(engine.computeScore(8), 25); //full house 
		engine.dice[0].val = 2;
		engine.dice[1].val = 3; 
		engine.dice[2].val = 4; 
		engine.dice[3].val = 5; 
		engine.dice[4].val = 6; 
		//adupc: [1, 2, 4, 6, 10, 33]
		//assertEquals(engine.computeScore(9), 30); //small straight *************** error
		//adupc: [1, 2, 4, 6, 11, 35]
		assertEquals(engine.computeScore(10), 40); //large straight 
		engine.dice[0].val = 1;
		engine.dice[1].val = 1; 
		engine.dice[2].val = 1; 
		engine.dice[3].val = 1; 
		engine.dice[4].val = 1; 
		//adupc: [1, 2, 4, 6, 12, 37, 38, 39]
		assertEquals(engine.computeScore(11), 50); //yahtzee
		//adupc: [1, 2, 4, 6, 13]
		assertEquals(engine.computeScore(12), 5); //chance 
	}//end testComputeScore\
	
	@Test 
	void testGetTotalScore() {
		//adupc: [1, 2, 3, 5, 2, 4, 7, 8, 9, 10, 9, 11, 13, 14] 
		//edge pair: [1, 2, 3], [1, 2, 4], [2, 3, 5], [2, 4, 7], [3, 5, 2], [4, 7, 8], [5, 2,3 ], [5, 2, 4], [7, 8, 9]
		engine.upper[0] = 2; 
		engine.upper[1] = 2;
		engine.upper[2] = 3;
		engine.upper[3] = 8;
		engine.upper[4] = 10;
		engine.upper[5] = 6;	//total upper = 31
		engine.lower[0] = 10;
		engine.lower[1] = 8;
		engine.lower[2] = 25;
		engine.lower[3] = 30;
		engine.lower[4] = 40;
		engine.lower[5] = 50;
		engine.lower[6] = 5;	//total lower = 168 
		assertEquals(engine.getTotalScore(), 199);
		
		//adupc: [1, 2, 3, 5, 2, 4, 6, 8, 9, 10, 9, 11, 12, 14]
		//edge pair: [2, 4, 6], [4, 6, 8], [6, 8, 9], [8, 9, 10], [9, 10, 9], [9, 11, 13], [11, 13, 14] 
		engine.upper[0] = 5; 
		engine.upper[1] = 10;
		engine.upper[2] = 6;
		engine.upper[3] = 12;
		engine.upper[4] = 15;
		engine.upper[5] = 18;	//total upper = 66
		engine.lower[0] = 10;
		engine.lower[1] = 8;
		engine.lower[2] = 25;
		engine.lower[3] = 30;
		engine.lower[4] = 40;
		engine.lower[5] = 50;
		engine.lower[6] = 5;	//total lower = 168 
		assertEquals(engine.getTotalScore(), 269);
		
		//edge pair: [8, 9, 11], [9, 11, 12], [10, 9, 10], [10, 9, 11], [11, 12, 14]
		engine.upper[0] = 5; 
		engine.upper[1] = 10;
		engine.upper[2] = 6;
		engine.upper[3] = 12;
		engine.upper[4] = 15;
		engine.upper[5] = 18;	//total upper = 66
		engine.lower[0] = 10;
		engine.lower[1] = 8;
		engine.lower[2] = 25;
		engine.lower[3] = 30;
		engine.lower[4] = 40;
		engine.lower[5] = 50;
		engine.lower[6] = 5;	//total lower = 168 
		engine.bonusPoint = 2; 
		assertEquals(engine.getTotalScore(), 369);
	}//end testGetTotalScore
	
}//end class