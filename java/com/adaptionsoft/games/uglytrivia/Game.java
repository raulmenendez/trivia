package com.adaptionsoft.games.uglytrivia;

import java.util.HashMap;
import java.util.Random;



public class Game {	

	Questions questions;
    Players players;
    private HashMap<String, Integer> playersScores = new HashMap<String, Integer>();
	int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	public boolean isPlayable() throws MinimumPlayersException, MinimumQuestionsException, MaximumPlayersExcededException {
		if (players.getNumberOfPlayers() < Players.MIN_NUMBER_PLAYERS) throw new MinimumPlayersException();
		if (questions.getNumberQuestions() < Questions.MIN_NUMBER_QUESTIONS) throw new MinimumQuestionsException();
		return true;
	}

	public int rollTheDice(Random dice){
		return (dice.nextInt(5) + 1);
	}

	public boolean isPlayerInThePenaltyBox(){
		return players.getInPenaltyBox()[currentPlayer];			
	}	
	
	public boolean isPlayerGettingOutFromPenaltyBox(int roll){
		return (roll % 2 != 0);
	}

	public void turn(int diceResult) {
		
		if (isPlayerInThePenaltyBox()){
			if (isPlayerGettingOutFromPenaltyBox(diceResult)){				
				isGettingOutOfPenaltyBox = true;
				play(diceResult);
			} else {
				isGettingOutOfPenaltyBox = false;
			}
		}else{			
			play(diceResult);
		}
		
	}

	public void play(int roll) {
		movePlaces(roll);
		questions.askQuestion(currentCategory());
	}

	private void movePlaces(int roll) {
		players.getPlaces()[currentPlayer] = placeFrom(currentPlayer) + roll;
		if (placeFrom(currentPlayer) > 11){ 
			players.getPlaces()[currentPlayer] = placeFrom(currentPlayer) - 12;
		}
	}

	private int placeFrom(int currentPlayer) {
		return players.getPlaces()[currentPlayer];
	}
	
	// randomly return a category
	private String currentCategory() {
		if (placeFrom(currentPlayer) == 0) return Messages.getString("Text.14"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 4) return Messages.getString("Text.14"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 8) return Messages.getString("Text.14"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 1) return Messages.getString("Text.15"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 5) return Messages.getString("Text.15"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 9) return Messages.getString("Text.15"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 2) return Messages.getString("Text.16"); //$NON-NLS-1$
		if (placeFrom(currentPlayer)== 6) return Messages.getString("Text.16"); //$NON-NLS-1$
		if (placeFrom(currentPlayer) == 10) return Messages.getString("Text.16"); //$NON-NLS-1$
		return Messages.getString("Text.17"); //$NON-NLS-1$
	}

	private boolean hasPlayerWon(){
		System.out.println(Messages.getString("Text.29")); //$NON-NLS-1$
		players.addPurseTo(currentPlayer);
		System.out.println(players.get(currentPlayer) 
				+ Messages.getString("Text.30") //$NON-NLS-1$
				+ players.getPurses()[currentPlayer]
				+ Messages.getString("Text.31")); //$NON-NLS-1$
		
		playersScores.put(players.get(currentPlayer),players.getPurses()[currentPlayer]);
		
		boolean hasPlayerWon = players.didPlayerWin(currentPlayer);
		if (!hasPlayerWon){
			currentPlayer = nextPlayerToPlay();
		}
		return hasPlayerWon;
	}

	private int nextPlayerToPlay() {
		currentPlayer++;
		if (currentPlayer == players.getNumberOfPlayers()){ 
			currentPlayer = 0;
		}
		return currentPlayer;
	}
	
	public boolean wasCorrectlyAnswered() {
		if (players.getInPenaltyBox()[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				return hasPlayerWon();
			} else {
				currentPlayer = nextPlayerToPlay();
				return false;
			}			
		} else {				
			return hasPlayerWon();
		}
	}
	
	public boolean wasIncorrectlyAnswered(){
		System.out.println(Messages.getString("Text.35")); //$NON-NLS-1$
		System.out.println(players.get(currentPlayer)+ Messages.getString("Text.36")); //$NON-NLS-1$
		players.getInPenaltyBox()[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.getNumberOfPlayers()) currentPlayer = 0;
		return false;
	}
    
	public void setPlayers(Players players) {
		this.players = players;
	}
	
	public HashMap<String, Integer> getPlayersScores(){
		return playersScores;
	}
	
	public int getScoreFrom(String player){
		return this.playersScores.get(player).intValue();
	}
	
	public void setQuestions(Questions questions){
		this.questions = questions;
	}
}
