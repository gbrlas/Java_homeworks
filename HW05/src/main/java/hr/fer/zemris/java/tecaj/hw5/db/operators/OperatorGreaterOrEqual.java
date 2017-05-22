package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Concrete strategy class for the greater or equal operator.
 * Implements hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class OperatorGreaterOrEqual implements IComparisonOperator {
	@Override
	public boolean satisfied(String value1, String value2) {
		if (value2.compareTo(value1) <= 0) {
			return true;
		} else {
			return false;
		}
	}
}