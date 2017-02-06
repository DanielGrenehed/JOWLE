package engine.ReadWrite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Daniel Amos Grenehed
 */
public abstract class Read {

	/**
	 * Creates and returns an image from the given filename
	 * 
	 * @param fileName
	 * @return BufferedImage
	 */
	public static BufferedImage readImage(String fileName) {
		File file = new File(fileName);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Could not Read file : "+fileName);
			e.printStackTrace();
		}

		return image;
	}

	public static BufferedImage readImage(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * Creates and returns an array of strings from the given filename
	 * 
	 * @param fileName
	 * @return String[]
	 */
	public static String[] ReadText(String fileName) {
		String[] content = null;
		try {
			Scanner sc = new Scanner(new File(fileName));
			ArrayList<String> lines = new ArrayList<String>();
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
			content = lines.toArray(new String[0]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String s : content) {
			System.out.println(s);
		}
		return content;
	}

	/**
	 * opens creates and returns an audio clip from the given filename
	 * 
	 * @href https://www3.ntu.edu.sg/home/ehchua/programming/java/
	 *       J8c_PlayingSound.html
	 * @param filename
	 * @return Clip
	 */
	public static Clip getAudio(String filename) {
		File soundFile = new File(filename);
		AudioInputStream audioIn = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Get a sound clip resource.

		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clip;
	}

}
