package engine.Window;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * @author Daniel Amos Grenehed
 * 
 */
public class WindowOptions {

	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	private int WIDTH, HEIGHT;

	private String screenFormat;

	private boolean borderless;

	/**
	 * sets the size to the screen format with the width as specified if its not
	 * larger than your screen's width and height as formatted by the width
	 * 
	 * @param width
	 * 
	 */
	public void setAsScreenFormat(int width) {

		width = setAcceptableWidth(width);
		this.WIDTH = width;
		this.HEIGHT = (int) (width * formatHeight());
		this.fullscreen = false;
		this.setScreenFormat(getScreenFormat());
	}

	/**
	 * 
	 * returns a width that is no bigger than your screen's width
	 * @param width
	 */
	public int setAcceptableWidth(int width) {
		double w = gd.getDisplayMode().getWidth();
		if (width > w) {
			System.out.println(
					"Cannot set window-width bigger than screen width! \nWindow size set to : " + (int) w + "px width");
			return (int) w;
		} else {
			return width;
		}
	}

	/**
	 * 
	 * Sets the size to custom width and height without questions
	 * @param width, height
	 * 
	 */
	public void setCustomSize(int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.fullscreen = false;
		this.setScreenFormat("CustomFormat");
	}

	/**
	 * sets the screen to the given width as width and determines from the given
	 * format what the height should be from the given format
	 * 
	 * @param format,
	 *            width that are accepted : "16:9", "5:4", "4:3", "5:3", "8:5",
	 *            "21:9", "17:9", "16:4" sets the width as width and the height
	 *            as formatted by the width
	 */
	public void setCustomScreenFormat(int width, String format) {

		width = setAcceptableWidth(width);

		int nmb = -1;
		for (int i = 0; i < formats.length; i++) {
			if (formatNames[i].equals(format)) {
				nmb = i;
				i = formats.length;
			}
		}
		if (nmb > -1) {
			this.WIDTH = width;
			this.HEIGHT = (int) (width * formats[nmb]);
			this.setScreenFormat(format);
		} else {
			System.out.println("Could not find format called " + format + "!");
			this.WIDTH = width;
			this.HEIGHT = (int) (width * formatHeight());
			this.setScreenFormat(getScreenFormat());

		}
		this.fullscreen = false;
	}

	double[] formats = { 0.5625, 0.8, 0.75, 0.6, 0.625, 0.42857142857142857142857142857143,
			0.52941176470588235294117647058824, 0.25 };
	String[] formatNames = { "16:9", "5:4", "4:3", "5:3", "16:10", "21:9", "17:9", "16:4" };

	/**
	 * 
	 * returns an array with all formats available
	 * 
	 */
	public String[] getFormatNames() {
		return this.formatNames;
	}

	/**
	 * 
	 * Sets the width and height equal to that of your screen
	 * 
	 */
	public void setFullscreen() {
		this.WIDTH = (int) (gd.getDisplayMode().getWidth());
		this.HEIGHT = (int) (gd.getDisplayMode().getHeight());
		setFullscreen(true);
		this.setScreenFormat(this.getScreenFormat() + " fullscreen");
	}

	private boolean fullscreen;

	/**
	 * 
	 * Returns what format your screen is
	 * 
	 */
	public String getScreenFormat() {
		double fh = formatHeight();

		for (int i = 0; i < formats.length; i++) {
			if (fh == formats[i]) {
				return formatNames[i];
			}
		}
		return "Could not detect screen format!";
	};

	/**
	 * 
	 * Returns the number of what you should time your screen width with to get
	 * your screen height to get screen with the same format as your screen
	 * 
	 */
	public double formatHeight() {

		double width = gd.getDisplayMode().getWidth();
		double height = gd.getDisplayMode().getHeight();

		return height / width;
	}

	/**
	 * Returns the width of the game window
	 * 
	 * @return int
	 */
	public int getWIDTH() {
		return WIDTH;
	}

	/**
	 * sets the width of the game window
	 * 
	 * @param wIDTH
	 */
	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	/**
	 * Returns the height of the game window
	 * 
	 * @return int
	 */
	public int getHEIGHT() {
		return HEIGHT;
	}

	/**
	 * sets the height of the game window
	 * 
	 * @param
	 */
	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	/**
	 * 
	 * @return fullscreen
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * enables/disables fullscreen mode
	 * 
	 * @param fullscreen
	 */
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
		this.setBorderless(fullscreen);
	}

	/**
	 * sets the format of the screen to the given format
	 * 
	 * @param ScreenFormat
	 */
	public void setScreenFormat(String ScreenFormat) {
		this.screenFormat = ScreenFormat;
		System.out.println("Format set to " + this.screenFormat);
	}

	/**
	 * 
	 * @return borderless
	 */
	public boolean isBorderless() {
		return borderless;
	}

	/**
	 * enables/disables borderless mode
	 * 
	 * @param borderless
	 */
	public void setBorderless(boolean borderless) {
		this.borderless = borderless;
	}
}
