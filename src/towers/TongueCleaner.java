package towers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import main.MainFrame;
import mobs.Mob;
import basics.Movable;

public class TongueCleaner extends Tower {

	/**
	 * Creates a TongueCleaner tower with various stats
	 * 
	 * @param price
	 *            the price of the tower
	 * @param damage
	 *            the damage the TongueCleaner deals to candied monsters
	 * @param range
	 *            the range the tower can attack up to
	 * @param x
	 *            the x coordinate of the tower on the board
	 * @param y
	 *            the y coordinate of the tower on the board
	 * @param angle
	 *            the initial angle the tower is positioned
	 * @param images
	 *            A collection of images that the tower uses
	 */
	public TongueCleaner(int price, double damage, double range, double x,
			double y, double angle, Image[] imgs){
		super(price, damage, range, x, y, angle, 0, imgs);
	}

	/**
	 * Replaces an old tongueCleaner tower with a new tongueCleaner tower
	 * 
	 * @param t
	 *            the tongueCleaner object that the new tongueCleaner will
	 *            replace
	 * @param xtower
	 *            the old x coordinate of the tower on the board
	 * @param ytower
	 *            the old y coordinate of the tower on the board
	 */
	public TongueCleaner(TongueCleaner t, int xTower, int yTower){
		super(t.price, t.damage, t.range, xTower - 32, yTower, t.angle,
				t.initCooldown, t.imgs);
		this.image = 0;
	}

	/**
	 * Performs the primary functions of a tower, which include finding an
	 * enemy, launching a projectile and cooling down for a short period of time
	 * 
	 * @param movingObjs
	 *            a collections of all the moving objects on the grid that will
	 *            be searching to check if it is viable to be targetted by the
	 *            tower
	 */
	public void doFunction(ArrayList<Movable> movingObjs){
		// Determines the angle speed to turn based on level
		if(level == 0)
			angle += Math.toRadians(25);
		if(level == 1)
			angle += Math.toRadians(30);
		if(level == 2)
			angle += Math.toRadians(47.5);
		double degrees = Math.toDegrees(angle);
		degrees %= 360;
		degrees -= 22.5;
		double x = this.x + 1 + MainFrame.TILE_SIZE;
		double y = this.y + 1;
		// Rotates in a circle
		if(degrees < 45 || degrees == 360){
			x -= MainFrame.TILE_SIZE;
			y -= MainFrame.TILE_SIZE;
		} else if(degrees < 90){
			y -= MainFrame.TILE_SIZE;
		} else if(degrees < 135){
			y -= MainFrame.TILE_SIZE;
			x += MainFrame.TILE_SIZE;
		} else if(degrees < 180){
			x += MainFrame.TILE_SIZE;
		} else if(degrees < 225){
			x += MainFrame.TILE_SIZE;
			y += MainFrame.TILE_SIZE;
		} else if(degrees < 270){
			y += MainFrame.TILE_SIZE;
		} else if(degrees < 315){
			y += MainFrame.TILE_SIZE;
			x -= MainFrame.TILE_SIZE;
		} else{
			x -= MainFrame.TILE_SIZE;
		}
		// Hits any enemy that touches the tongueCleaner
		for(Movable m: movingObjs)
			if(m instanceof Mob)
				if(((Mob)m).contains((int)x, (int)y))
					((Mob)m).takeDamage(damage);
	}

	/**
	 * Performs an upgrade that reloads a new image for the tower, as well as
	 * change the stats of the tower
	 */
	public void doUpgrade(){
		if(level == 0){
			this.damage = 7;
			this.initCooldown = 3;
			this.range = 100;
			price += 100;
			level++;
			image = level;
			img = imgs[image];
		} else if(level == 1){
			this.damage = 10;
			this.initCooldown = 3;
			this.range = 100;
			price += 150;
			level++;
			image = level;
			img = imgs[image];
		}
	}

	/**
	 * Returns the cost required to build or upgrade the tower
	 * 
	 * @return 100 dollars to upgrade the tower if it is level 0 150 dollars to
	 *         upgrade the tower if it is or above level 1
	 */
	public int getCostToUpgrade(){
		if(level == 0){
			return 100;
		}
		if(level == 1){
			return 150;
		}
		return 150;
	}

	/**
	 * Paints the tongueCleaner as it spins around a tile
	 * 
	 * @param g
	 *            the graphics which will be painted onto the panel
	 */
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.rotate(angle, x + img.getWidth(null) / 2 + img.getWidth(null) / 4,
				y + img.getHeight(null) / 2);
		g2d.drawImage(img, (int)x, (int)y, null);
		g2d.rotate(-angle, x + img.getWidth(null) / 2 + img.getWidth(null) / 4,
				y + img.getHeight(null) / 2);
	}
}
