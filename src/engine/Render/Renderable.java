package engine.Render;

import java.awt.Graphics;

/**
 * this is an object class that is made to make your life easier when drawing
 * different stuffs to the screen. it is what is makeing this engine paint many
 * different elements to the game canvas without relying on me(the developer of
 * this engine) to hardcode the right graphics into the engine
 * 
 * @author Daniel Amos Grenehed
 */
public interface Renderable {

	/**
	 * paints the graphical elements of this class to the given Graphics element
	 * 
	 * @param Graphics element g
	 */
	public void paint(Graphics g);

	/**
	 * updates the elements of this class
	 */
	public void update();

}
