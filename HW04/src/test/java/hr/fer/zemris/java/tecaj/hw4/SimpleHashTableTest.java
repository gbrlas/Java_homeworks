package hr.fer.zemris.java.tecaj.hw4;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;

import org.junit.Test;
import org.junit.Assert;

/**
 * Tests for the SimpleHashtable methods.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class SimpleHashTableTest {
	
	//constructors
	@Test 
	/**
	 * Empty constructor should create an empty hash table.
	 */
	public void constructorTestV1 () {
		SimpleHashtable table = new SimpleHashtable();
		Assert.assertEquals(table.size(), 0);
	}
	
	@Test
	/**
	 * Not empty constructor should create an empty hash table with correct size.
	 */
	public void constructorTestV2 () {
		SimpleHashtable table = new SimpleHashtable(4);
		Assert.assertEquals(table.size(), 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	/**
	 * Constructor should throw an exception if negative size is passed.
	 */
	public void constructorTestV3 () {
		SimpleHashtable table = new SimpleHashtable(-2);
		table.clear();
	}
	
	//put
	@Test (expected = IllegalArgumentException.class)
	/**
	 * Put should throw an exception if null key is passed.
	 */
	public void putTestV1 () {
		SimpleHashtable table = new SimpleHashtable();
		table.put(null, 2);
	}
	
	@Test
	/**
	 * Put should put the object in the hash table correctly.
	 */
	public void putTestV2 () {
		SimpleHashtable table = new SimpleHashtable();
		table.put("Goran", 7);
		Assert.assertEquals(table.containsKey("Goran"), true);
	}
	
	@Test
	/**
	 * Put should put the object in the hash table correctly (as a part of the list).
	 */
	public void putTestV3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsKey("Kristina"), true);
	}
	
	//get
	@Test
	/**
	 * Get should return null if null is passed as the key.
	 */
	public void getTestV1 () {
		SimpleHashtable table = new SimpleHashtable();
		table.put("Goran", 7);
		Assert.assertEquals(table.get(null), null);
	}
	
	@Test
	/**
	 * Get should return correct value if the key is in the hash table.
	 */
	public void getTestV2 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.get("Kristina"), 5);
	}
	
	@Test
	/**
	 * Get should return correct value if the key is in the hash table.
	 */
	public void getTestV3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.get("Ante"), 2);
	}
	
	//size
	@Test
	/**
	 * Size should return correct hash table size.
	 */
	public void sizeTest () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.size(), 4);
	}
	
	//containsKey
	@Test
	/**
	 * Contains should return correct boolean value if the key is in the hash table.
	 */
	public void containsKeyTestV1 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsKey("Jasna"), true);
	}
	
	@Test
	/**
	 * Contains should return correct boolean value if the key is in the hash table.
	 */
	public void containsKeyTestV2 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsKey("Ante"), true);
	}
	
	@Test
	/**
	 * Contains should return correct boolean value if the passed key is null.
	 */
	public void containsKeyTestV3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsKey(null), false);
	}
	
	//containsValue
	@Test
	/**
	 * Contains should return correct boolean value if the given value is in the hash table.
	 */
	public void containsValueTestV1 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsValue(2), true);
	}
	
	@Test
	/**
	 * Contains should return correct boolean value if the given value is in the hash table.
	 */
	public void containsValueTestV2 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsValue(5), true);
	}
	
	@Test
	/**
	 * Contains should return correct boolean value if the given value is null.
	 */
	public void containsValueTest3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.containsValue(null), false);
	}
	
	//remove
	@Test
	/**
	 * Remove should remove the correct element from the hash table.
	 */
	public void removeTestV1 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.remove("Ante");
		Assert.assertEquals(examMarks.containsKey("Ante"), false);
	}
	
	@Test
	/**
	 * Remove should remove the correct element from the hash table.
	 */
	public void removeTestV2 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.remove("Ivana");
		Assert.assertEquals(examMarks.containsKey("Ivana"), false);
	}
	
	@Test
	/**
	 * Remove should remove the correct element from the hash table.
	 */
	public void removeTestV3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.remove("Jasna");
		Assert.assertEquals(examMarks.containsKey("Jasna"), false);
	}
	
	@Test
	/**
	 * Remove should not remove anything if the given key is not in the hash table.
	 */
	public void removeTestV4 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.remove("Ante");
		Assert.assertEquals(examMarks.size(), 1);
	}
	
	//isEmpty
	@Test
	/**
	 * IsEmpty should return the correct boolean value.
	 */
	public void isEmptyTestV1 () {
		SimpleHashtable table = new SimpleHashtable();
		Assert.assertEquals(table.isEmpty(), true);
	}
	
	@Test
	/**
	 * IsEmpty should return the correct boolean value.
	 */
	public void isEmptyTestV2 () {
		SimpleHashtable table = new SimpleHashtable();
		table.put("Ivana", Integer.valueOf(2));
		Assert.assertEquals(table.isEmpty(), false);
	}
	
	//toString 
	@Test
	/**
	 * ToString should print out the correct string.
	 */
	public void toStringTest () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		Assert.assertEquals(examMarks.toString().equals("[Ante=2, Ivana=5, Jasna=2, Kristina=5]"), true);
	}
	
	//clear
	@Test
	/**
	 * Clear should clear the entire table
	 */
	public void clearTestV1 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.clear();
		Assert.assertEquals(examMarks.containsKey("Ante"), false);
	}
	
	@Test
	/**
	 * Clear should clear the entire table
	 */
	public void clearTestV2 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.clear();
		Assert.assertEquals(examMarks.size(), 0);
	}
	
	@Test
	/**
	 * Clear should clear the entire table
	 */
	public void clearTestV3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.clear();
		Assert.assertEquals(examMarks.isEmpty(), true);
	}
	
	//iteration tests
	@Test
	/**
	 * Iteration should result with the correct output if nothing is changed during said iteration.
	 */
	public void iterationTestV1 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		
		StringBuilder builder = new StringBuilder();
		for (SimpleHashtable.TableEntry pair : examMarks) {
			builder.append(pair.getKey() + " => " + pair.getValue() + "  ");
		}
		
		Assert.assertEquals(builder.toString(), "Ante => 2  Ivana => 5  Jasna => 2  Kristina => 5  ");
	}
	
	@Test
	/**
	 * Iteration should result with the correct output if an element is removed from the hash table
	 * by the iterator, not by the user.
	 */
	public void iterationTestV2 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		
		Iterator<SimpleHashtable.TableEntry> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
			}
			
		}
		
		StringBuilder builder = new StringBuilder();
		for (SimpleHashtable.TableEntry pair : examMarks) {
			builder.append(pair.getKey() + " => " + pair.getValue() + "  ");
		}
		
		Assert.assertEquals(builder.toString(), "Ante => 2  Jasna => 2  Kristina => 5  ");
	}
	
	@Test (expected = ConcurrentModificationException.class)
	/**
	 * If the user changes the table during iteration, an exception should be thrown.
	 */
	public void iterationTestV3 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		
		Iterator<SimpleHashtable.TableEntry> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
			
		}
	}
	
	@Test (expected = IllegalStateException.class)
	/**
	 * If the iterator removes the same element more than once an exception should be thrown.
	 */
	public void iterationTestV4 () {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		
		Iterator<SimpleHashtable.TableEntry> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
			
		}
	}
	
}
