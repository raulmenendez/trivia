
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	private static boolean notAWinner;

	public static void play(Game game, Random rand) throws MinimumPlayersException {

		if (isGamePayable(game)){
			playGame(game, rand);
		}else{
			throw new MinimumPlayersException();
		}
		
	}

	private static void playGame(Game game, Random rand) {
		do {
			
			int diceResult = game.rollTheDice(rand);
			game.roll(diceResult);
			
			if (isACorrectAnswerExpected(rand)) {
				notAWinner = game.wasCorrectlyAnswered();
			} else {
				notAWinner = game.wrongAnswer();				
			}
						
		} while (gameIsNotFinished());
	}

	private static boolean isGamePayable(Game game) {
		return game.isPlayable();
	}

	private static boolean isACorrectAnswerExpected(Random rand) {
		return rand.nextInt(9) != 7;
	}
	
	private static boolean gameIsNotFinished() {		
		return notAWinner;
	}
	
}
