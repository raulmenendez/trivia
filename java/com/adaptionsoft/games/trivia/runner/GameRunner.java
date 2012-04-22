
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.MaximumPlayersExcededException;
import com.adaptionsoft.games.uglytrivia.MinimumPlayersException;
import com.adaptionsoft.games.uglytrivia.MinimumQuestionsException;


public class GameRunner {

	private static boolean winnerPlayer;

	public static void play(Game game, Random rand) throws MinimumPlayersException, MinimumQuestionsException, MaximumPlayersExcededException {

		if (isGamePlayable(game)){ 
			playGame(game, rand);
		}
		
	}

	private static void playGame(Game game, Random rand) {
		do {
			
			int diceValue = game.rollTheDice(rand);
			game.turn(diceValue);
			
			if (isACorrectAnswerExpected(rand)) {
				winnerPlayer = game.wasCorrectlyAnswered();
			} else {
				winnerPlayer = game.wasIncorrectlyAnswered();				
			}
						
		} while (!isGameFinished());
	}

	private static boolean isGamePlayable(Game game) throws MinimumPlayersException, MinimumQuestionsException, MaximumPlayersExcededException {
		return game.isPlayable();
	}

	private static boolean isACorrectAnswerExpected(Random rand) {
		return rand.nextInt(9) != 7;
	}
	
	private static boolean isGameFinished() {		
		return winnerPlayer;
	}
	
}
