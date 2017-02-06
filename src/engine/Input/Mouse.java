package engine.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import engine.Main;

/**
 * Mouse input for the game
 * @author Daniel Amos Grenehed
 * */
public class Mouse implements MouseListener, MouseWheelListener {

	Main mn;
	
	public Mouse(Main fmn) {
		mn = fmn;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mn.pressMouse(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mn.releaseMouse(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		mn.addScroll(e.getPreciseWheelRotation());
	}

}