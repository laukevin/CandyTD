package projectiles;

import java.awt.Image;
import java.util.ArrayList;

import mobs.Mob;
import basics.Movable;

/**
 * This is a projectile which also slows down the mob that it hits.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class SlowingProjectile extends StraightProjectile {
	private double speedMod;
	private int ticksToSlow;

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
	 * @param speedMod
	 *            the modifier in speed (as a decimal, e.g. 0.5, 0.75).
	 * @param ticksToSlow
	 *            the number of game ticks that this slows down a mob for.
	 * @param img
	 *            the image that this projectile displays.
	 */
	public SlowingProjectile(double damage, double x, double y, double speed,
			double angle, double speedMod, int ticksToSlow, Image img){
		super(damage, x, y, speed, angle, img);
		this.speedMod = speedMod;
		this.ticksToSlow = ticksToSlow;
	}

	/**
	 * This method will check if this projectile has collided with a given
	 * ArrayList of mobs, and will deal damage, and set itself as dead if
	 * needed. This also will reduce the speed of the mob that it hits.
	 * 
	 * @return true if this projectile collides; false if it doesn't.
	 */
	public boolean hasCollided(ArrayList<Movable> mobs){
		for(Movable mob: mobs)
			if(mob instanceof Mob)
				if(((Mob)mob).contains(this)){
					// Tell this mob to take damage and reduce it's speed by a
					// set amount.
					((Mob)mob).takeDamage(damage);
					((Mob)mob).reduceSpeed(speedMod, ticksToSlow);
					this.isDead = true;
					return true;
				}
		return false;
	}
}
