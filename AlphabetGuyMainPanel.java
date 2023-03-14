/*******************************************
 * Name: Jerry Liang
 * Course: CS170-01
 * Lab#: Project (AlphabetGuy)
 * Submission Date: 10:00 pm, Wed (12/8)
 * Brief Description: The code for the AlphabetGuyMainPanel
 * 	class. Inherits from the JPanel class and 
 * 	implements the ActionListener and KeyListener interfaces.
 * 	Sets up structure of main menu and button functionality.
 ********************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;

@SuppressWarnings("serial")
public class AlphabetGuyMainPanel extends JPanel implements ActionListener, KeyListener {	//AlphabetGuyMainPanel class
	
	//main panel features
	private JLabel timeLabel;		//label for displaying timer
	private JLabel wordLabel;		//label for displaying current word
	private JLabel imageLabel;		//label for displaying word's image
	private Image image;			//image variable
	private JButton startButton;	//play button
	private JButton stopButton;		//stop button
	private JButton scoresButton;	//button for displaying top scores
	private JButton rulesButton;	//button for displaying game instructions
	public AlphabetGuyGamePanel gamePanel;	//panel containing main game play
	public JPanel infoPanel;		//panel for main info (player name, timer, scores/rules buttons)
	public JPanel wordPanel;		//panel for displaying word info (word, image)
	private Timer timer;			//timer object
	private int counter;			//counter for timer
	private String playerName;			//player name
	private boolean gamePlayed = false;	//variable to check if game is in session
	private int letterCheck = 0;	//counter for letters collected during game
	
	public AlphabetGuyMainPanel(String player) {		//constructor 
		
		this.playerName = player;
		this.setLayout(new BorderLayout());
		
		//create panel for basic info (player name, instructions, top scores)
		infoPanel = new JPanel();
		infoPanel.add(new JLabel("PLAYER: " + playerName));		//display player name
		infoPanel.add(new JLabel(" "));
		rulesButton = new JButton("HOW TO PLAY");		//add button for displaying instructions
		rulesButton.addActionListener(this);
		infoPanel.add(rulesButton);
		infoPanel.add(new JLabel(" "));
		timeLabel = new JLabel("TIME: ");		//display time for scoring
		infoPanel.add(timeLabel);
		infoPanel.add(new JLabel(" "));
		scoresButton = new JButton("TOP SCORES");	//add button for displaying top scores
		scoresButton.addActionListener(this);
		infoPanel.add(scoresButton);
		
		//create panel for game buttons (play, stop)
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));		//place buttons next to each other
		startButton = new JButton("PLAY", new ImageIcon("images/play_button.png"));		//add play button
		startButton.addActionListener(this);
		startButton.setPreferredSize(new java.awt.Dimension(100, 50));
		
		stopButton = new JButton("STOP", new ImageIcon("images/x_button.png"));		//add stop button
		stopButton.setPreferredSize(new java.awt.Dimension(100, 50));
		
		buttonPanel.add(startButton);				//add buttons to button panel, add button panel to info panel
		buttonPanel.add(stopButton);
		infoPanel.add(new JLabel(" "));
		infoPanel.add(buttonPanel);
		JPanel spacePanel = new JPanel();
		spacePanel.setSize(100, 50);
		infoPanel.add(spacePanel);
		
		//create panel for word information
		wordPanel = new JPanel();
		wordLabel = new JLabel();		//label for letters in word
		imageLabel = new JLabel();		//label for image of word
		infoPanel.add(wordPanel);
		wordPanel.setLayout(new BoxLayout(wordPanel, BoxLayout.Y_AXIS));	//set vertical structure of word panel
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));	//set vertical structure of info panel
		infoPanel.setSize(100, 480);
		this.add(infoPanel, BorderLayout.WEST);
		
		//create panel for when game is not playing (white space with no objects)
		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(Color.white);
		tempPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(tempPanel, BorderLayout.CENTER);
		
		//create timer for timing user when playing the game
		timer = new Timer(1000, this);
		timeLabel.setText("TIME: " + Integer.toString(counter));
	} //end constructor 
	
	public void actionPerformed(ActionEvent e) {		//check for events
		Object source = e.getSource();
		if (source == rulesButton) {		//display game instructions when rules button is clicked on
			JOptionPane.showMessageDialog(null, displayInstructions(), "GAME INSTRUCTIONS", JOptionPane.INFORMATION_MESSAGE);
			rulesButton.setFocusable(false);
		}
		if (source == scoresButton) {		//display top 5 scores when scores button is clicked on 
			JOptionPane.showMessageDialog(null, displayScores(), "TOP SCORES", JOptionPane.INFORMATION_MESSAGE);
			scoresButton.setFocusable(false);
		}
		if (source == startButton) {			//initiate game play
			startButton.setFocusable(false);
			stopButton.setFocusable(false);
			timer.start();					//start timer for player
			gamePanel = new AlphabetGuyGamePanel();	//create AlphabetGuyGamePanel object for user to play in
			gamePanel.setFocusable(true);
			this.addKeyListener((KeyListener) gamePanel);
			gamePanel.setBackground(Color.white);
			gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			this.add(gamePanel, BorderLayout.CENTER);
			startButton.removeActionListener(this);
			stopButton.addActionListener(this);
			gamePlayed = true;							//check if a game is in session
			image = (new ImageIcon(gamePanel.getWordImage())).getImage();	//add image of word to bottom left corner
			imageLabel.setIcon(new ImageIcon(image));
			wordPanel.add(imageLabel, BorderLayout.WEST);
			wordPanel.add(wordLabel, BorderLayout.WEST);
			wordLabel.setText("");			//reset collected letters for new word
			wordLabel.setFont(new Font("SchoolHouse Printed A", Font.BOLD, 40));
			letterCheck = 0; //reset counter for correct letters to 0
		}
		if (source == timer) {		//increment timer by 1 every second
			counter++;
			timeLabel.setText("TIME: " + Integer.toString(counter));
		}
		if (source == stopButton) {		//stop game play
			timer.stop();		//stop and reset timer 
			counter = 0;		
			timeLabel.setText("TIME: " + Integer.toString(counter));
			this.remove(gamePanel);		//remove current game panel
			startButton.addActionListener(this);
			gamePlayed = false;
			wordPanel.removeAll();		//remove current word info to prepare for next word
			imageLabel.setIcon(null);
		}
		if (gamePanel != null) {		//check if game panel exists (game is in session)
			if (gamePanel.getLettersCorrect() > letterCheck) {		//check if player collected the next letter in the sequence
				letterCheck = gamePanel.getLettersCorrect();
				String newText = "";								//update set of collected letters at the bottom left corner of window
				for (int i = 0; i < letterCheck; i++) {
					newText += gamePanel.getWordArray()[i].toUpperCase();
				}
				wordLabel.setText(newText);
				MusicPlayer musicPlayer = new MusicPlayer();		//play confirmation sound
				musicPlayer.playSound("sounds/Ding-sound-effect.wav");
			}
			if (gamePanel.getLettersCorrect() == gamePanel.getWordLength() && gamePlayed == true) {	//check if all letters have been collected for word
				timer.stop();		//stop timer
				JOptionPane.showMessageDialog(null, "Your score is " + Integer.toString(counter) + ".");
				topScoreCheck(playerName, counter);		//check if player's score is within top 5 scores
				counter = 0;	//reset timer counter
				timeLabel.setText("TIME: " + Integer.toString(counter));
				this.remove(gamePanel);		//reset game panel
				startButton.addActionListener(this);
				stopButton.removeActionListener(this);
				gamePlayed = false;
				wordPanel.removeAll();				//reset word panel
				imageLabel.removeAll();
				gamePanel.setFocusable(true);
			}
		}
	} //end actionPerformed
	
	public void topScoreCheck(String player, int counter) {		//check player's score against top 5 scores
		try {
			File myFile = new File("scores.txt");			//open file with top scores
			BufferedReader in = new BufferedReader(new FileReader(myFile));
			TreeMap<String, String> scoresMap = new TreeMap<String, String>();		//create map to store current rankings
			int rank = 1;
			String line = in.readLine();
			
			while (line != null) {		//read lines of the file into map
				scoresMap.put(String.valueOf(rank), line);
				rank++;
				line = in.readLine();
			}
			in.close();	//close file
			
			if (scoresMap.size() == 0) {			//add player's score to top scores if there are no top scores
				PrintWriter out = new PrintWriter(myFile);	//open file for writing
				out.println(player + ": " + counter);	//add player's score to file
				out.close(); //close file
			} //end if
			else if (scoresMap.size() < 5) {	//find rank for player's score among current top scores if there are less than 5 top scores
				int scoreValue = 0;	//store score
				ArrayList<String> topScoreCounters = new ArrayList<String>();	//ArrayList for storing all scores
				for (int i = 0; i < scoresMap.size(); i++) {					//add current top scores to ArrayList
					topScoreCounters.add(scoresMap.get(String.valueOf(i+1)));
				}
				for (int i = 0; i < scoresMap.size(); i++) {			//find placement of player's score among top scores
					scoreValue = Integer.valueOf(scoresMap.get(String.valueOf(i+1)).split(": ")[1]);
					if (counter < scoreValue) {		//check player's score against each score, rank player above if score is lower
						topScoreCounters.add(i, player + ": " + String.valueOf(counter));
						break;		//exit loop once player's score is added
					}
				}
				if (scoresMap.size() == topScoreCounters.size()) {	//add player's score to the end of the top scores list if all other scores are better
					topScoreCounters.add(player + ": " + String.valueOf(counter));
				}
				PrintWriter out = new PrintWriter(myFile);		//update file with ranking changes
				for (int i = 0; i < topScoreCounters.size(); i++) {
					out.println(topScoreCounters.get(i));
				}
				out.close();
			} //end else if 
			else {			//check player's score against top scores if there are 5 top scores
				int position = 0;		//store rank associated with score
				int scoreValue = 0;		//store score
				boolean newScoreAdded = false;		//check if player's score has been added to top scores (if it is possible)
				ArrayList<String> topScoreCounters = new ArrayList<String>();	//ArrayList for storing all scores
				int lowestTopScore = Integer.valueOf(scoresMap.get(scoresMap.lastKey()).split(": ")[1]); //get the current lowest top score (rank 5th)
				if (counter < lowestTopScore) {		//add player's score if it is less than current lowest top score
					for (int i = 0; i < scoresMap.size(); i++) {
						if (i < 5) {	//find the rank of player's score among top scores
							scoreValue = Integer.valueOf(scoresMap.get(String.valueOf(position+1)).split(": ")[1]);
							if (counter < scoreValue && newScoreAdded == false) {	//check player's score against all scores
								topScoreCounters.add(player + ": " + String.valueOf(counter));
								newScoreAdded = true;
								position--;		//move scores greater than player's score behind it
							}
							else {		//keep scores in current rank if lower than player's score
								topScoreCounters.add(scoresMap.get(String.valueOf(position+1)));
							}
							position++;
						} //end if
					} //end loop
					PrintWriter out = new PrintWriter(myFile);		//update file with new score rankings
					for (int i = 0; i < topScoreCounters.size(); i++) {
						out.println(topScoreCounters.get(i));
					}
					out.close();
				} //end if
			} //end else
			
		} catch (IOException e) { //catch file error
			System.out.println(e);
		} //end catch
	} //end topScoreCheck()
	
	public String displayScores() {		//display top 5 scores
		String scores = "";		//store rankings in String
		try {
			File inFile = new File("scores.txt");		//open scores file
			BufferedReader in = new BufferedReader(new FileReader(inFile));
			int rank = 1;	//variable for assigning ranks to corresponding scores
			String line = in.readLine();
			
			while (line != null) {		//read each line into String to store scores
				scores += String.valueOf(rank) + ". " + line + "\n";
				rank++;	
				line = in.readLine();
			}
			in.close();		//close file
		} catch (IOException e) {		//catch file error
			System.out.println(e);
			scores = "Unable to display top scores.";
		} //end catch block
		return scores;	//return String of top scores
	} //end displayScores()
	
	public String displayInstructions() {	//display game instructions
		return "HOW TO PLAY\n" + "Press PLAY to start. A picture will show up at the bottom left corner." +
				"\nCollect the letters on the screen in the order that the word for the object in the picture is spelled."
				+ "\nUse the arrow keys to move ALPHABET GUY to collect the letters. Scores are based on speed."
				+ "\nThere will be a confirmation sound when the correct letter has been collected."
				+ "\nTry to get short times! Press STOP to stop the game. Good luck!"
				+ "\n\nTIP: ALPHABET GUY is most sensitive around the head. Use the head to collect letters!";
	} //end displayInstructions()
	
	
	//required KeyListener methods
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
} //end AlphabetGuyMainPanel class
