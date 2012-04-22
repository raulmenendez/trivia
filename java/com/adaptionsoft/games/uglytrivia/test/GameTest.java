package com.adaptionsoft.games.uglytrivia.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Players;
import com.adaptionsoft.games.uglytrivia.Questions;

public class GameTest {

	private ByteArrayOutputStream outContent;
	private Game gameSpy;
	private Random randomMock;
	private Players players;
	private Questions questions;
	
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
	
	@Test
	public void ask_question_and_user_is_out_of_penalty_box() {
		
		when(randomMock.nextInt(5)).thenReturn(0);		
		gameSpy.setPlayers(setUpPlayersToPlay(1));
		
		int roll = gameSpy.rollTheDice(randomMock);
		gameSpy.turn(roll);
		
		assertEquals(false,gameSpy.isPlayerInThePenaltyBox());
		verify(gameSpy,times(0)).isPlayerGettingOutFromPenaltyBox(roll);
		
	}
	
	@Test
	public void ask_question_and_user_is_in_the_penalty_box_but_goes_out() {
		
		when(randomMock.nextInt(5)).thenReturn(0);
		gameSpy.setPlayers(setUpPlayersToPlay(1));
		gameSpy.setQuestions(questions);
		
		gameSpy.wrongAnswer();		
		int roll = gameSpy.rollTheDice(randomMock);		
		gameSpy.turn(roll);
		
		assertEquals(true,gameSpy.isPlayerInThePenaltyBox());		
		verify(gameSpy,times(1)).isPlayerGettingOutFromPenaltyBox(roll);
		assertEquals(true,gameSpy.isPlayerGettingOutFromPenaltyBox(roll));
		verify(gameSpy,times(1)).play(roll);
	}
	
	@Test
	public void ask_question_and_user_is_in_the_penalty_box_and_fails_to_be_out() {
		
		when(randomMock.nextInt(5)).thenReturn(1);
		gameSpy.setPlayers(setUpPlayersToPlay(1));
		gameSpy.wrongAnswer();
		int roll = gameSpy.rollTheDice(randomMock);
		
		gameSpy.turn(roll);
		
		assertEquals(true,gameSpy.isPlayerInThePenaltyBox());		
		verify(gameSpy,times(1)).isPlayerGettingOutFromPenaltyBox(roll);
		assertEquals(false,gameSpy.isPlayerGettingOutFromPenaltyBox(roll));
		verify(gameSpy,never()).play(roll);
		
		
	}
		
	private Players setUpPlayersToPlay(int numberOfPlayers){
		for (int player=1;player<=numberOfPlayers;player++){
			players.addPlayer("Player".concat(String.valueOf(player)));
		}
		return players;
	}

}
