package mobs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Arrays;

import main.MainFrame;
import basics.Movable;

/**
 * This is the Mob class, which handles all the images/movements/HP/etc... for
 * each monster in the grid.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class Mob extends Movable {
	/*
	 * Up is 0, Right is 1, Down is 2, Left is 3
	 */

	protected double HP;
	protected double slowMod;
	protected int direction;
	protected int nextMove;
	protected int money;
	protected int ticksToSlow;
	protected Image[] imgs;
	protected double image;

	/**
	 * This creates a mob from these given parameters.
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
	 */
	public Mob(double HP, int speed, int direction, double x, double y,
			int money, Image[] imgs){
		this.HP = HP;
		this.speed = speed;
		this.direction = direction;
		this.imgs = imgs;
		this.x = x - imgs[0].getWidth(null) / 2;
		this.y = y - imgs[0].getHeight(null) / 2;
		this.img = imgs[0];
		this.money = money;
	}

	/**
	 * This creates a mob from a given Mob as a parameter. This will copy most,
	 * if not all, aspects of that given mob.
	 * 
	 * @param mob
	 *            the given mob.
	 */
	public Mob(Mob mob){
		this.HP = mob.HP;
		this.speed = mob.speed;
		this.direction = mob.direction;
		this.x = mob.x;
		this.y = mob.y;
		this.imgs = mob.imgs;
		this.img = imgs[0];
		this.money = mob.money;
	}

	/**
	 * Reduces this mob's HP.
	 * 
	 * @param d
	 *            the damage taken.
	 */
	public void takeDamage(double d){
		this.HP -= d;
	}

	/**
	 * Reduces this mob's speed, temporarily.
	 * 
	 * @param slowMod
	 *            the modifier of speed (as a decimal, e.g. 0.5, 0.75).
	 * @param ticksToSlow
	 *            how long to slow down this mob for (in game ticks).
	 */
	public void reduceSpeed(double slowMod, int ticksToSlow){
		this.slowMod = slowMod;
		this.ticksToSlow = ticksToSlow;
	}

	/**
	 * This checks if the mob is dead or not.
	 * 
	 * @return true if this mob's HP is below or equal to 0; false if otherwise.
	 */
	public boolean isDead(){
		return HP <= 0;
	}

	/**
	 * This checks if this mob needs it's move.
	 * 
	 * @return true if this mob is in the centre of a tile; false if otherwise.
	 */
	public boolean needsMove(){
		return (this.x + (this.img.getWidth(null) / 2) - MainFrame.TILE_SIZE / 2)
				% MainFrame.TILE_SIZE == 0
				&& (this.y + (this.img.getHeight(null) / 2) - MainFrame.TILE_SIZE / 2)
						% MainFrame.TILE_SIZE == 0;
	}

	/**
	 * This just moves the mob in it's current heading.
	 */
	@Override
	public void move(){
		// If this mob needs a move, we update the direction.
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

		// If we're being slowed, apply the modifier.
		double speed = this.speed;
		if(ticksToSlow > 0){
			speed *= slowMod;
			ticksToSlow--;
		}

		// Then move in the given direction.
		if(direction == Movable.DOWN)
			y += speed;
		else if(direction == Movable.UP)
			y -= speed;
		else if(direction == Movable.LEFT)
			x -= speed;
		else if(direction == Movable.RIGHT)
			x += speed;

		// Update the image for this mob.
		image += 0.25;
		img = imgs[((int)image) % imgs.length];
	}

	/**
	 * Paint this mob in it's current heading and current position.
	 * 
	 * @param g
	 *            the Graphics object to draw on.
	 */
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		double radians = Math.toRadians(direction);
		// Rotate the Graphics2D object by the certain amount.
		g2d.rotate(radians, x + (img.getWidth(null) / 2), y
				+ (img.getHeight(null) / 2));
		g2d.drawImage(img, (int)x, (int)y, null);
		// Rotate it back!
		g2d.rotate(-radians, x + (img.getWidth(null) / 2), y
				+ (img.getHeight(null) / 2));
	}

	/**
	 * Checks if the given point is within this mob.
	 * 
	 * @param x
	 *            the x value of the coordinate.
	 * @param y
	 *            the y value of the coordinate.
	 * @return true if they are on the same grid tile; false if otherwise.
	 */
	public boolean contains(int x, int y){
		return (int)(this.x) / MainFrame.TILE_SIZE * MainFrame.TILE_SIZE == x
				/ MainFrame.TILE_SIZE * MainFrame.TILE_SIZE
				&& (int)(this.y) / MainFrame.TILE_SIZE * MainFrame.TILE_SIZE == y
						/ MainFrame.TILE_SIZE * MainFrame.TILE_SIZE;
	}

	/**
	 * Checks if the given movable object is in the same tile as this mob.
	 * 
	 * @param mov
	 *            the movable object to check with.
	 * @return true if they are on the same grid tile; false if otherwise.
	 */
	public boolean contains(Movable mov){
		// Convert all the points to integers (so we can do integer division)
		int cx = (int)x + img.getWidth(null);
		int cy = (int)y + img.getHeight(null);
		int cx2 = (int)mov.getX() + mov.getImage().getWidth(null);
		int cy2 = (int)mov.getY() + mov.getImage().getHeight(null);
		return cx / MainFrame.TILE_SIZE * MainFrame.TILE_SIZE == cx2
				/ MainFrame.TILE_SIZE * MainFrame.TILE_SIZE
				&& cy / MainFrame.TILE_SIZE * MainFrame.TILE_SIZE == cy2
						/ MainFrame.TILE_SIZE * MainFrame.TILE_SIZE;
	}

	/**
	 * Determines this mob's next move from a given grid of movement costs. This
	 * will take, randomly, one of the tiles that is lowest in movement cost.
	 * 
	 * @param boardCosts
	 */
	public void determineNextMove(int[][] boardCosts){
		/*
		 * Up is 0, Right is 1, Down is 2, Left is 3
		 */
		int min = Integer.MAX_VALUE;
		int xi = (int)(x / 32);
		int yi = (int)(y / 32);
		boolean[] temp = new boolean[4];
		Arrays.fill(temp, false);
		// Firstly, find the smallest value.
		if(xi < boardCosts.length - 1 && boardCosts[xi + 1][yi] < min
				&& boardCosts[xi + 1][yi] >= 0){
			min = boardCosts[xi + 1][yi];
		}
		if(xi > 0 && boardCosts[xi - 1][yi] < min
				&& boardCosts[xi - 1][yi] >= 0){
			min = boardCosts[xi - 1][yi];
		}
		if(yi < boardCosts[0].length - 1 && boardCosts[xi][yi + 1] < min
				&& boardCosts[xi][yi + 1] >= 0){
			min = boardCosts[xi][yi + 1];
		}
		if(yi > 0 && boardCosts[xi][yi - 1] < min
				&& boardCosts[xi][yi - 1] >= 0){
			min = boardCosts[xi][yi - 1];
		}

		// Then we see which ones are that smallest value...
		if(xi < boardCosts.length - 1 && boardCosts[xi + 1][yi] == min){
			temp[1] = true;
		}
		if(xi > 0 && boardCosts[xi - 1][yi] == min){
			temp[3] = true;
		}
		if(yi < boardCosts[0].length - 1 && boardCosts[xi][yi + 1] == min){
			temp[2] = true;
		}
		if(yi > 0 && boardCosts[xi][yi - 1] == min){
			temp[0] = true;
		}

		// And we pick one of those moves from them.
		do{
			nextMove = (int)(Math.random() * 4);
		} while(!temp[nextMove]);
	}

	/**
	 * This method checks if this mob is on the last tile of the grid.
	 * 
	 * @return true if the mob's x position corresponds to the last tile and the
	 *         y position as well; false if otherwise.
	 */
	public boolean isOnLastTile(){
		return this.x >= MainFrame.MAX_WIDTH - MainFrame.TILE_SIZE * 2
				&& this.y >= MainFrame.MAX_HEIGHT - MainFrame.TILE_SIZE * 2;
	}

	/**
	 * This returns the amount of money that this mob gives after being
	 * "killed".
	 * 
	 * @return the money that this gives.
	 */
	public int getMoney(){
		return this.money;
	}

	/**
	 * This gives a String representation of this mob.
	 * @return a String in the format: "HP: x.xxx\nGives: $x".
	 */
	public String toString(){
		return String.format("HP: %.2f\nGives: $%d", HP, money);
	}
}
