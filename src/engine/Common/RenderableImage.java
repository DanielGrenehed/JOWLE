package engine.Common;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import engine.Render.Renderable;

/**
 *	@author Daniel Amos Grenehed
 *	
 *	This objects takes an image and position to render to a graphics element
 *
 * */
public class RenderableImage implements Renderable {

	private BufferedImage bufferedimage = null;
	private int x, y;
	
	/**
	 * Constructs the RenderableImage object
	 * @param BufferedImage bufferedimage, the image of the object
	 * @param int x, horizontal location of image 
	 * @param int y, vertical location of image
	 * */
	public RenderableImage(BufferedImage bufferedimage, int x, int y) {
		this.bufferedimage = bufferedimage;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(bufferedimage, this.x, this.y, null);
	}

	@Override
	public void update() {}
	
	/**
	 * @return BufferdImage of the RenderableImage object
	 * */
	public BufferedImage getImage() {
		return this.bufferedimage;
	}
	
	/**
	 * @param BufferedImage bufferedimage
	 * sets the image to the given bufferedimage
	 * */
	public void setImage(BufferedImage bufferedimage) {
		this.bufferedimage = bufferedimage;
	}
	
	/**
	 * @param int x, horizontal location of image 
	 * @param int y, vertical location of image
	 * sets the image location to the given location
	 * */
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return Point location of image placement
	 * */
	public Point getLocation() {
		return new Point(this.x, this.y);
	}

}
