package projectiles;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import mobs.Mob;
import basics.Movable;

/**
 * This is a basic straight projectile.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class StraightProjectile extends Projectile {
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
	 * @param img
	 *            the image that this projectile displays.
	 */
	public StraightProjectile(double damage, double x, double y, double speed,
			double angle, Image img){
		this.damage = damage;
		this.x = x;
		this.y = y;
		this.img = new ImageIcon(img).getImage();
		this.isDead = false;
		this.angle = angle;
		this.speed = speed;
	}

	/**
	 * This method will check if this projectile has collided with a given
	 * ArrayList of mobs, and will deal damage, and set itself as dead if
	 * needed.
	 * 
	 * @return true if this projectile collides; false if it doesn't.
	 */
	@Override
	public boolean hasCollided(ArrayList<Movable> mobs){
		for(Movable mob: mobs)
			if(mob instanceof Mob)
				if(((Mob)mob).contains(this)){
					((Mob)mob).takeDamage(damage);
					this.isDead = true;
					return true;
				}
		return false;
	}
}
