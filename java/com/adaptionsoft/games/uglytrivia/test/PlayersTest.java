package com.adaptionsoft.games.uglytrivia.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.MaximumPlayersExcededException;
import com.adaptionsoft.games.uglytrivia.MinimumPlayersException;
import com.adaptionsoft.games.uglytrivia.MinimumQuestionsException;
import com.adaptionsoft.games.uglytrivia.Players;

public class PlayersTest extends Players {

	private Players players;
	
	@Before
	public void SetUp(){
		players = new Players();	
	}
	
	@Test
	public void no_player_at_all() throws MaximumPlayersExcededException {		
		
		assertEquals(0, players.getNumberOfPlayers());
		
	}
	
	@Test
	public void add_one_player() throws MaximumPlayersExcededException {		
		
		setUpPlayersToPlay(1);
		
		assertEquals(1, players.getNumberOfPlayers());
		
	}

	@Test
	public void add_two_players() throws MaximumPlayersExcededException {		

		setUpPlayersToPlay(2);
		
		assertEquals(2, players.getNumberOfPlayers());
		
	}
	
	@Test
	public void add_maximum_players() throws MaximumPlayersExcededException {		

		setUpPlayersToPlay(6);
		
		assertEquals(6, players.getNumberOfPlayers());
		
	}
	
	@Test(expected=MaximumPlayersExcededException.class)
	public void a_game_with_more_players_than_maximum_allowed() throws MinimumPlayersException, MinimumQuestionsException, MaximumPlayersExcededException {
		
		setUpPlayersToPlay(7);
		
	}
	
	@Test
	public void player_one_wins() throws MaximumPlayersExcededException{
		setUpPlayersToPlay(2);
		players.addPurseTo(0);
		players.addPurseTo(0);
		players.addPurseTo(0);
		players.addPurseTo(0);
		players.addPurseTo(0);
		players.addPurseTo(0);
		assertEquals(true, players.didPlayerWin(0));
		
	}
	
	@Test
	public void player_is_not_still_winner() throws MaximumPlayersExcededException{
		setUpPlayersToPlay(2);
		players.addPurseTo(0);
		assertEquals(false,players.didPlayerWin(0));
	}
	
	private Players setUpPlayersToPlay(int numberOfPlayers) throws MaximumPlayersExcededException{
		for (int player=1;player<=numberOfPlayers;player++){
			players.addPlayer("Player".concat(String.valueOf(player)));
		}
		return players;
	}

	
}
