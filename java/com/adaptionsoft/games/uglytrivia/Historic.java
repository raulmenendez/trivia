package com.adaptionsoft.games.uglytrivia;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Historic implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, Integer> historicInfo = new HashMap<String, Integer>();
	
	public void addPlayerScore (String player, int playerScore){
		if (historicInfo.containsKey(player)){
			historicInfo.put(player, historicInfo.get(player) + playerScore);
		} else historicInfo.put(player, playerScore);
	}

	public HashMap<String, Integer> getPlayersScores() {
		return historicInfo;
	}

	public void addPlayersScores(HashMap<String, Integer> hashMap) {		
		Iterator<Entry<String, Integer>> it = hashMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> e = it.next();
			addPlayerScore(e.getKey() ,e.getValue());
		}
	}

	public void printResults() {

		if (historicInfo.size()==0) {
			System.out.println("No hay datos");
		} else {
			System.out.println("Resultados-------------------------");   
	        Iterator<Entry<String, Integer>> it = historicInfo.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Integer> e = it.next();
				System.out.println("Jugador " + e.getKey() + " " + "con resultado " + (Integer)e.getValue());
			}
		}		
	}	
	
	public int getScoreFrom(String player){
		return this.historicInfo.get(player).intValue();
	}
}
