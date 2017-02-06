package engine.ReadWrite;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author Daniel Amos Grenehed
 */
public abstract class Write {

	/**
	 * creates an image file with the given image as content and the filename as
	 * name
	 * 
	 * @param image,
	 *            filename
	 */
	public static void writeImage(BufferedImage image, String filename) {

		FileWriter fw = null;
		try {
			fw = new FileWriter(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter utFil = new PrintWriter(bw);
		utFil.print(image);
		utFil.flush();
		utFil.close();
		try {
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * creates a file with the given string array content and the name of
	 * filename
	 * 
	 * @param content,
	 *            filename
	 */
	public static void writeStringArray(String[] content, String filename) {

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			for (int i = 0; i < content.length; i++) {
				if (content[i].equals("") | content[i].equals(null))
					;
				else {
					bw.write(content[i]);
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
