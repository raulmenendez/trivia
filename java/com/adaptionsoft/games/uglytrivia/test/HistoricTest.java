package com.adaptionsoft.games.uglytrivia.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Historic;
import com.adaptionsoft.games.uglytrivia.NotAValidPlayerException;

public class HistoricTest {

	private Historic historic = new Historic();
	private ByteArrayOutputStream outContent;

	@Test
	public void create_and_read_serialized_historic() {
		historic = new Historic();
		historic.createSerialized();
		historic.readSerialized();
		assertNotNull(historic);
	}	
	
	@Test
	public void get_players_scores_empty() {
		assertEquals(new HashMap(),historic.getPlayersScores());
	}
	
	@Test
	public void save_player_record() throws NotAValidPlayerException {
		historic.addPlayerScore("Player1",5);
		
		assertEquals(5,historic.getScoreFrom("Player1"));
	}
	
	@Test
	public void save_players_records() throws NotAValidPlayerException {
		HashMap playersScores = new HashMap();
		playersScores.put("Player1", 7);
		playersScores.put("Player2", 10);
		playersScores.put("Player3", 4);
		
		historic.addPlayersScores(playersScores);
		
		assertEquals(4,historic.getScoreFrom("Player3"));
	}
	
	@Test (expected=NotAValidPlayerException.class)
	public void get_players_scores_that_not_exist() throws NotAValidPlayerException{
		historic.addPlayerScore("Player1",5);		
		historic.getScoreFrom("Player2");
	}
	
	@Test
	public void save_player_record_and_add_result() throws NotAValidPlayerException {
		historic.addPlayerScore("Player1",5);
		historic.addPlayerScore("Player1",5);
		
		assertEquals(10,historic.getScoreFrom("Player1"));
	}		

	@Test
	public void save_two_players_record_and_add_two_results_for_each_one() throws NotAValidPlayerException {
		historic.addPlayerScore("Player1",5);
		historic.addPlayerScore("Player2",6);
		historic.addPlayerScore("Player1",7);
		historic.addPlayerScore("Player2",10);
		
		assertEquals(12,historic.getScoreFrom("Player1"));
		assertEquals(16,historic.getScoreFrom("Player2"));
	}
	
	@Test
	public void print_without_records() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		historic.printResults();
		
		assertEquals(true,outContent.toString().contains("No hay datos"));
	}
	
	@Test
	public void save_results_and_print() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));		
		historic.addPlayerScore("Player1",5);
		historic.addPlayerScore("Player2",6);
		historic.addPlayerScore("Player2",10);
		historic.printResults();
		
		assertEquals(true,outContent.toString().contains("Jugador Player1 con resultado 5"));
		assertEquals(true,outContent.toString().contains("Jugador Player2 con resultado 16"));
	}
	
}
