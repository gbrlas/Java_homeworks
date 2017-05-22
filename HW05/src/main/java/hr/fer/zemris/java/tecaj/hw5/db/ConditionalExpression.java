package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;



/**
 * Class which represents the complete conditional expression.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ConditionalExpression {
	/**
	 * A reference to the IComparisonOperator strategy.
	 */
	private IComparisonOperator comparisonOperator;
	/**
	 * A reference to the IFieldValueGetter strategy.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * Query string.
	 */
	private String stringLiteral;
	
	/**
	 * Constructor which sets the strategy references and the string literal to the given values.
	 * @param fieldStrategy IFieldValueGetter strategy.
	 * @param literal IFieldValueGetter strategy.
	 * @param operator IComparisonOperator strategy.
	 */
	public ConditionalExpression(IFieldValueGetter fieldStrategy, String literal, IComparisonOperator operator) {
		comparisonOperator = operator;
		fieldGetter = fieldStrategy;
		stringLiteral = literal;
	}
	
	/**
	 * Getter for the IComparisonOperator reference. 
	 * @return IComparisonOperator reference. 
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	/**
	 * Getter for the IFieldValueGetter reference.
	 * @return IFieldValueGetter reference.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Getter for the query string.
	 * @return Query string.
	 */
	public String getStringLitteral() {
		return stringLiteral;
	}
	
	
}	
