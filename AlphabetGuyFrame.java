/*******************************************
 * Name: Jerry Liang
 * Course: CS170-01
 * Lab#: Project (AlphabetGuy)
 * Submission Date: 10:00 pm, Wed (12/8)
 * Brief Description: The code for the AlphabetGuyFrame
 * 	class. Inherits from the JFrame class. Opens
 * 	window for user to enter name and play the game.
 ********************************************/
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class AlphabetGuyFrame extends JFrame {		//AlphabetGuyFrame class
	Toolkit tk = Toolkit.getDefaultToolkit();	//get screen dimensions
	Dimension dimension = tk.getScreenSize();
	String player = "";							//store name of player
	
	public AlphabetGuyFrame() {			//constructor
		player = JOptionPane.showInputDialog(null, "Enter player name: ", "WELCOME TO ALPHABET GUY", JOptionPane.INFORMATION_MESSAGE);		//ask for player's name
		setTitle("ALPHABET GUY");		//set title of game window
		setSize(3*dimension.width/4, 3*dimension.height/4);		//set size of game window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	
		JPanel panel = new AlphabetGuyMainPanel(player);		//add game functionality to window
		this.add(panel);
		panel.setFocusable(true);
		setLocation((dimension.width-this.getWidth())/2, (dimension.height-this.getHeight())/2);	//place game window in the middle of the screen
	} //end constructor
} //end AlphabetGuyFrame class
