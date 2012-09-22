package projectiles;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.MainFrame;

import basics.Movable;

/**
 * This is the basic class for a Projectile. This defines what happens when a
 * projectile collides.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public abstract class Projectile extends Movable {
	double damage;
	double angle;
	boolean isDead;
	
	public abstract boolean hasCollided(ArrayList<Movable> mobs);
	
	/**
	 * Moves this projectile in it's current direction.
	 */
	@Override
	public void move(){
		double angle = Math.toDegrees(this.angle);
		// Find out where to go.
		if(angle == 0){
			y += speed;
		} else if(angle < 90){
			y += speed * Math.cos(this.angle);
			x += speed * Math.sin(this.angle);
		} else if(angle == 90){
			x += speed;
		} else if(angle < 180){
			y -= speed * Math.cos(this.angle - Math.toRadians(90));
			x += speed * Math.sin(this.angle - Math.toRadians(90));
		} else if(angle == 180){
			y -= speed;
		} else if(angle < 270){
			y -= speed * Math.cos(this.angle - Math.toRadians(180));
			x -= speed * Math.sin(this.angle - Math.toRadians(180));
		} else if(angle == 270){
			x -= speed;
		} else{
			y += speed * Math.cos(this.angle - Math.toRadians(270));
			x -= speed * Math.sin(this.angle - Math.toRadians(270));
		}
		// You're dead if you're out of bounds, too.
		if(x < 0 || x > MainFrame.MAX_WIDTH || y < 0
				|| y > MainFrame.MAX_HEIGHT){
			this.isDead = true;
		}
	}

	/**
	 * Checks if this Projectile is dead.
	 * 
	 * @return true if this Projectile is used up; false if otherwise.
	 */
	public boolean isDead(){
		return isDead;
	}
	
	/**
	 * Paint this projectile in it's current heading and current position.
	 * 
	 * @param g
	 *            the Graphics object to draw on.
	 */
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.rotate(angle, x + (img.getWidth(null) / 2), y
				+ (img.getHeight(null) / 2));
		g2d.drawImage(img, (int)x, (int)y, null);
		g2d.rotate(-angle, x + (img.getWidth(null) / 2), y
				+ (img.getHeight(null) / 2));
	}
}
