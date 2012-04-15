package com.adaptionsoft.games.uglytrivia;

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

public class GameTest {

	private ByteArrayOutputStream outContent;
	private Game gameSpy;
	private Random randomMock;
	
	@Before
	public void setUp() throws Exception {
		randomMock = mock(Random.class);
		gameSpy = spy(new Game());
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	public void ask_question_and_user_is_out_of_penalty_box() {
		
		when(randomMock.nextInt(5)).thenReturn(0);
		
		setUpPlayersToPlay(gameSpy,1);
		int roll = gameSpy.rollTheDice(randomMock);
		
		gameSpy.askTheQuestion(roll);
		
		assertEquals(false,gameSpy.isUserInThePenaltyBox());
		verify(gameSpy,times(0)).isUserGettingOutFromPenaltyBox(roll);
		
	}
	
	@Test
	public void ask_question_and_user_is_in_the_penalty_box_but_goes_out() {
		
		when(randomMock.nextInt(5)).thenReturn(0);
		setUpPlayersToPlay(gameSpy,1);
		gameSpy.wrongAnswer();
		int roll = gameSpy.rollTheDice(randomMock);
		
		gameSpy.askTheQuestion(roll);
		
		assertEquals(true,gameSpy.isUserInThePenaltyBox());		
		verify(gameSpy,times(1)).isUserGettingOutFromPenaltyBox(roll);
		assertEquals(true,gameSpy.isUserGettingOutFromPenaltyBox(roll));
		verify(gameSpy,times(1)).play(roll);
	}
	
	@Test
	public void ask_question_and_user_is_in_the_penalty_box_and_fails_to_be_out() {
		
		when(randomMock.nextInt(5)).thenReturn(1);
		setUpPlayersToPlay(gameSpy,1);
		gameSpy.wrongAnswer();
		int roll = gameSpy.rollTheDice(randomMock);
		
		gameSpy.askTheQuestion(roll);
		
		assertEquals(true,gameSpy.isUserInThePenaltyBox());		
		verify(gameSpy,times(1)).isUserGettingOutFromPenaltyBox(roll);
		assertEquals(false,gameSpy.isUserGettingOutFromPenaltyBox(roll));
		verify(gameSpy,never()).play(roll);
		
		
	}
	
		
	private void setUpPlayersToPlay(Game gameSpy,int numberOfPlayers){
		for (int player=1;player<=numberOfPlayers;player++){
			gameSpy.add("Player".concat(String.valueOf(player)));	
		}
	}

}
