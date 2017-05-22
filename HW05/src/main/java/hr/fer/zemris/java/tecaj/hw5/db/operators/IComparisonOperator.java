package hr.fer.zemris.java.tecaj.hw5.db.operators;

/**
 * Interface which represents the strategy for comparing two string values.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface IComparisonOperator {
	/**
	 * Boolean representation of whether two given strings are the same.
	 * @param value1 String value.
	 * @param value2 String value.
	 * @return True if the comparison is satisfied, false otherwise.
	 */
	public boolean satisfied (String value1, String value2);
}
