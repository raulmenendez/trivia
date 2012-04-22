
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.MinimumPlayersException;
import com.adaptionsoft.games.uglytrivia.MinimumQuestionsException;


public class GameRunner {

	private static boolean notAWinner;

	public static void play(Game game, Random rand) throws MinimumPlayersException, MinimumQuestionsException {

		if (isGamePlayable(game)){ 
			playGame(game, rand);
		}
		
	}

	private static void playGame(Game game, Random rand) {
		do {
			
			int diceValue = game.rollTheDice(rand);
			game.turn(diceValue);
			
			if (isACorrectAnswerExpected(rand)) {
				notAWinner = game.wasCorrectlyAnswered();
			} else {
				notAWinner = game.wrongAnswer();				
			}
						
		} while (gameIsNotFinished());
	}

	private static boolean isGamePlayable(Game game) throws MinimumPlayersException, MinimumQuestionsException {
		return game.isPlayable();
	}

	private static boolean isACorrectAnswerExpected(Random rand) {
		return rand.nextInt(9) != 7;
	}
	
	private static boolean gameIsNotFinished() {		
		return notAWinner;
	}
	
}
