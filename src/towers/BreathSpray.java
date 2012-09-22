package towers;

import java.awt.Image;
import java.util.ArrayList;

import main.MainFrame;
import mobs.Mob;
import projectiles.Projectile;
import projectiles.SplashProjectile;
import basics.Movable;

public class BreathSpray extends Tower {
	private double splashRange;

	/**
	 * Creates a BreathSpray tower with various stats
	 * 
	 * @param price
	 *            the price of the tower
	 * @param damage
	 *            the damage the BreathSpray deals to candied monsters
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
	public BreathSpray(int price, double damage, double range, double x,
			double y, double angle, int cooldown, double splashRange,
			Image[] imgs){
		super(price, damage, range, x, y, angle, cooldown, imgs);
		this.splashRange = splashRange;
	}

	/**
	 * Replaces an old BreathSpray tower with a new BreathSpray tower
	 * 
	 * @param t
	 *            the BreathSpray object that the new BreathSpray will replace
	 * @param xtower
	 *            the old x coordinate of the tower on the board
	 * @param ytower
	 *            the old y coordinate of the tower on the board
	 */
	public BreathSpray(BreathSpray t, int xTower, int yTower){
		super(t.price, t.damage, t.range, xTower, yTower, t.angle,
				t.initCooldown, t.imgs);
		this.initCooldown = t.initCooldown;
		this.splashRange = t.splashRange;
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
				Projectile shoot = new SplashProjectile(damage, x
						+ (img.getWidth(null) / 2), y
						+ (img.getHeight(null) / 2), SPEED, angleToEnemy,
						splashRange, MainFrame.PROJECTILE_IMAGES[4 + level]);
				addMoveable(shoot);
				this.angle = angleToEnemy;
				// Resets the cool down counted
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
			this.damage = 6;
			this.initCooldown = 4;
			this.range = 110;
			this.splashRange = 55;
			price += 110;
			level++;
			image = level;
			img = imgs[image];
		} else if(level == 1){
			this.damage = 10;
			this.initCooldown = 4;
			this.range = 130;
			this.splashRange = 65;
			price += 180;
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
			return 110;
		}
		if(level == 1){
			return 180;
		}
		return 180;
	}

}
