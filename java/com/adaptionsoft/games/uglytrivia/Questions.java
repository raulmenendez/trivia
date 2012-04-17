package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Questions {

	public LinkedList<String> popQuestions = new LinkedList<String> ();
	public LinkedList<String> scienceQuestions = new LinkedList<String> ();
	public LinkedList<String> sportsQuestions = new LinkedList<String> ();
	public LinkedList<String> rockQuestions = new LinkedList<String> ();

	public Questions(){
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast(createQuestion(i,"Text.0") + i); //$NON-NLS-1$
			scienceQuestions.addLast(createQuestion(i,"Text.1") + i); //$NON-NLS-1$
			sportsQuestions.addLast(createQuestion(i,"Text.2") + i); //$NON-NLS-1$
			rockQuestions.addLast(createQuestion(i,"Text.3")); //$NON-NLS-1$
		}
	}
	
	private String createQuestion(int index, String message){
		return Messages.getString(message) + index; 
	}
	
	public LinkedList<?> getPopQuestions() {
		return popQuestions;
	}	

	public String askQuestion(String currentCategory) {
		if (currentCategory == Messages.getString("Text.14")) //$NON-NLS-1$
			return popQuestions.removeFirst().toString();
		else if (currentCategory == Messages.getString("Text.15")) //$NON-NLS-1$
				return scienceQuestions.removeFirst().toString();
		else if (currentCategory == Messages.getString("Text.16")) //$NON-NLS-1$
			return sportsQuestions.removeFirst().toString();
		else if (currentCategory == Messages.getString("Text.17")) //$NON-NLS-1$
			return rockQuestions.removeFirst().toString();
		else 
			return "";
	}

	public void setPopQuestions(LinkedList<String> popQuestions) {
		this.popQuestions = popQuestions;
	}

	public LinkedList<?> getScienceQuestions() {
		return scienceQuestions;
	}

	public void setScienceQuestions(LinkedList<String> scienceQuestions) {
		this.scienceQuestions = scienceQuestions;
	}

	public LinkedList<?> getSportsQuestions() {
		return sportsQuestions;
	}

	public void setSportsQuestions(LinkedList<String> sportsQuestions) {
		this.sportsQuestions = sportsQuestions;
	}

	public LinkedList<String> getRockQuestions() {
		return rockQuestions;
	}

	public void setRockQuestions(LinkedList<String> rockQuestions) {
		this.rockQuestions = rockQuestions;
	}
}