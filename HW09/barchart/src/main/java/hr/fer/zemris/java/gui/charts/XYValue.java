package hr.fer.zemris.java.gui.charts;

/**
 * Class representing bar chart values. Holds an x-axis value and a y-axis
 * value.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class XYValue {
	/**
	 * X-axis value.
	 */
	private int x;
	/**
	 * Y-axis value.
	 */
	private int y;

	/**
	 * Constructor which sets the initial values to the provided ones.
	 * 
	 * @param x
	 *            X-axis value.
	 * @param y
	 *            Y-axis value.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * X-axis value getter.
	 * 
	 * @return X-axis value.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Y-axis value getter.
	 * 
	 * @return Y-axis value.
	 */
	public int getY() {
		return y;
	}

}
