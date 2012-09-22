package basics;

import java.awt.Graphics;
import java.awt.Image;

import towers.Tower;

/**
 * This is a Tile, each of which makes up the grid.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class Tile extends Base {
	private Tower isOver;
	private boolean traverseable;

	/**
	 * Creates a tile in the given position, whether it's traversable, and it's
	 * image.
	 * 
	 * @param x
	 *            the x value of the coordinate of it's top left-hand corner.
	 * @param y
	 *            the y value of the coordinate of it's top left-hand corner.
	 * @param t
	 *            whether it can be moved over.
	 * @param image
	 *            the image for this tile.
	 */
	public Tile(double x, double y, boolean t, Image image){
		this.isOver = null;
		this.x = x;
		this.y = y;
		this.traverseable = t;
		this.img = image;
	}

	/**
	 * This checks whether the given point is in this tile.
	 * 
	 * @param x
	 *            the x value of the point.
	 * @param y
	 *            the y value of the point.
	 * @return true if the point is in this tile; false if otherwise.
	 */
	public boolean contains(double x, double y){
		return (x >= this.x && x <= this.x + img.getWidth(null))
				&& (y >= this.y && y <= this.y + img.getHeight(null));
	}

	/**
	 * Place this given tower over this tile.
	 * 
	 * @param over
	 *            the tower to place over.
	 */
	public void placeOver(Tower over){
		this.isOver = over;
	}

	/**
	 * Remove the current tower.
	 */
	public void removeOver(){
		this.isOver = null;
	}

	/**
	 * Checks if this tile is being covered by a tower.
	 * 
	 * @return true if this tile is covered; false if otherwise.
	 */
	public boolean isCovered(){
		return isOver != null;
	}

	/**
	 * Draws this object on the given Graphics object.
	 */
	@Override
	public void paint(Graphics g){
		// Draw this tile.
		if(img != null){
			g.drawImage(img, (int)x, (int)y, null);
		}

		// Draw the covering tower, too.
		if(isOver != null && isOver instanceof Tower){
			isOver.paint(g);
		}
	}

	/**
	 * Get the tower that is currently on this tile.
	 * 
	 * @return the Tower that currently covers this tile.
	 */
	public Tower getOver(){
		return isOver;
	}

	/**
	 * Checks if this tile can be traversed.
	 * 
	 * @return true if it can be traversed; false if otherwise.
	 */
	public boolean isTraverseable(){
		return traverseable;
	}
}
