package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class representing database records for each student.
 * 
 * @author Goran Brlas
 * @version 1.0
 */
public class StudentRecord {
	/**
	 * Student's official JMBAG.
	 */
	private String jmbag;
	/**
	 * Student's last name.
	 */
	private String lastName;
	/**
	 * Student's first name.
	 */
	private String firstName;
	/**
	 * Student's final grade.
	 */
	private int finalGrade;
	
	/**
	 * Constructor which takes a single line from the file and creates a new instance of StudentRecord if 
	 * the passed arguments are valid.
	 * @param line Single line read from the file.
	 * @throws IllegalArgumentException - if invalid line is passed.
	 */
	public StudentRecord(String line) throws IllegalArgumentException {
		String[] temp = line.split("\\t");
		
		if (temp.length < 4 || temp.length> 5) {
			throw new IllegalArgumentException("Invalid line!");
		}
		
		try {
			jmbag = temp[0].trim();
			lastName = temp[1].trim();
			firstName = temp[2];
			finalGrade = Integer.parseInt(temp[3]);
		} catch (Exception exception) {
			throw new IllegalArgumentException("Error in the database.");
		}
		
	}
	
	
	/**
	 * Boolean representation of whether the passed StudentRecord equals the caller.
	 * Two records are treated as equal if their jmbags are equal.
	 * @param student2 StudentRecord to be compared with the caller.
	 * @return True if passed StudentRecord equals the caller, false otherwise.
	 */
	public boolean equals (StudentRecord student2) {
		if (student2 == null) {
			return false;
		}
		
		if (this.hashCode() == student2.hashCode() && jmbag.equals(student2.getJmbag())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode () {
		int result = 20;
		
		return 37 * result + (int) (Integer.parseInt(jmbag) ^ (Integer.parseInt(jmbag) >>> 32));
	}
	
	/**
	 * Gets the jmbag of this StudentRecord.
	 * @return JMBAG of this StudentRecord.
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Gets the first name of this StudentRecord.
	 * @return Gets the name of this StudentRecord.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Gets the final grade of this StudentRecord. 
	 * @return Gets the final grade of this StudentRecord.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * Gets the last name of this StudentRecord.
	 * @return Gets the name of this StudentRecord.
	 */
	public String getLastName() {
		return lastName;
	}
	
	
}
