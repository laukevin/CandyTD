package projectiles;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.MainMethods;
import mobs.Mob;
import basics.Movable;

/**
 * This is a projectile which also deals splash damage to it's surrounding mobs.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class SplashProjectile extends Projectile {
	double splashRange;

	/**
	 * This creates a projectile from the given values.
	 * 
	 * @param damage
	 *            the damage this does to a mob.
	 * @param x
	 *            the x position of it's top left-hand corner.
	 * @param y
	 *            the y position of it's top left-hand corner.
	 * @param speed
	 *            the speed at which this travels.
	 * @param angle
	 *            the angle which this is facing (in radians)
	 * @param splashRange
	 *            the range that this can deal damage (in pixels).
	 * @param img
	 *            the image that this projectile displays.
	 */
	public SplashProjectile(double damage, double x, double y, double speed,
			double angle, double splashRange, Image img){
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.img = new ImageIcon(img).getImage();
		this.isDead = false;
		this.angle = angle;
		this.speed = speed;
		this.splashRange = splashRange;
	}

	/**
	 * This method will check if this projectile has collided with a given
	 * ArrayList of mobs, and will deal damage, and set itself as dead if
	 * needed. This also takes into account that it deals splash damage to
	 * surrounding mobs (in a given range).
	 * 
	 * @return true if this projectile collides; false if it doesn't.
	 */
	@Override
	public boolean hasCollided(ArrayList<Movable> mobs){
		for(Movable mob: mobs)
			if(mob instanceof Mob)
				if(((Mob)mob).contains(this)){
					// Says that it's hit, but doesn't return yet.
					((Mob)mob).takeDamage(damage);
					this.isDead = true;
					break;
				}
		if(this.isDead){
			for(Movable mob: mobs)
				if(mob instanceof Mob){
					double dist = MainMethods.distance(mob.getX(), mob.getY(),
							this.x, this.y);
					if(dist <= splashRange)
						// After we deal damage to all the surrounding mobs...
						((Mob)mob).takeDamage(damage
								* (dist / this.splashRange));
				}
			// Then we return!
			return true;
		}
		return false;
	}
}
