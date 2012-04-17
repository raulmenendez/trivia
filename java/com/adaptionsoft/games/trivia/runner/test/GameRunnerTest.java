package com.adaptionsoft.games.trivia.runner.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.trivia.runner.MinimumPlayersException;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Players;

public class GameRunnerTest {

	private ByteArrayOutputStream outContent;
	private Game gameSpy;
	private Random randomMock;
	private Players players;
	
	@Before
	public void setUp() throws Exception {
		randomMock = mock(Random.class);
		gameSpy = spy(new Game());
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		players = new Players(); 
	}
	
	@Test(expected=MinimumPlayersException.class)
	public void a_game_with_no_players_at_all() throws MinimumPlayersException {	
		gameSpy.setPlayers(players);
		GameRunner.play(gameSpy, randomMock);
	}
	
	@Test(expected=MinimumPlayersException.class)
	public void a_game_with_only_one_player_and_all_answers_are_ok() throws MinimumPlayersException {
		
		when(randomMock.nextInt(5)).thenReturn(0);
		when(randomMock.nextInt(9)).thenReturn(2);
		
		gameSpy.setPlayers(setUpPlayersToPlay(1));
		GameRunner.play(gameSpy, randomMock);

		assertEquals(true,outContent.toString().contains("Player1 now has 6 Gold Coins."));
		verify(gameSpy,times(0)).wrongAnswer();
		verify(gameSpy,times(6)).wasCorrectlyAnswered();
		
	}
	
	@Test(expected=MinimumPlayersException.class)
	public void a_game_with_only_one_player_first_answer_is_ko_rest_are_ok() throws MinimumPlayersException {

		when(randomMock.nextInt(5)).thenReturn(0);
		when(randomMock.nextInt(9)).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(1));	
		GameRunner.play(gameSpy, randomMock);
		
		assertEquals(true,outContent.toString().contains("Player1 was sent to the penalty box"));
		assertEquals(true,outContent.toString().contains("Player1 now has 6 Gold Coins."));
		verify(gameSpy,times(1)).wrongAnswer();	
		verify(gameSpy,times(6)).wasCorrectlyAnswered();
	}
	
	@Test
	public void a_game_with_3_players_all_answers_ok_and_player_one_has_6_gold_coins() throws MinimumPlayersException {

		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player1 now has 6 Gold Coins."));		
		verify(gameSpy,times(0)).wrongAnswer();
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	
	@Test
	public void a_game_with_3_players_some_answers_ok_and_player_two_has_6_gold_coins() throws MinimumPlayersException {

		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);
		
		assertEquals(true,outContent.toString().contains("Player2 now has 6 Gold Coins."));		
		verify(gameSpy,times(1)).wrongAnswer();	
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	
	@Test
	public void a_game_with_3_players_some_answers_ok_and_player_three_has_6_gold_coins() throws MinimumPlayersException {

		Random randomSpy = spy (new Random());
		when(randomSpy.nextInt(9)).thenReturn(7).thenReturn(7).thenReturn(1);
		
		gameSpy.setPlayers(setUpPlayersToPlay(3));
		GameRunner.play(gameSpy, randomSpy);				
		
		assertEquals(true,outContent.toString().contains("Player3 now has 6 Gold Coins."));		
		verify(gameSpy,times(2)).wrongAnswer();
		verify(gameSpy,times(16)).wasCorrectlyAnswered();
	}
	
	private Players setUpPlayersToPlay(int numberOfPlayers){
		for (int player=1;player<=numberOfPlayers;player++){
			players.addPlayer("Player".concat(String.valueOf(player)));
		}
		return players;
	}
	
}
