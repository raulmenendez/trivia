
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	protected static void play(Game game, Random rand) {
		do {
			
			int roll = game.rollTheDice(rand);
			game.askTheQuestion(roll);
			
			if (isACorrectAnswerExpected(rand)) {
				notAWinner = game.wasCorrectlyAnswered();
			} else {
				notAWinner = game.wrongAnswer();				
			}
			
		} while (gameIsNotFinished(rand));
	}

	private static boolean isACorrectAnswerExpected(Random rand) {
		return rand.nextInt(9) != 7;
	}
	
	private static boolean gameIsNotFinished(Random rand) {		
		return notAWinner;
	}
	
}
