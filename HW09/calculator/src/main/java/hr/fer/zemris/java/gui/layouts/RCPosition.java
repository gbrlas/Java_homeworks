package hr.fer.zemris.java.gui.layouts;

/**
 * Class representing element location constraints.
 * Offers two read-only properties: row and column.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class RCPosition {
	/**
	 * Row index in the layout.
	 */
	private int row;
	/**
	 * Column index in the layout.
	 */
	private int column;
	
	/**
	 * Constructor which sets the row and column index according to the provided values.
	 * @param row Row in the layout.
	 * @param column Column in the layout.
	 * @throws IllegalArgumentException - if provided arguments are lower than zero.
	 */
	public RCPosition(int row, int column) {
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException("Row and column values must be positive!");
		}
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Gets the row index.
	 * 
	 * @return Row index.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Gets the column index.
	 * 
	 * @return Column index.
	 */
	public int getColumn() {
		return column;
	}
	
}
