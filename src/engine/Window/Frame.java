package engine.Window;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

/**
 * @author Daniel Amos Grenehed
 */
public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	/**
	 * constructor for this Frame JFrame thingy
	 * */
	public Frame(String Title, boolean borderless) {
		super(Title);
		System.out.println(getOsName());
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		if (borderless) {
			this.setUndecorated(true);
			if (isMac()) {
			}
			// gd.setFullScreenWindow(this);
		} else {
			this.setUndecorated(false);
		}
		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private static String OS = null;

	/**
	 * gets the os name of the system this engine is running on
	 * @return String
	 * */
	public static String getOsName() {
		if (OS == null) {
			OS = System.getProperty("os.name");
		}
		return OS;
	}

	/**
	 * determines whether or not the system this engine is running on is a
	 * windows computer or not
	 * 
	 * @return boolean
	 */
	public static boolean isWindows() {
		return getOsName().startsWith("Windows");
	}

	/**
	 * determines whether or not the system this engine is running on is a Mac or
	 * not
	 * 
	 * @return boolean
	 */
	public static boolean isMac() {
		return getOsName().startsWith("Mac");
	}
}
