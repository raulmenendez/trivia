package com.adaptionsoft.games.uglytrivia;

import java.util.Random;

public class Game {
	
	//Esto debería llegar por factoria
    Questions questions = new Questions();
    Players players;
	int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	public boolean isPlayable() {
		return (players.howManyPlayers() >= 2);
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

	public void roll(int diceResult) {
		
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
	
  public static void main(String[] args) {
    System.out.println(Messages.getString("Text.18")); // Display the string. //$NON-NLS-1$
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

	public boolean wasCorrectlyAnswered() {
		if (isPlayerInThePenaltyBox()){
			if (isGettingOutOfPenaltyBox) {
				System.out.println(Messages.getString("Text.29")); //$NON-NLS-1$
				players.getPurses()[currentPlayer]++;
				System.out.println(players.getPlayers().get(currentPlayer) 
						+ Messages.getString("Text.30") //$NON-NLS-1$
						+ players.getPurses()[currentPlayer]
						+ Messages.getString("Text.31")); //$NON-NLS-1$
				
				boolean winner = players.didPlayerWin(currentPlayer);
				currentPlayer = nextPlayerToPlay();
				return winner;
			} else {
				currentPlayer = nextPlayerToPlay();
				return true;
			}
			
		} else {
		
			System.out.println(Messages.getString("Text.29")); //$NON-NLS-1$
			players.getPurses()[currentPlayer]++;
			System.out.println(players.getPlayers().get(currentPlayer) 
					+ Messages.getString("Text.30") //$NON-NLS-1$
					+ players.getPurses()[currentPlayer]
					+ Messages.getString("Text.31")); //$NON-NLS-1$
			
			boolean winner = players.didPlayerWin(currentPlayer);
			currentPlayer = nextPlayerToPlay();
			return winner;
		}
	}

	private int nextPlayerToPlay() {
		currentPlayer++;
		if (currentPlayer == players.howManyPlayers()){ 
			currentPlayer = 0;
		}
		return currentPlayer;
	}
	
	public boolean wrongAnswer(){
		System.out.println(Messages.getString("Text.35")); //$NON-NLS-1$
		System.out.println(players.getPlayers().get(currentPlayer)+ Messages.getString("Text.36")); //$NON-NLS-1$
		players.getInPenaltyBox()[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.getPlayers().size()) currentPlayer = 0;
		return true;
	}
    
	public Players getPlayers() {
		return players;
	}

	public void setPlayers(Players players) {
		this.players = players;
	}	
}
