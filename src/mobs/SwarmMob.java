package mobs;

import java.awt.Image;
import java.util.ArrayList;

import basics.Movable;

/**
 * This is a regular mob, except that it can split into more mobs, each of which
 * have half that HP, but doesn't give any money when "killed".
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class SwarmMob extends Mob {
	private boolean canSplit;

	/**
	 * Create a SwarmMob from given values.
	 * 
	 * @param HP
	 *            the max HP of this mob.
	 * @param speed
	 *            the speed that this moves (in pixels).
	 * @param direction
	 *            the direction this moves in (in degrees).
	 * @param x
	 *            the x coordinate of the top left-hand corner of this mob.
	 * @param y
	 *            the y coordinate of the top left-hand corner of this mob.
	 * @param money
	 *            the money that this mob gives upon death.
	 * @param imgs
	 *            the images that this mob uses to paint.
	 * @param canSplit
	 *            if this mob can still split.
	 * @param image
	 *            the current image that this mob is on.
	 */
	public SwarmMob(double HP, int speed, int direction, double x, double y,
			int money, Image[] imgs, boolean canSplit, int image){
		super(HP, speed, direction, x, y, money, imgs);
		this.canSplit = canSplit;
		this.image = image;
	}

	/**
	 * This creates a swarm mob from a given SwarmMob as a parameter. This will
	 * copy most, if not all, aspects of that given mob.
	 * 
	 * @param canSplit
	 *            if this mob can still split.
	 * @param image
	 *            the current image that this mob is on.
	 */
	public SwarmMob(SwarmMob sm, boolean canSplit, double image){
		super(sm);
		if(image > 0){
			this.HP = this.HP * 3 / 4;
			this.money = 0;
		}else{
			this.money = sm.money;
		}
		this.money = 0;
		this.canSplit = canSplit;
		if(canSplit){
			this.image = image;
			this.img = imgs[(int)this.image];
		}
	}

	/**
	 * This does the same thing as {@link mobs.Mob#move()} except that it
	 * doesn't update the image.
	 */
	public void move(){
		if(needsMove()){
			if(nextMove == 0)
				direction = Movable.UP;
			else if(nextMove == 1)
				direction = Movable.RIGHT;
			else if(nextMove == 2)
				direction = Movable.DOWN;
			else if(nextMove == 3)
				direction = Movable.LEFT;
		}
		if(direction == Movable.DOWN)
			y += speed;
		else if(direction == Movable.UP)
			y -= speed;
		else if(direction == Movable.LEFT)
			x -= speed;
		else if(direction == Movable.RIGHT)
			x += speed;
	}

	/**
	 * When this mob dies, it will spawn more mobs. This returns the ArrayList
	 * of swarm mobs that this spawns.
	 * 
	 * @return the ArrayList, but only if it can split; an empty ArrayList
	 *         otherwise.
	 */
	public ArrayList<SwarmMob> getSplitted(){
		if(canSplit){
			ArrayList<SwarmMob> temp = new ArrayList<SwarmMob>();
			temp.add(new SwarmMob(this, image < imgs.length - 1? true: false,
					image + 1));
			temp.add(new SwarmMob(this, image < imgs.length - 1? true: false,
					image + 1));
			System.out.println("ASDF");
			return temp;
		}
		return new ArrayList<SwarmMob>();
	}
}
