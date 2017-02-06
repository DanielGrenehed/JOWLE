package engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.Clip;

import engine.Common.KeyButton;
import engine.Common.MemoryList;
import engine.Common.Settings;
import engine.Common.Template;
import engine.Developer.GraphicDebug;
import engine.Input.Key;
import engine.Input.Mouse;
import engine.Processing.AudioProcessor;
import engine.Processing.CameraProcessor;
import engine.Processing.RenderableProcessor;
import engine.ReadWrite.Classloader;
import engine.ReadWrite.Convert;
import engine.ReadWrite.Read;
import engine.ReadWrite.Write;
import engine.Render.Camera;
import engine.Render.GameCanvas;
import engine.Render.Renderable;
import engine.Window.Frame;
import engine.Window.WindowOptions;

/**
 * 
 * @author Daniel Amos Grenehed
 * 
 */
public class Main {

	ExecutorService procesExecutor;
	ExecutorService AudioExecutor;
	Queue<Clip> audio = new LinkedList<Clip>();
	private Camera currentCamera;
	KeyButton kb = new KeyButton(KeyEvent.VK_ESCAPE, this);
	Key key;
	Mouse mouse;
	Settings settings;
	Convert convert = new Convert();
	MemoryList memory = new MemoryList();
	String xmldoc = "order.xml";
	String SettingsFile = "settings.conf";
	Classloader cl = new Classloader();
	BufferedImage gameImage;
	GraphicDebug gd;
	Rectangle BoundingBox = new Rectangle();
	Frame frame;
	GameCanvas canvas;
	WindowOptions wo = new WindowOptions();
	boolean running = false;
	boolean fullscreen;
	boolean borderless;
	boolean customframesize;
	boolean Antialiasing;
	double UpdatesPerSecond = 60;
	double UpdateTime;
	private boolean LogExceptions;
	boolean developermode = false;
	final double version = 1.03;
	private double Scale = 1.0;
	long FPS = 0;
	int numberofthreads = 6, audiochannels = 16;
	int updt = 0;
	int gsleep = 15;
	int usleep = 15;
	int NumberOfCameras;
	int cameraId = 0;
	private int WIDTH = 800, HEIGHT = 500;
	double LastKey;
	String Title = "Gymnasiearbete Te13-Daniel-Grenehed " + " Version : " + version;

	/**
	 * main method, start and runs the engine and the game
	 */
	public Main() {
		this.setUpdatesPerSecond(60);
		settings = new Settings(this, convert, wo, frame);
		settings.setup();
		settings.save();
		this.BoundingBox.setSize(WIDTH, HEIGHT);
		this.BoundingBox.setLocation(0, 0);
		MouseBox.setSize(12, 20);
		gd = new GraphicDebug(this, canvas, frame);
		procesExecutor = Executors.newFixedThreadPool(numberofthreads);
		AudioExecutor = Executors.newFixedThreadPool(audiochannels);

		frame = new Frame(Title, wo.isBorderless());
		canvas = new GameCanvas(this, gd);
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		canvas.addKeyListener(key = new Key(this));
		canvas.addMouseListener(mouse = new Mouse(this));
		canvas.addMouseWheelListener(mouse);
		setCamera(cl.loadOrder(xmldoc, this, 0));
		// shutdown secure on exit button
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public synchronized void run() {
				running = false;
				try {
					gpcsThread.join();
					audioThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}));

		running = true;
		frame.requestFocus();
		canvas.requestFocus();
		gpcsThread.start();
		audioThread.start();

