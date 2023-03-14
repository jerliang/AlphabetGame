/*******************************************
 * Name: Jerry Liang
 * Course: CS170-01
 * Lab#: Project (AlphabetGuy)
 * Submission Date: 10:00 pm, Wed (12/8)
 * Brief Description: The code for the AlphabetGuyGamePanel
 *  class. Inherits from the JPanel class and implements
 *  the ActionListener and KeyListener interfaces. 
 *  Sets up structure of game display and objects in game.
 *  Allows the Alphabet Guy to move around in a space and
 *  collect letters.
 ********************************************/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class AlphabetGuyGamePanel extends JPanel implements ActionListener, KeyListener {	//AlphabetGuyGamePanel class
	private Timer timer = new Timer(5, this);		//Timer for Alphabet Guy movement
	private int x = 0, y = 0, xMovement = 0, yMovement = 0;	//movement coordinates for Alphabet Guy
	private Image alphabetGuy = (new ImageIcon("images/stick_figure.png")).getImage();	//Alphabet Guy image
	private Word word;				//word for Alphabet Guy to spell out
	private int[] xCoordinates;	//x-coordinates for letter placement on panel
	private int[] yCoordinates;	//y-coordinates for letter placement on panel
	private boolean[] letterCheck;	//check if each letter has been collected
	private int lettersCorrect = 0;	//check how many letters have been collected
	private Color backgroundColor;	//background color of panel
	
	public AlphabetGuyGamePanel() {		//constructor
		timer.start();			//start timer for updating Alphabet Guy movement
		addKeyListener(this);	//allow arrow keys to move Alphabet Guy around
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		String[] randWord = randomWord();		//get random word from file of words
		word = new Word(randWord[0], "images/" + randWord[1]);	
		xCoordinates = new int[word.getArray().length];	//assign random coordinates to each letter in the word
		yCoordinates = new int[word.getArray().length];
		letterCheck = new boolean[word.getArray().length];
		for (int i = 0; i < word.getArray().length; i++) {
			xCoordinates[i] = randomX();
			yCoordinates[i] = randomY();
			letterCheck[i] = false;			//set initial collected status for each letter to false
		}
		backgroundColor = Color.white;		//set background panel color to white
	} //end constructor 
	
	public void paint(Graphics g) {				//panel animations
		super.paint(g);							//update placement of objects in game panel
		Graphics2D newG = (Graphics2D) g;
		newG.drawImage(alphabetGuy, x, y, this);
		
		newG.setFont(new Font("Comic Sans MS", Font.BOLD, 50));	//set font for letters on panel
        newG.setColor(Color.black);
        this.setBackground(backgroundColor);		//set background color
        for (int i = 0; i < word.getArray().length; i++) {		//redraw letters in current positions if they are not collected
        	if (letterCheck[i] == false) {
            	newG.drawString(word.getArray()[i], xCoordinates[i], yCoordinates[i]);
        	}
        } //end loop
        
        if (lettersCorrect != word.getArray().length) {	//remove collected letter from screen
	        if ((x >= xCoordinates[lettersCorrect] - 30 && x <= xCoordinates[lettersCorrect] + 30)	//increase range for collecting letters
	       			&& (y >= yCoordinates[lettersCorrect] - 30 && y <= yCoordinates[lettersCorrect] + 30)) {
	       		letterCheck[lettersCorrect] = true;
	       		lettersCorrect += 1;
	       		backgroundColor = randomColor();	//change background color when letter is collected
	       	}
        } //end if
       
	} //end paint()
	
	public void actionPerformed(ActionEvent e) {		//check for events 
		if (lettersCorrect <= word.getArray().length) {	//allow for animations while player has not finished game
			repaint();
			x += xMovement;
			y += yMovement;
		}
	} //end actionPerformed()
	
	public void keyPressed(KeyEvent e) {		//allow arrow keys to move Alphabet Guy
		int keyCode = e.getKeyCode();
		if (lettersCorrect < word.getArray().length) {
			if (keyCode == KeyEvent.VK_UP) {		//up movement
				xMovement = 0;
				yMovement = -1;
			}
			if (keyCode == KeyEvent.VK_DOWN) {		//down movement
				xMovement = 0;
				yMovement = 1;
			}
			if (keyCode == KeyEvent.VK_LEFT) {		//left movement
				xMovement = -1;
				yMovement = 0;
			}
			if (keyCode == KeyEvent.VK_RIGHT) {		//right movement
				xMovement = 1;
				yMovement = 0;
			}
		}
	} //end keyPressed()

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {			//stop Alphabet Guy movement when arrow keys are released
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP || code == KeyEvent.VK_DOWN) {		//stop y-axis movement
			yMovement = 0;
		}
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {	//stop x-axis movement
			xMovement = 0;
		}
	} //end keyReleased()
	
	public int randomX() {		//get random x-coordinate
		return 50 + (int)(Math.random()*640);
	} //end randomX()
	
	public int randomY() {		//get random y-coordinate
		return 50 + (int)(Math.random()*480);
	} //end randomY()
	
	public Color randomColor() {	//get random color
		Color color = new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
		return color;
	} //end randomColor()
	
	public String[] randomWord() {	//get random word from file of words
		try {
			File myFile = new File("words.txt");	//open words file
			BufferedReader in = new BufferedReader(new FileReader(myFile));
			ArrayList<String> words = new ArrayList<String>();	//store words in ArrayList
			String line = in.readLine();
			while (line != null) {
				words.add(line);
				line = in.readLine();
			}
			in.close();	//close file
			int randIndex = (int)(Math.random()*words.size());	//pick word assigned to random index in ArrayList
			return words.get(randIndex).split(", ");
		} catch (FileNotFoundException e) {			//catch error for file
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {				//catch error for reading file
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} //end randomWord()
	
	public int getLettersCorrect() {	//get number of collected letters
		return lettersCorrect;
	} //end getLettersCorrect()
	
	public int getWordLength() {		//get length of word
		return word.getArray().length;
	} //end getWordLength()
	
	public String getWordImage() {		//get image associated with word
		return word.getImage();
	} //end getWordImage()
	
	public String[] getWordArray() {	//get word as array
		return word.getArray();
	} //end getWordArray()
} //end AlphabetGuyGamePanel class
