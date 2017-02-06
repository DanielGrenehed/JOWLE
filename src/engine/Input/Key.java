package engine.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.Main;

/**
 * Keyboard input for the game
 * @author Daniel Amos Grenehed
 * */
public class Key implements KeyListener {

	Main mn;

	/**
	 * @param Main
	 * */
	public Key(Main fmn) {
		mn = fmn;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		mn.press(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		mn.release(e.getKeyCode());
	}

}
