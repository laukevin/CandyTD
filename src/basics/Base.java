package basics;

import java.awt.Graphics;
import java.awt.Image;

/**
 * The generic Object base, on which all the other objects of this game are
 * based upon.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public abstract class Base {
	protected int image;
	protected double x;
	protected double y;
	protected Image[] imgs;
	protected Image img;

	public abstract void paint(Graphics g);

	/**
	 * Draws this object on the given Graphics object in the given position.
	 * 
	 * @param x
	 *            the x coordinate to draw this onto.
	 * @param y
	 *            the y coordinate to draw this onto.
	 */
	public void paint(Graphics g, int x, int y){
		g.drawImage(img, x, y, null);
	}

	/**
	 * Gets the x coordinate of the top-left-hand corner of this object.
	 * 
	 * @return the x coordinate of the top-left-hand corner of this object.
	 */
	public double getX(){
		return x;
	}

	/**
	 * Gets the y coordinate of the top-left-hand corner of this object.
	 * 
	 * @return the y coordinate of the top-left-hand corner of this object.
	 */
	public double getY(){
		return y;
	}

	/**
	 * Gets the image for this Base.
	 * 
	 * @return the image for this base.
	 */
	public Image getImage(){
		return img;
	}
}
