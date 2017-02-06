package engine.Render;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import engine.Main;
import engine.Developer.GraphicDebug;

/**
 * Here is where all the graphics magic happens , not really but kinda' =)
 * 
 * @author Daniel Amos Grenehed
 */
public class GameCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Main mn;

	GraphicDebug gd;

	/**
	 * 
	 * */
	public GameCanvas(Main fmn, GraphicDebug fgd) {
		mn = fmn;
		gd = fgd;
		this.setSize(mn.getWIDTH(), mn.getHEIGHT());
		this.setBackgroundColor(Color.BLACK);

	}

	BufferStrategy bs;

	long lastTime = 0;

	/**
	 * Paints Graphics to the canvas
	 */
	public synchronized void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
		} else {
			Graphics g = bs.getDrawGraphics();

			Graphics2D g2d = (Graphics2D) g;
			g2d.scale(mn.getScale(), mn.getScale());
			g2d.setStroke(new BasicStroke(4));

			g.clearRect(0, 0, mn.getWIDTH(), mn.getHEIGHT());

			if (mn.isAntialiased()) {
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			// http://www.java2s.com/Code/Java/2D-Graphics-GUI/AntiAlias.htm

			mn.Render(g);

			if (mn.isDevelopermode() && gd.showDeveloperInfo()) {
				gd.paint(g);
			}
			// end of draw graphics
			g2d.dispose();
			g.dispose();

			bs.show();
		}
		mn.setFPS(((long) (1000000000.0 / (System.nanoTime() - lastTime))));
		lastTime = System.nanoTime();
	}

	/**
	 * gets the current BackgroundColor of the canvas
	 * 
	 * @return Color
	 */
	public Color getBackgroundColor() {
		return this.getBackground();
	}

	/**
	 * sets the current BackgroundColor of canvas to the given color
	 * 
	 * @param bkgcolor
	 */
	public void setBackgroundColor(Color bkgcolor) {
		this.setBackground(bkgcolor);
	}

}
