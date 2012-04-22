package com.adaptionsoft.games.uglytrivia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
				System.out.println("Jugador " + e.getKey() + " " + "con resultado " + e.getValue());
			}
		}		
	}	
	
	public int getScoreFrom(String player) throws NotAValidPlayerException{
		if (this.historicInfo.containsKey(player)){
			return this.historicInfo.get(player).intValue();
		}else{
			throw new NotAValidPlayerException();
		}
	}
	
	public Historic readSerialized(){
		Historic historic = null;
		try{
			ObjectInputStream entrada=new ObjectInputStream(new FileInputStream("historic.obj"));
	        historic=(Historic)entrada.readObject();
	        historic.printResults();
		}catch (FileNotFoundException ex){
			System.out.println(ex);
			createSerialized();
		}catch (IOException ex) {
	        System.out.println(ex);
	    }catch (ClassNotFoundException ex) {
	        System.out.println(ex);	    
	    }	
	    
	    return historic;
	}
	
	public void createSerialized(){
		System.out.println("No existe historico de puntuaciones, se crea");
    	try{
    		Historic historic = new Historic();
	    	ObjectOutputStream salida=new ObjectOutputStream(new FileOutputStream("historic.obj"));
            salida.writeObject(historic);
            salida.close();
    	}catch (IOException exc) {
	        System.out.println(exc);	    
	    }		
	}
	
}
