package towers;

import java.awt.Image;
import java.util.ArrayList;

import main.MainFrame;
import mobs.Mob;
import projectiles.SlowingProjectile;
import basics.Movable;

public class ToothPaste extends Tower {
	protected int speed = 15;
	protected double slowMod;
	protected int ticksToSlow;

	/**
	 * Creates a ToothPaste tower with various stats
	 * 
	 * @param price
	 *            the price of the tower
	 * @param damage
	 *            the damage the ToothPaste deals to candied monsters
	 * @param range
	 *            the range the tower can attack up to
	 * @param x
	 *            the x coordinate of the tower on the board
	 * @param y
	 *            the y coordinate of the tower on the board
	 * @param angle
	 *            the initial angle the tower is positioned
	 * @param cooldown
	 *            the time it takes for the tower to cool down
	 * @param slowMod
	 *            how much slower a projectile from the tower will force a
	 *            monster
	 * @param ticksToSlow
	 *            how long a monster is affected by a speed decrease
	 * @param images
	 *            A collection of images that the tower uses
	 */
	public ToothPaste(int price, double damage, double range, double x,
			double y, double angle, int cooldown, double slowMod,
			int ticksToSlow, Image[] imgs){
		super(price, damage, range, x, y, angle, cooldown, imgs);
		this.slowMod = slowMod;
		this.ticksToSlow = ticksToSlow;
		image = 0;
	}

	/**
	 * Replaces an old ToothPaste tower with a new ToothPaste tower
	 * 
	 * @param t
	 *            the ToothPaste object that the new ToothPaste will replace
	 * @param xtower
	 *            the old x coordinate of the tower on the board
	 * @param ytower
	 *            the old y coordinate of the tower on the board
	 */
	public ToothPaste(ToothPaste t, int xtower, int ytower){
		super(t.price, t.damage, t.range, xtower, ytower, t.angle,
				t.initCooldown, t.imgs);
		this.slowMod = t.slowMod;
		this.ticksToSlow = t.ticksToSlow;
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
		// Check to see if the tower has cooled down yet
		if(cooldown > 0)
			cooldown--;
		else{
			// If the tower has cooled down, then it will proceed to find the
			// farthest moving object
			Movable nearest2 = findFarthestEnemy(movingObjs);
			// If the farthest moving object is a mob, then the tower will
			// launch a projectile at it
			if(nearest2 instanceof Mob){
				Mob nearest = (Mob)nearest2;
				// find the angle to the enemy
				double angleToEnemy = findAngleToEnemy(nearest);
				// launches a projectile to the enemy based on it's position
				SlowingProjectile shoot = new SlowingProjectile(damage, x
						+ (img.getWidth(null) / 2), y
						+ (img.getHeight(null) / 2), speed, angleToEnemy,
						slowMod, ticksToSlow,
						MainFrame.PROJECTILE_IMAGES[1 + level]);
				addMoveable(shoot);
				this.angle = angleToEnemy;
				// Resets the counter for cool down so the tower does not
				// rapidly fire
				cooldown = initCooldown;
			}
		}
	}

	/**
	 * Performs an upgrade that reloads a new image for the tower, as well as
	 * change the stats of the tower
	 */
	public void doUpgrade(){
		if(canUpgrade()){
			if(level == 0){
				this.damage = 2;
				this.initCooldown = 4;
				this.range = 75;
				price += 75;
				ticksToSlow = 25;
				level++;
				image = level;
				img = imgs[image];
			} else if(level == 1){
				this.damage = 3;
				this.initCooldown = 3;
				this.range = 90;
				price += 125;
				ticksToSlow = 39;
				level++;
				image = level;
				img = imgs[image];
			}
		}
	}

	/**
	 * Returns the cost required to build or upgrade the tower
	 * 
	 * @return To cost or upgrade fee
	 */
	public int getCostToUpgrade(){
		if(level == 0){
			return 75;
		}
		if(level == 1){
			return 125;
		}
		return 125;
	}
}
