package engine.Common;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import engine.Render.Renderable;

/**
 * 
 * @author Daniel Amos Grenehed
 * 
 * This object takes an string and position to render it to a graphics element
 * 
 * */
public class RenderableText implements Renderable {

	private String text;
	private int x, y;
	private Font font, canvasFont;
	private Color color = Color.gray;
	FontMetrics fontm;
	/**
	 * constructs the RenderableText object 
	 * @param String text, the text to be displayed
	 * @param int x, horizontal location of image 
	 * @param int y, vertical location of image
	 * 
	 * */
	public RenderableText(String text, int x, int y) {
		this.text = text;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void paint(Graphics g) {
		if (g.getFont() != canvasFont) canvasFont = g.getFont();
		if (font == null) font = canvasFont;
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, this.x, this.y);
		g.setFont(canvasFont);
	}

	@Override
	public void update() {}

	/**
	 * @return String text of the object
	 * */
	public String getText() {
		return text;
	}

	/**
	 * @param String text, changes the text to be displayed
	 * */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * sets the center of the text to the last position 
	 * */
	public void centerText() {
		
		this.x= this.x-((font.getSize()/2)*(text.length()/2));
		this.y= this.y-(font.getSize()/2);
	}
	
	/**
	 * @param int x, horizontal location of image 
	 * @param int y, vertical location of image
	 * sets the text location to the given location
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
	
	/**
	 * @param Color color, changes the color of the text
	 * */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * @return Color color of the text
	 * */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * @param Font font, changes the font of the text
	 * */
	public void setFont(Font font) {
		this.font = font;
	}
	
	/**
	 * @return  Font font of the text
	 * */
	public Font getFont() {
		return this.font;
	}
	
}
