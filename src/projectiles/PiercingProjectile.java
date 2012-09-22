package projectiles;

import java.awt.Image;
import java.util.ArrayList;

import mobs.Mob;

import basics.Movable;

/**
 * This is a projectile which can hit multiple mobs in the same line.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class PiercingProjectile extends StraightProjectile {
	private int hits;
	private int maxHits;

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
	 *            @param maxHits the maximum number of mobs this can hit.
	 * @param img
	 *            the image that this projectile displays.
	 */
	public PiercingProjectile(double damage, double x, double y, double speed,
			double angle, int maxHits, Image img){
		super(damage, x, y, speed, angle, img);
		this.maxHits = maxHits;
		hits = 0;
	}

	/**
	 * This method will check if this projectile has collided with a given
	 * ArrayList of mobs, and will deal damage, and set itself as dead if
	 * needed. This takes into account that this projectile will pierce, and
	 * deals damage to more than one mob.
	 * 
	 * @return true if this projectile collides; false if it doesn't.
	 */
	public boolean hasCollided(ArrayList<Movable> mobs){
		boolean hasHit = false;
		for(Movable mob: mobs)
			if(mob instanceof Mob)
				if(((Mob)mob).contains(this)){
					((Mob)mob).takeDamage(damage);
					this.hits++;
					hasHit = true;
					if(this.hits >= maxHits){
						isDead = true;
						return true;
					}
				}
		return hasHit;
	}
}
