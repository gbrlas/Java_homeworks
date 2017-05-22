package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Interface which represents a strategy for obtaining a requested field value from StudentRecord.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface IFieldValueGetter {
	/**
	 * StudentRecord getter.
	 * @param record from which to get a string value. 
	 * @return String value from the StudentRecord.
	 */
	public String get (StudentRecord record);
}
