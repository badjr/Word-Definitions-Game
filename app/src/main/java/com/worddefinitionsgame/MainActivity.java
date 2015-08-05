package com.worddefinitionsgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MainActivity extends Activity implements OnClickListener {
	
//	String word[];
//	String def[];
	
	static List<String> Word = new ArrayList<String>();
	static List<String> Def = new ArrayList<String>();
	
//	int numWords;
	int correctWordIndex;
	
	String FILENAME = "e3.txt";
	
	TextView defTextView;
	TextView statusTextView;
	TextView currentFileTextView;
	Button button[];
	Random r = new Random(); //Used for the 4 buttons.
	int correctButtonNumber;
	
	boolean guessingEmAll = true;
	
	WordsModel wordsModel; //Manipulates the words
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        currentFileTextView = (TextView) findViewById(R.id.currentFileNameTV);
		defTextView = (TextView) findViewById(R.id.definitionView);
		statusTextView = (TextView) findViewById(R.id.statusView);
		button = new Button[4];
		button[0] = (Button) findViewById(R.id.button0);
		button[1] = (Button) findViewById(R.id.button1);
		button[2] = (Button) findViewById(R.id.button2);
		button[3] = (Button) findViewById(R.id.button3);
		
		defTextView.setTextSize(18);
		defTextView.setMovementMethod(new ScrollingMovementMethod());
		
		for (int i = 0; i < 4; i++) {
			button[i].setOnClickListener(this);
		}

//		String FILENAME = "grealt.txt";
//		StringBuilder sb = new StringBuilder();
//		String currLine = "";
		
		wordsModel = new WordsModel(getApplicationContext());
		
//		wordsModel.loadFile(getAssets().open(FILENAME));
		wordsModel.loadFile(FILENAME);
		loadGame();
//		loadFile(FILENAME);
//		loadNextWord();

	}
	
	public void loadGame() {
		currentFileTextView.setText("Current file: " + FILENAME + ". There's " + wordsModel.getNumWords() + " words.");
		loadNextWord();
	}
	
	public void loadNextWord() {
		if (wordsModel.getNumWords() == 0) {
			wordsModel.loadFile(FILENAME);
		}
		wordsModel.nextWord();
		
		defTextView.setText(wordsModel.getCurrentDef());
		
		correctButtonNumber = r.nextInt(4);
		button[correctButtonNumber].setText(wordsModel.getCurrentWord());
		
		String correctWord = wordsModel.getCurrentWord();
		String randGeneratedWord = wordsModel.getRandomWord();
		
		for (int i = 0; i < 4; i++) {
			if (i != correctButtonNumber) {
//				while (correctWord == randGeneratedWord) {
//					randGeneratedWord = wordsModel.getRandomWord();
//				}
				randGeneratedWord = wordsModel.getRandomWord();
				while (randGeneratedWord == correctWord) {
					randGeneratedWord = wordsModel.getRandomWord();
				}
//				button[i].setText(wordsModel.getRandomWord());
				button[i].setText(randGeneratedWord);
			}
		}
		
	}
	
	public void onClick(View v) {
		
		Button b = (Button) v;
	    String buttonText = b.getText().toString();
	    if (
//	    		buttonText.compareTo(word[correctWordIndex]) == 0
//	    		buttonText.compareTo(Word.get(correctWordIndex)) == 0
	    		buttonText.compareTo(wordsModel.getCurrentWord()) == 0
	    		) {
	    	statusTextView.setText(buttonText + " was the correct answer!");
	    	
	    	if (guessingEmAll) {
	    		wordsModel.removeWord();
	    		statusTextView.append(" " + wordsModel.getNumWords() + " words left!");
	    	}
	    	
	    	try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	loadNextWord();
	    }
	    else {
	    	statusTextView.setText(buttonText + " is the wrong answer. Try again...");
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String[] fileList = null;
		AssetManager aMan = this.getAssets();
		try {
			fileList = aMan.list("");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String fileListString = "";
		for (int i = 0; i < fileList.length; i++) {
			fileListString += fileList[i] + "; ";
		}
		
//		Toast.makeText(getApplicationContext(), file,
//				Toast.LENGTH_LONG).show();
		
		
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.file_selection:
//			Toast.makeText(getApplicationContext(), "You selected the menu.",
//					Toast.LENGTH_LONG).show();
			
			final EditText input = new EditText(this);
			
//			final String fileName;
			
			new AlertDialog.Builder(this)
		    .setTitle("File Selection")
		    .setMessage("Choose a file: " + fileListString)
		    .setView(input)
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            Editable value = input.getText();
		            Toast.makeText(getApplicationContext(), "Your file: " +
		            				value.toString(), Toast.LENGTH_LONG).show();
//		            loadFile(value.toString());
		        }
		    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            // Do nothing.
		        }
		    }).show();
			
//			Toast.makeText(getApplicationContext(), value,
//					Toast.LENGTH_LONG).show();
			
			
			return true;
			// case R.id.help:
			// showHelp();
			// return true;
		case R.id.game_mode:
			AlertDialog.Builder ad = new AlertDialog.Builder(this);
			LinearLayout linearLayout = new LinearLayout(this);
//			RadioButton rb = new RadioButton(this);
//			RadioButton rb2 = new RadioButton(this);
//			
//			rb.setText("Guess Random");
//			rb2.setText("Guess 'em all");
			
			final ToggleButton tb = new ToggleButton(this);
			tb.setChecked(guessingEmAll);
//			linearLayout.addView(rb);
//			linearLayout.addView(rb2);
			linearLayout.addView(tb);
		    ad.setTitle("Select game mode").setView(linearLayout)
		    .setMessage("Guess 'em all mode is: ")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            guessingEmAll = tb.isChecked();
//		            loadFile(value.toString());
		        }
		    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            // Do nothing.
		        }
		    }).show();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}