		double ftm = 0;
		while (running) {
			if (System.nanoTime() - ftm >= this.UpdateTime && !kb.isOn()) {
				ftm = System.nanoTime();
				try {
					for (Renderable r : currentCamera.getRenderables()) {
						try {
							procesExecutor.submit(new RenderableProcessor(r));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					procesExecutor.submit(new CameraProcessor(currentCamera));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			sleep(1);
		}
	}

	/**
	 * paints all renderable components of the current camera
	 * 
	 * @param g
	 */
	public void Render(Graphics g) {
		for (Renderable r : currentCamera.getRenderables()) {
			r.paint(g);
		}
	}

	// ///////////////////////////////////////////////////////////////

	/**
	 * queues the given audio-clip to the audio playing queue
	 * 
	 * 
	 * @param a
	 */
	public void queueAudio(Clip a) {
		audio.add(a);
	}

	Runnable audi = () -> {

		while (running) {
			while (!audio.isEmpty()) {
				AudioExecutor.submit(new AudioProcessor(audio.poll()));
			}
			sleep(5);
		}
		return;
	};
	Thread audioThread = new Thread(audi);
	// Graphics Threads

	Runnable gpcs = () -> {
		while (running) {
			canvas.render();
			moveMouseBox();
			if (kb.isOn()) {
				sleep(35);
			} else {
				sleep(3);
			}
		}
		return;
	};
	Thread gpcsThread = new Thread(gpcs);

	/**
	 * sets the thread whom called this method to sleep for the given time
	 * 
	 * @param t�me
	 */
	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param string
	 *            title as the title of the game window
	 */
	public void setTitle(String title) {
		this.frame.setTitle(title);
	}

	/**
	 * @return the tile of the game window
	 */
	public String getTitle() {
		return this.frame.getTitle();
	}

	// /////////////////////////////////////////
	/**
	 * changes the current camera to the given camera
	 * 
	 * @param camera
	 */
	public void setCamera(Camera camera) {
		currentCamera = null;
		currentCamera = camera;
		System.out.println("changed camera");
	}

	/**
	 * changes the current camera to the next camera in the camera order from
	 * the xml order document
	 */
	public void nextCamera() {
		cameraId++;
		if (cameraId > cl.OrderLength(xmldoc) - 1)
			cameraId = 0;
		setCamera(cl.loadOrder(xmldoc, this, cameraId));
	}

	/**
	 * changes the current camera to the i camera in the camera order doc,
	 * returns true if able to change camera to the i one
	 * 
	 * @param i
	 * @return boolean
	 */
	public boolean setCamera(int i) {
		if (i > cl.OrderLength(xmldoc) - 1 || i < 0)
			return false;
		else {
			setCamera(cl.loadOrder(xmldoc, this, cameraId));
			return true;
		}
	}

	/**
	 * sets the camera to the first camera from the xml document
	 */
	public void firstCamera() {
		cameraId = 0;
		setCamera(cl.loadOrder(xmldoc, this, cameraId));
	}

	/**
	 * tries to find a camera from the memory list with the same name as the
	 * provided string, returns false if unable
	 * 
	 * @param cn
	 * @return boolean
	 */
	public boolean setCameraFromMemory(String cn) {
		if (inMemory(currentCamera, cn)) {
			currentCamera = (Camera) getFromMemory(currentCamera, cn).getContent();
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////

	/**
	 * shuts down the game
	 */
	public void terminate() {
		running = false;

		frame.dispose();
		try {
			gpcsThread.join();
			audioThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}

	Rectangle MouseBox = new Rectangle();

	/**
	 * returns a rectangle with the size and position of the mouse
	 * 
	 * @return Rectangle
	 */
	public Rectangle getMouseBox() {
		return MouseBox;
	}

	/**
	 * checks if the provided rectangle intersects the mouse box
	 * 
	 * @param rect
	 * @return boolean
	 */
	public boolean isMouseOver(Rectangle rect) {
		return MouseBox.intersects(rect);
	}

	/**
	 * sets the position of the Mouse Box position to the actual position of the
	 * actual mouse.
	 */
	public void moveMouseBox() {
		MouseBox.setLocation((int) (MouseInfo.getPointerInfo().getLocation().getX() - frame.getLocation().getX()) - 2,
				(int) (MouseInfo.getPointerInfo().getLocation().getY() - frame.getLocation().getY()) - 26);
	}

	private ArrayList<Double> MousePressed = new ArrayList<Double>();

	/**
	 * checks if the mouse-key is pressed
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean isMousePressed(double key) {
		return MousePressed.contains(key);
	}

	/**
	 * returns the number of mouse buttons that is currently pressed
	 * 
	 * @return int
	 */
	public int numberOfMouseButtonsPressed() {
		return MousePressed.size();
	}

	/**
	 * adds the provided key to the MousePressed list
	 * 
	 * @param key
	 */
	public void pressMouse(double key) {
		if (!isPressed(key))
			MousePressed.add(key);
	}

	/**
	 * removes the provided mouse-key from the MousePressed list
	 * 
	 * @param key
	 */
	public void releaseMouse(double key) {
		MousePressed.remove(key);
	}

	private ArrayList<Double> KeysPressed = new ArrayList<Double>();

	/**
	 * Returns the number of Keys that is currently pressed
	 * 
	 * @return int
	 */
	public int numbersOfkeysPressed() {
		return KeysPressed.size();
	}

	/**
	 * checks if the provided key is pressed
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean isPressed(double key) {
		return KeysPressed.contains(key);
	}

	/**
	 * adds the provided key to the KeysPressed list
	 * 
	 * @param key
	 */
	public void press(double key) {
		if (!isPressed(key))
			// System.out.println(key);
			KeysPressed.add(key);
		LastKey = key;
	}

	/**
	 * @return LastKey, returns last key pressed
	 */
	public double LastKeypress() {
		return this.LastKey;
	}

	/**
	 * removes the provided key from the KeyPressed list
	 * 
	 * @param key
	 */
	public void release(double key) {
		KeysPressed.remove(key);
	}

	double Scroll = 0;

	/**
	 * adds scroll to the memory of scrollwheel action
	 * 
	 * @param double
	 *            ammount of scrolls
	 */
	public void addScroll(double n) {
		this.Scroll += n;
	}

	/**
	 * @return the ammount of scrolls that have been triggered since last call
	 *         of this method
	 */
	public double getScroll() {
		double n = this.Scroll;
		this.Scroll = 0;
		return n;
	}

	/**
	 * checks whether or not developer mode is on or off
	 * 
	 * @return boolean
	 */
	public boolean isDevelopermode() {
		return developermode;
	}

	/**
	 * inverts the y axis on canvas for easier use of things like physics
	 * 
	 * @param y
	 * @return inverted y
	 */
	public int invertedY(double y) {
		return (int) (this.getHEIGHT() - y);
	}

	/**
	 * Creates an image from renderables in render array for integration in
	 * other classes
	 * 
	 * @param burk,
	 *            WIDTH, HEIGHT
	 * @return BufferdImage
	 */
	public BufferedImage getImageFromCanvas(List<Renderable> burk, int WIDTH, int HEIGHT) {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		for (Renderable r : burk) {
			r.paint(g);
		}
		return image;
	}

	/**
	 * sets the FPS(Frames Per Seconds) of the graphics element
	 * 
	 * @param fps
	 */
	public void setFPS(long fps) {
		FPS = fps;
	}

	/**
	 * returns the WIDTH of the game-window
	 * 
	 * @return WIDTH
	 */
	public int getWIDTH() {
		return WIDTH;
	}

	/**
	 * returns the HEIGHT of the game-window
	 * 
	 * @return HEIGHT
	 */
	public int getHEIGHT() {
		return HEIGHT;
	}

	/**
	 * returns the FPS(Frames Per Seconds) of the Graphics element
	 * 
	 * @return FPS
	 */
	public long getFPS() {
		return FPS;
	}

	/**
	 * returns the bounding box of the game-window
	 * 
	 * @return Rectangle
	 */
	public Rectangle getBoundingBox() {
		return BoundingBox;
	}

	/**
	 * sets the number of audioChannels, this only works before the game is
	 * fully started
	 * 
	 * @param nt
	 */
	public void setAudioChannels(int nt) {
		this.audiochannels = nt;
	}

	/**
	 * returns the amount of threads used to play audio clips
	 * 
	 * @return audiochannels
	 */
	public int getNumberOfAudioChannels() {
		return this.audiochannels;
	}

	/**
	 * sets the number of threads used to update the game. This is only used
	 * before the game is fully started
	 * 
	 * @param nt
	 */
	public void setNumberOfThreads(int nt) {
		if (nt < 1) {
			nt = 1;
		}
		this.numberofthreads = nt;
	}

	/**
	 * returns the the amount of Threads used to update the game
	 * 
	 * @return numberOfThreads
	 */
	public int getNumberOfThreads() {
		return this.numberofthreads;
	}

	/**
	 * returns the filename of the settings file
	 * 
	 * @return String
	 */
	public String getSettingsFile() {
		return this.SettingsFile;
	}

	/**
	 * checks whether or not the game is set to fullscreen or not
	 * 
	 * @return boolean
	 */
	public boolean getFullscreen() {
		return this.fullscreen;
	}

	/**
	 * enables/disables fullscreen mode
	 * 
	 * @param b
	 */
	public void setFullscreen(boolean b) {
		this.fullscreen = b;
	}

	/**
	 * checks whether or not the game is set to borderless or not
	 * 
	 * @return boolean
	 */
	public boolean getBorderless() {
		return this.borderless;
	}

	/**
	 * enables/disables borderless mode
	 * 
	 * @param b
	 */
	public void setBorderless(boolean b) {
		this.borderless = b;
	}

	/**
	 * checks whether or not the game window is set to a custom frame-size
	 * 
	 * @return boolean
	 */
	public boolean getCustomframeSize() {
		return this.customframesize;
	}

	/**
	 * enables/disables custom frame size
	 * 
	 * @param b
	 */
	public void setCustomFrameSize(boolean b) {
		this.customframesize = b;
	}

	/**
	 * enables/disables developer mode
	 * 
	 * @param b
	 */
	public void setDevelopermode(boolean b) {
		this.developermode = b;
	}

	/**
	 * creates and adds a template to the memory-list with the obj as content
	 * and Name as name of the template
	 * 
	 * @param obj,
	 *            Name
	 */
	public void addToMemory(Object obj, String Name) {
		memory.addToMemory(obj, Name);
	}

	/**
	 * checks whether or not the memory-list contains an template with the same
	 * class as obj and the name Name
	 * 
	 * @param obj,
	 *            Name
	 * @return boolean
	 */
	public boolean inMemory(Object obj, String Name) {
		return memory.contains(obj, Name);
	}

	/**
	 * tries to get an template with the same class as obj and the name Name
	 * 
	 * @param obj,
	 *            Name
	 * @return Template
	 */
	public Template getFromMemory(Object obj, String Name) {
		return memory.getFromMemory(obj, Name);
	}

	/**
	 * removes a template with the same class as obj and the same name as Name
	 * 
	 * @param obj,
	 *            Name
	 */
	public void removeFromMemory(Object obj, String Name) {
		memory.removeFromMemory(obj, Name);
	}

	/**
	 * Java main method, starts the game
	 */
	public static void main(String[] args) {
		// start the main method to run the project
		new Main();
	}

	/**
	 * sets the Height of the game-window
	 * 
	 * @param int
	 *            h
	 */
	public void setHEIGHT(int h) {
		// TODO Auto-generated method stub
		this.HEIGHT = h;
	}

	/**
	 * sets the Width of the game-window
	 * 
	 * @param int
	 *            w
	 */
	public void setWIDTH(int w) {
		this.WIDTH = w;
	}

	/**
	 * Returns the scale of the game
	 * 
	 * @return Scale
	 */
	public double getScale() {
		return Scale;
	}

	/**
	 * sets the scale of the game
	 * 
	 * @param scale
	 */
	public void setScale(double scale) {
		if (hasmaximumscale) {
			if (scale > maxscale)
				scale = maxscale;
		}

		if (hasminimumscale) {
			if (scale < minscale)
				scale = minscale;
		}

		this.Scale = scale;
	}

	double minscale, maxscale;

	boolean hasmaximumscale = false, hasminimumscale = false;

	/**
	 * @param sets
	 *            a maximum scale of the game
	 */
	public void setMaxScale(double scale) {
		hasmaximumscale = true;
		this.maxscale = scale;
		this.setScale(this.Scale);
	}

	/**
	 * @param sets
	 *            a minimum scale of the game
	 */
	public void setMinScale(double scale) {
		hasminimumscale = true;
		this.minscale = scale;
		this.setScale(this.Scale);
	}

	/**
	 * checks whether or not exception logging is on or not
	 * 
	 * @return LogException
	 */
	public boolean isLogExceptions() {
		return LogExceptions;
	}

	/**
	 * enables/disables exception logging
	 * 
	 * @param logExceptions
	 */
	public void setLogExceptions(boolean logExceptions) {
		LogExceptions = logExceptions;
	}

	/**
	 * sets the number of times the game will update per seconds
	 * 
	 * @param n
	 */
	public void setUpdatesPerSecond(double n) {
		this.UpdatesPerSecond = n;
		this.UpdateTime = 1000000000.0 / UpdatesPerSecond;
	}

	/**
	 * returns how many times the game will update per seconds
	 * 
	 * @return updatesPerSeconds
	 */
	public double getUpdatesPerSeconds() {
		return this.UpdatesPerSecond;
	}

	/**
	 * checks whether or not Antialiasing is turned on or off
	 * 
	 * @return Antialiasing
	 */
	public boolean isAntialiased() {
		return this.Antialiasing;
	}

	/**
	 * enables/disables Antialiasing
	 * 
	 * @param f
	 */
	public void setAntialiased(boolean f) {
		this.Antialiasing = f;
	}

	/**
	 * changes the backgroundColor of the Canvas to the provided color
	 * 
	 * @param c
	 */
	public void setCanvasBackground(Color c) {
		canvas.setBackground(c);
	}

	/**
	 * pauses/unpauses the game
	 */
	public void togglePause() {
		kb.toggle();
	}

	/**
	 * 
	 * */
	public void setOrderDoc(String doc) {
		this.xmldoc = doc;
	}

	/**
	 * 
	 * */
	public String getOrderDoc() {
		return this.xmldoc;
	}

}
