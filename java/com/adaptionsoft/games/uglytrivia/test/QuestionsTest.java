package com.adaptionsoft.games.uglytrivia.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.adaptionsoft.games.uglytrivia.Messages;
import com.adaptionsoft.games.uglytrivia.Questions;

public class QuestionsTest {

	Questions questions;
	
	@Before
	public void SetUp(){
		questions = new Questions(50);
	}
	
	@Test
	public void ask_type_empty(){
		assertEquals("",questions.askQuestion(""));
	}
	
	@Test
	public void create_fifty_questions(){
		assertEquals(50, questions.getNumberQuestions());
	}
	
	@Test
	public void ask_pop_question(){
		assertEquals("Pop Question 0",questions.askQuestion(Messages.getString("Text.14")));
		assertEquals("Pop Question 1",questions.askQuestion(Messages.getString("Text.14")));
	}
	
	@Test
	public void ask_science_question(){
		assertEquals("Science Question 0",questions.askQuestion(Messages.getString("Text.15")));
	}
	
	@Test
	public void ask_sports_question(){
		assertEquals("Sports Question 0",questions.askQuestion(Messages.getString("Text.16")));
	}
	
	@Test
	public void ask_rock_question(){
		assertEquals("Rock Question 0",questions.askQuestion(Messages.getString("Text.17")));
	}
	
}
