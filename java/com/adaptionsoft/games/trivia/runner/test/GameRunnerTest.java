package com.adaptionsoft.games.trivia.runner.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Historic;
import com.adaptionsoft.games.uglytrivia.MinimumPlayersException;
import com.adaptionsoft.games.uglytrivia.MinimumQuestionsException;
import com.adaptionsoft.games.uglytrivia.Players;
import com.adaptionsoft.games.uglytrivia.Questions;

public class GameRunnerTest {

	private ByteArrayOutputStream outContent;
	private Game gameSpy;
	private Random randomMock;
	private Players players;
	private static Historic historic;
	private Questions questions;

	@BeforeClass
	public static void SetUpClass(){
		historic = new Historic();
		historic.readSerialized();
	}
	
	@Before
	public void setUp() throws Exception {
		randomMock = mock(Random.class);
		gameSpy = spy(new Game());
		questions = new Questions();
		questions.setNumberQuestions(50);
		gameSpy.setQuestions(questions);
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		players = new Players(); 
	}
	
	@AfterClass
	public static void afterExecution(){
		historic.printResults();
		historic.createSerialized();
	}
	
	@Test(expected=MinimumPlayersException.class)
	public void a_game_with_no_players_at_all() throws MinimumPlayersException, MinimumQuestionsException {	
		gameSpy.setPlayers(players);
		GameRunner.play(gameSpy, randomMock);
	}
	
	@Test(expected=MinimumQuestionsException.class)
	public void a_game_without_questions_and_two_players() throws MinimumPlayersException, MinimumQuestionsException {
		questions = new Questions();
		questions.setNumberQuestions(0);
		gameSpy.setQuestions(questions);
		gameSpy.setPlayers(setUpPlayersToPlay(2));
		GameRunner.play(gameSpy, randomMock);
	}
	
	@Test(expected=MinimumPlayersException.class)
	public void a_game_with_only_one_player() throws MinimumPlayersException, MinimumQuestionsException {
		
		gameSpy.setPlayers(setUpPlayersToPlay(1));
		GameRunner.play(gameSpy, randomMock);
		
	}
	
	@Test
	public void a_game_with_two_players_first_answer_in_player_one_is_ko_rest_are_ok() throws MinimumPlayersException, MinimumQuestionsException {

		when(randomMock.nextInt(5)).thenReturn(0);
		when(randomMock.nextInt(9)).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(2));	
		GameRunner.play(gameSpy, randomMock);
		
		assertEquals(true,outContent.toString().contains("Player1 was sent to the penalty box"));
		assertEquals(true,outContent.toString().contains("Player2 now has 6 Gold Coins."));
		verify(gameSpy,times(1)).wrongAnswer();	
		verify(gameSpy,times(11)).wasCorrectlyAnswered();
	}
	
	@Test
	public void a_game_with_3_players_all_answers_ok_and_player_one_has_6_gold_coins() throws MinimumPlayersException, MinimumQuestionsException {

		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player1 now has 6 Gold Coins."));		
		verify(gameSpy,times(0)).wrongAnswer();
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	
	@Test
	public void a_game_with_3_players_some_answers_ok_and_player_two_has_6_gold_coins() throws MinimumPlayersException, MinimumQuestionsException {

		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player2 now has 6 Gold Coins."));		
		verify(gameSpy,times(1)).wrongAnswer();	
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	
	@Test
	public void a_game_with_3_players_some_answers_ok_and_player_three_has_6_gold_coins() throws MinimumPlayersException, MinimumQuestionsException {

		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);				
		
		assertEquals(true,outContent.toString().contains("Player3 now has 6 Gold Coins."));		
		verify(gameSpy,times(2)).wrongAnswer();
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	

	@Test
	public void validate_scores_game_with_2_players_and_player_one_has_all_answers_ok_has_6_gold_coins() throws MinimumPlayersException, MinimumQuestionsException {				
		when(randomMock.nextInt(5)).thenReturn(0);
		when(randomMock.nextInt(9)).thenReturn(2);
		
		gameSpy.setPlayers(setUpPlayersToPlay(2));
		GameRunner.play(gameSpy, randomMock);

		historic.addPlayersScores(gameSpy.getPlayersScores());
		
		assertEquals(6,gameSpy.getScoreFrom("Player1"));
	}

	@Test
	public void validate_scores_game_with_3_players_some_answers_ok_and_player_three_has_6_gold_coins() throws MinimumPlayersException, MinimumQuestionsException {
		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);				

		historic.addPlayersScores(gameSpy.getPlayersScores());
		assertNotNull(gameSpy.getPlayersScores());
	}

	
	private Players setUpPlayersToPlay(int numberOfPlayers){
		for (int player=1;player<=numberOfPlayers;player++){
			players.addPlayer("Player".concat(String.valueOf(player)));
		}
		return players;
	}
	
}
