package engine.Developer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import engine.Main;
import engine.Render.GameCanvas;
import engine.Render.Renderable;
import engine.Window.Frame;

/**
 * 
 * this is the class drawing developer info to the game
 * 
 * @author Daniel Amos Grenehed
 * */
public class GraphicDebug implements Renderable {

	Main mn;
	GameCanvas gc;
	Frame fm;

	/**
	 * @param Main, GameCanvas, Frame
	 * */
	public GraphicDebug(Main fmn, GameCanvas fgc, Frame ffm) {
		mn = fmn;
		gc = fgc;
		fm = ffm;
	}

	boolean sdi = false;
	boolean wp = false;
	
	/**
	 *  @return if Developer info should be shown
	 * */
	public boolean showDeveloperInfo() {
		
		if (mn.isPressed(KeyEvent.VK_O) && !wp) {
			sdi = !sdi;
			wp = true;
		} else if (wp && !mn.isPressed(KeyEvent.VK_O)) {
			wp = false;
		}
		
		return sdi;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.cyan);
		g.drawString("FPS: " + mn.getFPS(), 20, 20);
		g.drawString("Keys Pressed:" + mn.numbersOfkeysPressed(), 20, 40);
		if (mn.getBoundingBox().intersects(mn.getMouseBox()))
			g.drawRect(mn.getMouseBox().x, mn.getMouseBox().y,
					mn.getMouseBox().width, mn.getMouseBox().height);
		if (mn.isPressed(KeyEvent.VK_N)) mn.nextCamera();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}