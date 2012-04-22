package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
	
	private static int MAX_NUMBER_PLAYERS = 6;
	private ArrayList<String> players = new ArrayList<String>() ;
	private int[] places = new int[MAX_NUMBER_PLAYERS];
	private int[] purses = new int[MAX_NUMBER_PLAYERS];
	private boolean[] inPenaltyBox = new boolean[MAX_NUMBER_PLAYERS];

	public boolean addPlayer(String playerName) throws MaximumPlayersExcededException {		
		
		if (players.size()<MAX_NUMBER_PLAYERS){ 
		    getPlayers().add(playerName);
		    setUpPlayer(players.size()-1);
		    System.out.println(playerName + Messages.getString("Text.4")); //$NON-NLS-1$
		    System.out.println(Messages.getString("Text.5") + getPlayers().size()); //$NON-NLS-1$
		}else{
			throw new MaximumPlayersExcededException();
		}
		return true;
		
	}

	private void setUpPlayer(int numberOfPlayer) {
		places[numberOfPlayer] = 0;
		purses[numberOfPlayer] = 0;
		inPenaltyBox[numberOfPlayer] = false;
	}

	public int getNumberOfPlayers() {
		return getPlayers().size();
	}
	
	/**
	 * Tells if the last player won.
	 */
	public boolean didPlayerWin(int currentPlayer) {
		return (this.purses[currentPlayer] == MAX_NUMBER_PLAYERS);
	}

	public ArrayList<String> getPlayers() {
		return this.players;
	}

	public int[] getPlaces() {
		return this.places;
	}

	public int[] getPurses() {
		return this.purses;
	}

	public void addPurseTo(int currentPlayer){
		this.purses[currentPlayer]++;
	}
	
	public boolean[] getInPenaltyBox() {
		return inPenaltyBox;
	}

}