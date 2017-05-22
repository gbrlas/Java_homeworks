package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.operators.*;

import java.util.Optional;

/**
 * Class which does the filtering of the StudentRecords.
 * Implements hr.fer.zemris.java.tecaj.hw5.db.IFilter.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * String representing the database query.
	 */
	private String query;
	/**
	 * Index representing whether the database is being searched by index.
	 */
	private boolean index = false;
	/**
	 * String representing the jmbag value.
	 */
	private String value;
	
	/**
	 * Constructor which sets the QueryFilter string query to the given value.
	 * @param query Query to be set as class default.
	 */
	public QueryFilter(String query) {
		this.query = query;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		ConditionalExpression[] expressions = parseQuery();
		
		boolean recordSatisfies = true;
		
		if (expressions == null) {
			throw new IllegalArgumentException();
		}
		
		for (ConditionalExpression exp : expressions) {
			recordSatisfies = exp.getComparisonOperator().satisfied(exp.getFieldGetter().get(record), exp.getStringLitteral());
			if (recordSatisfies == false) {
				break;
			}
		}
		
		value = record.getJmbag();
		
		return recordSatisfies;
	}
	
	/**
	 * Method which models an optional result if the database is being queried by jmbag.
	 * @return Value of the jmbag.
	 */
	public Optional<String> getJMBAG () {
		if (!index) {
			return Optional.empty();
		} else {
			return Optional.of(value);
		}
	}
	
	
	/**
	 * Parses the given query.
	 * @return Array of ConditionalExpressions.
	 */
	private ConditionalExpression[] parseQuery () {
		//get subqueries if there are any
		String[] subquerries = query.split(" and ");
		ConditionalExpression[] tempExpressions = new ConditionalExpression[subquerries.length];
		
		try {
			if (subquerries.length == 1) {
				tempExpressions[0] = singleSubQuery(subquerries[0]);
			} else {
				int index = 0;
				for (String query : subquerries) {
					tempExpressions[index++] = singleSubQuery(query);
				}
			}
			
			return tempExpressions;
		} catch (Exception exception) {
			System.err.println("Invalid query!");
			return null;
		}
		
		
	}
	
	/**
	 * Method processing a single subquery.
	 * @param query Query to be parsed.
	 * @return A single ConditionalExpression to be added to the array.
	 * @throws IllegalArgumentException - if invalid query.
	 */
	private ConditionalExpression singleSubQuery (String query) throws IllegalArgumentException {
		//get the type of the query and pass the correct substring
		if (query.contains("jmbag")) {
			return parseOperator(query.substring(5), 0);
		} else if (query.contains("firstName")) {
			return parseOperator(query.substring(9), 1);
		} else if (query.contains("lastName")) {
			return parseOperator(query.substring(8), 2);
		} else {
			throw new IllegalArgumentException("Illegal query!");
		}
	}
	
	/**
	 * Helper method for processing a single subquery.
	 * @param query Query to be parsed.
	 * @param caller Caller of the method (0 - jmbag, 1 - firstName, 2 - lastName).
	 * @return A single ConditionalExpression to be added to the array.
	 */
	private ConditionalExpression parseOperator (String query, int caller) {
		String temp = query.trim().replace("\"", "");
		
		//call different methods which send different IFieldValueGetter to the parseHelper()
		//according to the caller
		if (caller == 0) {
			return parseJmbag(temp);
		} else if (caller == 1) {
			return parseFirstName(temp);
		} else {
			return parseLastName(temp);
		}
		
	}
	
	 /**
	  * Parses the query which searches by jmbag.
	  * @param query Query to be parsed.
	  * @return A single ConditionalExpression to be added to the array.
	  */
	public ConditionalExpression parseJmbag (String query) {
		return parseHelper(query, new JmbagValueGetter());
	}
	
	/**
	  * Parses the query which searches by lastName.
	  * @param query Query to be parsed.
	  * @return A single ConditionalExpression to be added to the array.
	  */
	public ConditionalExpression parseLastName (String query) {
		return parseHelper(query, new LastNameValueGetter());
	}
	
	/**
	  * Parses the query which searches by firstName.
	  * @param query Query to be parsed.
	  * @return A single ConditionalExpression to be added to the array.
	  */
	public ConditionalExpression parseFirstName (String query) {
		return parseHelper(query, new FirstNameValueGetter());
	}
	
	/**
	 * Creates needed ConditionalExpression according to the given query and IFieldValueGetter
	 * @param query Query to be parsed.
	 * @param field Obtains a requested field value from StudentRecord.
	 * @return ConditionalExpression to be added to the array.
	 * @throws IllegalArgumentException - if invalid operator provided.
	 */
	public ConditionalExpression parseHelper (String query, IFieldValueGetter field) throws IllegalArgumentException {
		if (query.charAt(0) == '<' && query.charAt(1) != '=') {
			return new ConditionalExpression(field, query.substring(1).trim(), new OperatorLower());
		} else if (query.charAt(0) == '<' && query.charAt(1) == '=') {
			return new ConditionalExpression(field, query.substring(2).trim(), new OperatorLowerOrEqual());
		} else if (query.charAt(0) == '>' && query.charAt(1) != '=') {
			return new ConditionalExpression(field, query.substring(1).trim(), new OperatorGreater());
		} else if (query.charAt(0) == '>' && query.charAt(1) == '=') {
			return new ConditionalExpression(field, query.substring(2).trim(), new OperatorGreaterOrEqual());
		} else if (query.charAt(0) == '=' && (query.charAt(1) == ' ' || query.charAt(1) == '\"' || Character.isDigit(query.charAt(1)) || Character.isLetter(query.charAt(1)))) {
//			if (field instanceof JmbagValueGetter) {
//				index = true;
//			}
			return new ConditionalExpression(field, query.substring(1).trim(), new OperatorEquals());
		} else if (query.charAt(0) == '!' && query.charAt(1) == '=')
			return new ConditionalExpression(field, query.substring(2).trim(), new OperatorNotEqual());
		else {
			throw new IllegalArgumentException("Illegal comparison operator");
		}
	}
	
	/**
	 * Sets the value to the provided jmbag and index to true - used for O(1) search of the database.
	 * @param jmbag Jmbag being searched.
	 */
	public void jmbagSearch (String jmbag) {
		value = jmbag;
		index = true;
	}
	
	/**
	 * Gets the filter query.
	 * @return Query to be filtered.
	 */
	public String getQuery () {
		return query;
	}
	
	/**
	 * Gets the jmbag value if searched by jmbag.
	 * @return Jmbag.
	 */
	public String getValue () {
		return value;
	}
	
	
	
	/**
	 * Class which obtains the jmbag value.
	 * Implements hr.fer.zemris.java.tecaj.hw5.db.IFieldValueGetter.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private class JmbagValueGetter implements IFieldValueGetter {
		
		@Override
		/**
		 * Getter for the record jmbag.
		 */
		public String get(StudentRecord record) {
			return record.getJmbag();
		}
	}
	
	/**
	 * Class which obtains the firstName value.
	 * Implements hr.fer.zemris.java.tecaj.hw5.db.IFieldValueGetter.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private class FirstNameValueGetter implements IFieldValueGetter {
		
		@Override
		/**
		 * Getter for the record firstName.
		 */
		public String get(StudentRecord record) {
			return record.getFirstName();
		}
	}
	
	/**
	 * Class which obtains the lastName value.
	 * Implements hr.fer.zemris.java.tecaj.hw5.db.IFieldValueGetter.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private class LastNameValueGetter implements IFieldValueGetter {
		
		@Override
		/**
		 * Getter for the record lastName.
		 */
		public String get(StudentRecord record) {
			return record.getLastName();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
