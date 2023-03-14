/*******************************************
 * Name: Jerry Liang
 * Course: CS170-01
 * Lab#: Project (AlphabetGuy)
 * Submission Date: 10:00 pm, Wed (12/8)
 * Brief Description: The code for the Word class.
 * 	Contains data for words, such as array form
 * 	of word and image associated with word.
 ********************************************/
public class Word {			//Word class
	private String word;	//String variable of word
	private String image;	//string variable for image file name
	
	public Word(String word, String imageFile) {	//constructor
		this.word = word;
		image = imageFile;
	} //end constructor
	
	public String[] getArray() {	//get array form of word
		return word.split("");
	} //end getArray()
	
	public String getImage() {		//accessor method for image file name
		return image;
	} //end getImage()
} //end Word class
