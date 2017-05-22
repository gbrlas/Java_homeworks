package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Interface which implements a strategy for accepting StudentRecords according to different queries.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface IFilter {
	/**
	 * Accepts or rejects the StudentRecord.
	 * @param record StudentRecord to test.
	 * @return Boolean representation of whether the StudentRecord is accepted.
	 */
	public boolean accepts (StudentRecord record);
}
