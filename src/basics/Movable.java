package basics;

/**
 * This is the basic class for the basis of a moving object. Mob and Projectile
 * both will extend this class.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public abstract class Movable extends Base {
	// These are default values for UP, DOWN, LEFT, and RIGHT. (in degrees)
	public final static int UP = 0;
	public final static int RIGHT = 90;
	public final static int DOWN = 180;
	public final static int LEFT = 270;

	protected double speed;

	public abstract void move();

	public abstract boolean isDead();

	/**
	 * This method checks for equality between two Movable objects.
	 * 
	 * @return true if the Movable objects have the same position and speed.
	 */
	public boolean equals(Object o){
		if(o instanceof Movable){
			Movable m = (Movable)o;
			return this.x == m.x && this.y == m.y && this.speed == m.speed;
		}
		return false;
	}
}
