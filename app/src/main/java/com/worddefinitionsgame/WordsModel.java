package com.worddefinitionsgame;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordsModel {
	private static List<String> Word = new ArrayList<String>();
//	private static List<String> completeWordList = new ArrayList<String>(); //Keep complete words list for the guess 'em all game.
	private static List<String> completeWordList; //Keep complete words list for the guess 'em all game.
	private static List<String> Def = new ArrayList<String>();
	
	private int numWords;
	private int correctWordIndex;
	
	private Context context; //Reference to the main activity context
	
	private static Random r = new Random();
	
	public WordsModel(Context context) {
		this.context = context;
	}
	
	public void loadFile(String fileName) {
		numWords = 0;
		
		try {
//		    BufferedReader reader = new BufferedReader(
//		        new InputStreamReader(getAssets().open(fileName)));
		    
		    BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
			  
//			Scanner s = new Scanner(new File("../../assets/grealt.txt"));
//			s = new Scanner(new File("grealt.txt"));
			
		    // do reading, usually loop until end of file reading  
		    String currLine = "";
//		    while ((currLine = reader.readLine()) != null) {
		       //process line
//		       currLine = reader.readLine(); 
//		       sb.append(currLine);
//	    	for (int i = 0; (currLine = reader.readLine()) != null; i++) {
		    while ((currLine = reader.readLine()) != null) {
//		    while ((currLine = s.nextLine()) != null) {
		    	String str[] = currLine.split(":");
//	            word[i] = str[0];
//	            def[i] = str[1].trim();
		    	Word.add(str[0]);
		    	Def.add(str[1].trim());
	            numWords++;
		    }
//		    Collections.copy(Word, completeWordList);
		    completeWordList = new ArrayList<String>(Word);
//		    s.close();
		    reader.close();
		}
		catch (IOException e) {
		    //log the exception
//			Toast.makeText(getApplicationContext(), "File IO exception",
//					Toast.LENGTH_LONG).show();
			Log.d("DBG", "File IO Exception");
		}
		
//		for	(int i = 0; i < Word.size(); i++) {
//			System.out.println(Def.get(i));
//		}
		
//		r = new SecureRandom();
		//r = new Random();
//		correctWordIndex = r.nextInt(numWords);
//		int buttonNumber;
//		currentFile.setText("Current file: " + fileName + ". There's " + numWords + " words.");
		//statusTextView.setText("There are " + numWords + " words.");
//		generateNewWord(correctWordIndex);
	}
	
	public int getNumWords() {
		return numWords;
	}
	
	/*
	 * Go to the next word.
	 */
	public void nextWord() {
		correctWordIndex = r.nextInt(numWords);
	}
	
	public String getRandomWord() {
//		return Word.get(r.nextInt(numWords));
//		return completeWordList.get(r.nextInt(numWords));
		return completeWordList.get(r.nextInt(completeWordList.size()));
	}
	
	public String getCurrentWord() {
		return Word.get(correctWordIndex);
	}
	
	public String getCurrentDef() {
		return Def.get(correctWordIndex);
	}
	
	public void removeWord() {
		Word.remove(correctWordIndex);
		Def.remove(correctWordIndex);
		numWords--;
		
	}
	

	
}

//public void generateNewWord(int correctWordIndex) {
////	defTextView.setText(def[correctWordIndex]);
//	defView.setText(Def.get(correctWordIndex));
//	correctButtonNumber = 0 + (int)(Math.random() * ((3 - 0) + 1));
//	
//	ArrayList<String> wordsUsed = new ArrayList<String>();
//	
////	wordsUsed.add(word[correctWordIndex]);
//	wordsUsed.add(Word.get(correctWordIndex));
//	
//	String grabbedWord;
//	for (int i = 0; i < 4; i++) {
////		grabbedWord = word[r.nextInt(numWords)];
//		grabbedWord = Word.get(r.nextInt(numWords));
//		while (!wordsUsed.contains(grabbedWord)) {
////			grabbedWord = word[r.nextInt(numWords)];
//			grabbedWord = Word.get(r.nextInt(numWords));
//		}
//		if (i != correctButtonNumber) {
////				button[i].setText(word[r.nextInt(numWords)]);
//			button[i].setText(Word.get(r.nextInt(numWords)));
////			button[i].setText(grabbedWord);
//		}
//	}
//	
////	button[correctButtonNumber].setText(word[correctWordIndex]);
//	button[correctButtonNumber].setText(Word.get(correctWordIndex));
//}
