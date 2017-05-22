package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Concrete strategy class for the not equal operator.
 * Implements hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class OperatorNotEqual implements IComparisonOperator {
	@Override
	public boolean satisfied(String value1, String value2) {
		return !(value1.equals(value2));
	}
}