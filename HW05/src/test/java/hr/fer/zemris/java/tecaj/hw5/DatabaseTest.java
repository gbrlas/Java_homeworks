package hr.fer.zemris.java.tecaj.hw5;

import hr.fer.zemris.java.tecaj.hw5.db.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDB;
import hr.fer.zemris.java.tecaj.hw5.db.StudentDatabase;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the methods developed for problem1.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class DatabaseTest {
	//StudentRecord
	/**
	 * Constructor with valid line provided should return correct values.
	 */
	@Test
	public void studentRecordConstructorV1 () {
		StudentRecord record = new StudentRecord("0000000001	Akšamović	Marin	2");
		Assert.assertEquals(record.getFirstName(), "Marin");
		Assert.assertEquals(record.getLastName(), "Akšamović");
		Assert.assertEquals(record.getJmbag(), "0000000001");
		Assert.assertEquals(record.getFinalGrade(), 2);
	}
	
	
	/**
	 * Constructor with invalid line provided should throw an exception.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void studentRecordConstructorV2 () {
		@SuppressWarnings("unused")
		StudentRecord record = new StudentRecord("0000000001	Akšamović	Marin\\tMarko	Ivan\\t2");
	}
	
	/**
	 * Constructor with invalid line provided should throw an exception.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void studentRecordConstructorV3 () {
		@SuppressWarnings("unused")
		StudentRecord record = new StudentRecord("0000000001	Akšamović	Marin	2a");
	}
	
	
	/**
	 * Two records with same jmbag should be equal.
	 */
	@Test
	public void studentRecordEqualsV1 () {
		StudentRecord record = new StudentRecord("0000000001	Akšamović	Marin	2");
		StudentRecord record2 = new StudentRecord("0000000001	Akšamović	Marin	2");
		
		Assert.assertEquals(record.equals(record2), true);
	}
	
	
	/**
	 * Two records with different jmbag should not be equal.
	 */
	@Test
	public void studentRecordEqualsV2 () {
		StudentRecord record = new StudentRecord("0000000001	Akšamović	Marin	2");
		StudentRecord record2 = new StudentRecord("0000000002	Akšamović	Marin	2");
		
		Assert.assertEquals(record.equals(record2), false);
	}
	
	
	/**
	 * Comparing with null record should throw an exception.
	 */
	@Test 
	public void studentRecordEqualsV3 () {
		StudentRecord record = new StudentRecord("0000000001	Akšamović	Marin	2");
		StudentRecord record2 = null;
		
		Assert.assertEquals(record.equals(record2), false);
	}
	
	//student database
	
	
	/**
	 * Constructor with valid entries provided should create an internal array list of records.
	 */
	@Test
	public void studentDatabaseConstructorV1 () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			
			Assert.assertEquals(database.getRecords().isEmpty(), false);
			Assert.assertEquals(database.getMap().isEmpty(), false);
			
		} catch (Exception exception) {
			return;
		}
	}
	
	/**
	 * Constructor should throw an exception if no entries are provided.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void studentDatabaseConstructorV2 () {
		List<String> lines = new ArrayList<String>();
		@SuppressWarnings("unused")
		StudentDatabase database = new StudentDatabase(lines);
	}
	
	@Test
	/**
	 * Tests the filter method by parsing a query and returning StudentRecords which satisfy the query.
	 */
	public void filter () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			QueryFilter filter = new QueryFilter("firstName >= Ivan");
			
			ArrayList<StudentRecord> records = (ArrayList<StudentRecord>) database.filter(filter);
			
			Assert.assertEquals(records.isEmpty(), false);
			
		} catch (Exception exception) {
			return;
		}
	}
	
	/**
	 * ForJMBAG should return null if jmbag is not in the database.
	 */
	@Test
	public void forJMBAGV1 () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			
			Assert.assertEquals(database.forJMBAG("0036476746"), null);
			
		} catch (Exception exception) {
			return;
		}
	}
	
	
	/**
	 * ForJMBAG should return correct student record if it is in the database.
	 */
	@Test
	public void forJMBAGV2 () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			
			Assert.assertEquals(database.forJMBAG("0000000044").equals(new StudentRecord("0000000044	Pilat	Ivan	5")), true);
			
		} catch (Exception exception) {
			return;
		}
	}
	
	//query filter
	
	
	/**
	 * Constructor should take the correct value.
	 */
	@Test
	public void queryFilterConstructor () {
		QueryFilter filter = new QueryFilter("= \"Ivan\"");
		Assert.assertEquals(filter.getQuery(), "= \"Ivan\"");
	}
	
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test
	public void queryFilterEqualsV1 () {
		QueryFilter filter = new QueryFilter("firstName = Ivan and lastName = Marić");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test
	public void queryFilterEqualsV2 () {
		QueryFilter filter = new QueryFilter("lastName = Ivan");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000029	Kos Grabar	Ivo	2")), false);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test
	public void queryFilterEqualsV3 () {
		QueryFilter filter = new QueryFilter("lastName = Marić");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test
	public void queryFilterEqualsV4 () {
		QueryFilter filter = new QueryFilter("jmbag = 0000000035");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void queryFilterEqualsV5 () {
		QueryFilter filter = new QueryFilter("firstName = \"D**\"");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test 
	public void queryFilterEqualsV6 () {
		QueryFilter filter = new QueryFilter("firstName = \"I*\"");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test 
	public void queryFilterEqualsV7 () {
		QueryFilter filter = new QueryFilter("firstName = \"I*na\"");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test 
	public void queryFilterEqualsV8 () {
		QueryFilter filter = new QueryFilter("firstName = \"Ir*na\"");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void queryFilterEqualsV9 () {
		QueryFilter filter = new QueryFilter("finalGrade = 4");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with equals operator and O(1) search.
	 */
	@Test
	public void queryFilterEqualsV10 () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			
			QueryFilter filter = new QueryFilter("jmbag = 0000000035");
			filter.jmbagSearch("0000000035");
			Assert.assertEquals(database.forJMBAG(filter.getValue()).equals(new StudentRecord("0000000035	Marić	Ivan	4")), true);
		} catch (Exception exception) {
			return;
		}
	}
	
	/**
	 * Tests the QueryFilter class with greater or equal operator.
	 */
	@Test
	public void queryFilterGreaterOrEqualV1 () {
		QueryFilter filter = new QueryFilter("firstName >= Iva");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with greater or equal operator.
	 */
	@Test
	public void queryFilterGreaterOrEqualV2 () {
		QueryFilter filter = new QueryFilter("firstName >= Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with lower or equal operator.
	 */
	@Test
	public void queryFilterLowerOrEqualV1 () {
		QueryFilter filter = new QueryFilter("firstName <= Iva");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with lower or equal operator.
	 */
	@Test
	public void queryFilterLowerOrEqualV2 () {
		QueryFilter filter = new QueryFilter("firstName <= Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with greater operator.
	 */
	@Test
	public void queryFilterGreaterV1 () {
		QueryFilter filter = new QueryFilter("firstName > Iva");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with greater operator.
	 */
	@Test
	public void queryFilterGreaterV2 () {
		QueryFilter filter = new QueryFilter("firstName > Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with lower operator.
	 */
	@Test
	public void queryFilterLowerV1 () {
		QueryFilter filter = new QueryFilter("firstName < Iva");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with lower operator.
	 */
	@Test
	public void queryFilterLowerV2 () {
		QueryFilter filter = new QueryFilter("firstName < Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with not equal operator.
	 */
	@Test
	public void queryFilterNotEqualV1 () {
		QueryFilter filter = new QueryFilter("firstName != Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Ivan	4")), true);
	}
	
	/**
	 * Tests the QueryFilter class with not equal operator.
	 */
	@Test
	public void queryFilterNotEqualV2 () {
		QueryFilter filter = new QueryFilter("firstName != Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Marko	4")), false);
	}
	
	/**
	 * Tests the QueryFilter class with wrong operator provided.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void queryFilterWrongOperator () {
		QueryFilter filter = new QueryFilter("firstName @ Marko");
		Assert.assertEquals(filter.accepts(new StudentRecord("0000000035	Marić	Marko	4")), false);
	}
	
	//StudentDB
	/**
	 * Tests the print2() method.
	 */
	@Test
	public void print2 () {
		StudentDB.print2(5, 10);
	}
	
	/**
	 * Tests the blank() method.
	 */
	@Test
	public void blank () {
		StudentDB.blank("Goran", 10);
	}
	
	/**
	 * Tests the print1() method with normal query.
	 */
	@Test
	public void print1V1 () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			QueryFilter filter = new QueryFilter("firstName >= Ivan");
			
			ArrayList<StudentRecord> records = (ArrayList<StudentRecord>) database.filter(filter);
			
			StudentDB.print1(records, filter);
			
		} catch (Exception exception) {
			return;
		}
	}
	
	/**
	 * Tests the print1() method with O(1) query.
	 */
	@Test
	public void print1V2 () {
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			StudentDatabase database = new StudentDatabase(lines);
			QueryFilter filter = new QueryFilter("jmbag = 0000000033");
			
			ArrayList<StudentRecord> records = (ArrayList<StudentRecord>) database.filter(filter);
			
			StudentDB.print1(records, filter);
			
		} catch (Exception exception) {
			return;
		}
	}
	
	//main test
	/**
	 * Tests the main StudentDB class program using queries located in test.txt file.
	 */
	@Test
	public void mainTest () {
		try {
			System.setIn(new FileInputStream("./test.txt"));
			
			StudentDB.main(null);
		} catch (Exception exception) {
			return;
		}
	}
}
