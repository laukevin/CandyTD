package towers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import main.MainFrame;
import main.MainMethods;
import mobs.Mob;

import basics.Base;
import basics.Movable;

public abstract class Tower extends Base {

	// A collection of all the movable objects
	private ArrayList<Movable> temp;

	// Elements of the game that all towers contain
	protected static final int SPEED = 15;

	protected int cooldown;
	protected int initCooldown;
	protected int level;
	protected double range;
	protected double damage;
	protected int price;
	protected double angle;

	/**
	 * Performs the function of the tower
	 * 
	 * @param movingObjs
	 *            A collection of objects that the towers will use to determine
	 *            the target
	 */
	public abstract void doFunction(ArrayList<Movable> movingObjs);

	/**
	 * Upgrades the tower from one level to another
	 */
	public abstract void doUpgrade();

	/**
	 * Returns the cost required to build or upgrade a tower
	 * 
	 * @return The cost of the tower
	 */
	public abstract int getCostToUpgrade();

	/**
	 * Find the farthest enemy that can be hit within a certain radius
	 * 
	 * @param moving
	 *            a collection of all the moving enemies
	 * @return The farthest enemy within the bounds of the tower and the range
	 */
	protected Movable findFarthestEnemy(ArrayList<Movable> moving){
		double farthest = Integer.MIN_VALUE;
		int idxOfMob = -1;
		// Search for the farthest enemy within the range
		for(int i = 0; i < moving.size(); i++){
			if(moving.get(i) instanceof Mob){
				Mob mob = (Mob)moving.get(i);
				double tempEnemyPosX = mob.getX();
				double tempEnemyPosY = mob.getY();
				double tempFarthest = MainMethods.distance(x
						+ img.getWidth(null) / 2, y + img.getHeight(null) / 2,
						tempEnemyPosX, tempEnemyPosY);
				if(tempFarthest <= range && tempFarthest >= farthest){
					farthest = tempFarthest;
					idxOfMob = i;
				}
			}
		}
		if(idxOfMob == -1){
			return null;
		}
		return moving.get(idxOfMob);
	}

	/**
	 * Finds the angle between the tower and a target
	 * 
	 * @param enemy
	 *            The enemy that will be targeted
	 * @return the angle between the two objects on the grid
	 */
	protected double findAngleToEnemy(Movable enemy){
		double enemyX = enemy.getX() - x;
		double enemyY = enemy.getY() - y;
		return Math.atan2(enemyX, enemyY);
	}

	/**
	 * Creates a basic tower
	 * 
	 * @param price
	 *            the price of the tower
	 * @param damage
	 *            the damage a tower deals
	 * @param range
	 *            the range of the tower
	 * @param x
	 *            the x coordinate of the tower
	 * @param y
	 *            the y coordinate of the tower
	 * @param angle
	 *            the inital angle
	 * @param cooldown
	 *            the amount of time it takes to cool down
	 * @param imgs
	 *            A collection of images that is used to draw the tower
	 */
	public Tower(int price, double damage, double range, double x, double y,
			double angle, int cooldown, Image[] imgs){
		this.price = price;
		this.damage = damage;
		this.range = range;
		this.imgs = imgs;
		this.image = 0;
		this.img = imgs[image];
		this.temp = new ArrayList<Movable>();
		this.initCooldown = cooldown;
		this.cooldown = 0;
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.level = 0;
	}

	/**
	 * Checks if a tower is able to upgrade
	 * 
	 * @return if the tower can be upgraded
	 */
	public boolean canUpgrade(){
		return level < 2;
	}

	/**
	 * Returns information about the range of the tower
	 * 
	 * @return the tower's range
	 */
	public double getRange(){
		return range;
	}

	/**
	 * Gives the sell price of a tower
	 * 
	 * @return the sell price of a tower
	 */
	public int getSellPrice(){
		return (int)(price * 0.6);
	}

	/**
	 * Gives the full price of a tower
	 * 
	 * @return the cost of buying a tower
	 */
	public int getFullPrice(){
		return price;
	}

	/**
	 * Paints a basic tower
	 * 
	 * @param g
	 *            the graphics to be painted
	 */
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		g2d.rotate(angle, x + img.getWidth(null) / 2, y + img.getHeight(null)
				/ 2);
		g2d.drawImage(img, (int)x, (int)y, null);
		g2d.rotate(-angle, x + img.getWidth(null) / 2, y + img.getHeight(null)
				/ 2);
	}

	/**
	 * Checks if a an object is on top of another object
	 * 
	 * @param x
	 *            the x coordinate of the other object
	 * @param y
	 *            the y coordinate of the other object
	 * @return if the object is on top of another object
	 */
	public boolean contains(double x, double y){
		return (x >= this.x && x <= this.x + img.getWidth(null))
				&& (y >= this.y && y <= this.y + img.getHeight(null));
	}

	/**
	 * Adds a moveable object to a temporary collection of all movable objects
	 */
	protected void addMoveable(Movable m){
		temp.add(m);
	}

	/**
	 * Gets all the moving objects on the grid
	 * 
	 * @return a list of all the moving objects
	 */
	public ArrayList<Movable> getProjectiles(){
		ArrayList<Movable> moving = new ArrayList<Movable>(temp);
		temp = new ArrayList<Movable>();
		return moving;
	}

	/**
	 * Draws the image onto a panel
	 * 
	 * @param g
	 *            the graphic to draw
	 * @param xTower
	 *            the x coordinate of the tower on the grid
	 * @param yTower
	 *            the y coordinate of the tower on the grid
	 */
	public void paint(Graphics g, int xTower, int yTower){
		g.drawImage(img, xTower, yTower, null);
	}

	/**
	 * Outputs a message about the tower's statistics
	 * 
	 * @return a message with important tower information
	 */
	public String currStats(){
		return String.format("Damage: %f\nRange (in tiles): %f\nFire"
				+ " speed (shots per second): %d\nUpgrade cost: %d", damage,
				range / MainFrame.TILE_SIZE, initCooldown, getCostToUpgrade());
	}

	/**
	 * Get the intial cost of a tower
	 * 
	 * @return the price of a tower
	 */
	public int getInitCost(){
		return price;
	}
}
