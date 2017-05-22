package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Class which represents the student database.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class StudentDatabase {
	/**
	 * Array list of StudentRecords.
	 */
	private ArrayList<StudentRecord> records;
	/**
	 * Map with jmbag as key and storing index as value.
	 */
	private TreeMap<String, Integer> map;
	
	/**
	 * Constructor which sets the records list according to the given list of entries.
	 * @param entries List of entries to be added into the StudentRecords list.
	 * @throws IllegalArgumentException if the passed in list is empty.
	 */
	public StudentDatabase(List<String> entries) throws IllegalArgumentException {
		if (entries.size() == 0) {
			throw new IllegalArgumentException("The database cannot be created, no entries passed in!");
		}
		
		records = new ArrayList<StudentRecord>();
		map = new TreeMap<String, Integer>();

		StudentRecord studentRecord;
		int index = 0;
		
		boolean test = true;
		for (String student : entries) {
			studentRecord = new StudentRecord(student);
			for (StudentRecord record : records) {
				if (record.equals(studentRecord)) {
					test = false;
				}
			}
			
			if (test) {
				records.add(studentRecord);
				map.put(studentRecord.getJmbag(), index++);
			}
		}
	}
	
	/**
	 * Returns record at the index of the StudentRecord with the given jmbag in the database list.
	 * @param jmbag jmbag of the searched for StudentRecord.
	 * @return Studetn record withe the given jmbag.
	 */
	public StudentRecord forJMBAG (String jmbag) {
		return records.get(map.get(jmbag));
	}
	
	/**
	 * Filters the elements in the student database and returns the ones matching the filter.
	 * @param filter Filter used for filtering the list.
	 * @return List of StudentRecords which passed the filter.
	 */
	public List<StudentRecord> filter (IFilter filter) {
		ArrayList<StudentRecord> temp = new ArrayList<StudentRecord>();
		
		for (StudentRecord element : records) {
			if (filter.accepts(element)) {
				temp.add(element);
			}
		}
		
		return temp;
	}
	
	/**
	 * Gets the array list of records.
	 * @return Array list of records.
	 */
	public ArrayList<StudentRecord> getRecords() {
		return records;
	}
	
	/**
	 * Gets the hash map.
	 * @return Hash map.
	 */
	public TreeMap<String, Integer> getMap() {
		return map;
	}
	
}
