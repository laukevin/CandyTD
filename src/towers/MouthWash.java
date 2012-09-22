package towers;

import java.awt.Image;
import java.util.ArrayList;

import main.MainFrame;
import mobs.Mob;
import projectiles.StraightProjectile;
import basics.Movable;

public class MouthWash extends Tower {

	/**
	 * Creates a MouthWash tower with various stats
	 * 
	 * @param price
	 *            the price of the tower
	 * @param damage
	 *            the damage the MouthWash deals to candied monsters
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
	 * @param images
	 *            A collection of images that the tower uses
	 */
	public MouthWash(int price, double damage, double range, double x,
			double y, double angle, int cooldown, Image[] imgs){
		super(price, damage, range, x, y, angle, cooldown, imgs);
		this.image = 0;
	}

	/**
	 * Replaces an old MouthWash tower with a new MouthWash tower
	 * 
	 * @param t
	 *            the MouthWash object that the new MouthWash will replace
	 * @param xtower
	 *            the old x coordinate of the tower on the board
	 * @param ytower
	 *            the old y coordinate of the tower on the board
	 */
	public MouthWash(MouthWash t, int xtower, int ytower){
		super(t.price, t.damage, t.range, xtower, ytower, t.angle,
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
		// Check to see if the tower has cooled down yet
		if(cooldown > 0)
			cooldown--;
		else{
			// If the tower has cooled down, then it will proceed to find the
			// farthest moving object
			Movable nearest2 = findFarthestEnemy(movingObjs);
			// If the farthest moving object is a mob, then the tower will
			// launch a
			// projectile at it
			if(nearest2 instanceof Mob){
				Mob nearest = (Mob)nearest2;
				// find the angle to the enemy
				double angleToEnemy = findAngleToEnemy(nearest);
				// launches a projectile to the enemy based on it's position
				StraightProjectile shoot = new StraightProjectile(damage, x
						+ (img.getWidth(null) / 2), y
						+ (img.getHeight(null) / 2), SPEED, angleToEnemy,
						MainFrame.PROJECTILE_IMAGES[7 + level]);
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
		if(level == 0){
			this.damage = 11;
			this.initCooldown = 5;
			this.range = 200;
			price += 120;
			level++;
			image = level;
			img = imgs[image];
		} else if(level == 1){
			this.damage = 20;
			this.initCooldown = 5;
			this.range = 240;
			price += 200;
			level++;
			image = level;
			img = imgs[image];
		}
	}

	/**
	 * Returns the cost required to build or upgrade the tower
	 * 
	 * @return To cost or upgrade fee
	 */
	public int getCostToUpgrade(){
		if(level == 0){
			return 120;
		}
		if(level == 1){
			return 200;
		}
		return 200;
	}
}
