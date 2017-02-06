package engine.Common;

import java.awt.Point;

public class Vector {

	private double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector get() {
		return this;
	}

	public Vector add(Vector v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	public Vector sub(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}

	public Vector mlt(Vector v) {
		this.x *= v.x;
		this.y *= v.y;
		return this;
	}

	public Vector div(Vector v) {
		this.x /= v.x;
		this.y /= v.y;
		return this;
	}

	public Point getPoint() {
		return new Point((int) this.x, (int) this.y);
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
}
