package engine.Common;

import java.awt.Graphics;
import java.awt.Point;

/**
 * 
 * this is a 2d vector starting in origo (0,0)
 * 
 * @author Daniel Amos Grenehed
 * */
public class Vector2D extends Point {

	private static final long serialVersionUID = 1L;
	
	/**
	 * returns the angle of the vector
	 * */
	public double getAngle() {
		return Math.atan2(this.getY(), this.getX());
	}
	
	/**
	 * adds the position of p1 to the point of the vector
	 * @param p1
	 * */
	public void applyForce(Point p1) {
		this.setLocation(this.x + p1.x, this.y + p1.y);
	}
	
	/**
	 * adds the double X and Y to the point of the vector
	 * @param X, Y
	 * */
	public void applyForce(double X, double Y) {
		this.setLocation(this.x+X, this.y+Y);
	}
	
	/**
	 * @return the length from origo to this point
	 * */
	public double getSum() {
		double v0 = Math.pow(x, 2) + Math.pow(y, 2);
		return Math.sqrt(v0);
	}
	
	/**
	 * Paints the vector to the given Graphics Object
	 * @param g
	 * */
	public void paint(Graphics g) {
		g.drawLine(0, 0, x, y);
	}
	
}
