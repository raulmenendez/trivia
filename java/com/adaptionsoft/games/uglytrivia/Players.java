package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;

public class Players {
	
	private int number_of_players = 6;
	private ArrayList<String> players = new ArrayList<String>() ;
	private int[] places = new int[number_of_players];
	private int[] purses = new int[number_of_players];
	private boolean[] inPenaltyBox = new boolean[number_of_players];

	public boolean addPlayer(String playerName) {		
		
	    getPlayers().add(playerName);
	    getPlaces()[howManyPlayers()] = 0;
	    getPurses()[howManyPlayers()] = 0;
	    getInPenaltyBox()[howManyPlayers()] = false;
	    
	    System.out.println(playerName + Messages.getString("Text.4")); //$NON-NLS-1$
	    System.out.println(Messages.getString("Text.5") + getPlayers().size()); //$NON-NLS-1$
		return true;
	}

	public int howManyPlayers() {
		return getPlayers().size();
	}
	
	/**
	 * Tells if the last player won.
	 */
	public boolean didPlayerWin(int currentPlayer) {
		return !(getPurses()[currentPlayer] == getNumber_of_players());
	}
	
	public int getNumber_of_players() {
		return number_of_players;
	}

	public ArrayList<String> getPlayers() {
		return players;
	}

	public int[] getPlaces() {
		return places;
	}

	public int[] getPurses() {
		return purses;
	}


	public boolean[] getInPenaltyBox() {
		return inPenaltyBox;
	}

}