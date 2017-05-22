package hr.fer.zemris.java.tecaj.hw5.db.operators;

import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;

/**
 * Concrete strategy class for the equal operator.
 * Implements hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class OperatorEquals implements IComparisonOperator {
	@Override
	public boolean satisfied(String value1, String value2) throws IllegalArgumentException {
		if (!value2.contains("*")) {
			return (value1.equals(value2));
		} else {
			int test = 0;
			
			for (int i = 0; i < value2.length(); i++) {
				if (value2.charAt(i) == '*') {
					test++;
				}
			}
			
			if (test > 1) {
				throw new IllegalArgumentException();
			} else {
				boolean test2 = true;
				
				for (int i = 0; i < Math.min(value1.length(), value2.length()); i++) {
					if (value1.charAt(i) != value2.charAt(i) && value2.charAt(i) != '*') {
						test2 = false;
						break;
					} else if (value2.charAt(i) == '*') {
						if (!value1.endsWith(value2.substring(i+1))) {
							test2 = false;
							break;
						}
						
						break;
					}
				}
				
				if (test2) {
					return true;
				} else {
					return false;
				}
			}
		}
	}
}