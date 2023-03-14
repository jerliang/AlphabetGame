/*******************************************
 * Name: Jerry Liang
 * Course: CS170-01
 * Lab#: Project (AlphabetGuy)
 * Submission Date: 10:00 pm, Wed (12/8)
 * Brief Description: The code for the MusicPlayer class.
 * 	Allows sounds to be played during game.
 ********************************************/
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlayer {		//MusicPlayer class
	public void playSound(String soundLocation) {		//play sound with given file name
		try {
			File soundPath = new File(soundLocation);		//open sound file
			if (soundPath.exists()) {		//play sound if available
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
		} catch (Exception e) {		//catch errors
			System.out.println(e);
		}
	} //end playSound()
} //end MusicPlayer class
