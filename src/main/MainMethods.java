package main;

/**
 * This class holds any useful methods used for this TD.
 * 
 * @author Kevin Choi, Kevin Lau and Collier Jiang
 * @version January 20, 2010
 */
public class MainMethods {
	/**
	 * This calculates the distance from one point to another given point.
	 * 
	 * @param x1
	 *            the x value of the first point.
	 * @param y1
	 *            the y value of the first point.
	 * @param x2
	 *            the x value of the second point.
	 * @param y2
	 *            the y value of the second point.
	 * @return the distance calculated using the distance formula,
	 *         sqrt((x2-x1)^2 + (y2-y1)^2).
	 */
	public static double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
	}
}
