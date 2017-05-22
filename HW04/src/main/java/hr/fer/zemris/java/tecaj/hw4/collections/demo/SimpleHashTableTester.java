package hr.fer.zemris.java.tecaj.hw4.collections.demo;

import java.util.Iterator;

import hr.fer.zemris.java.tecaj.hw4.collections.SimpleHashtable;

/**
 * Class which tests the SimpleHashTable.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
@SuppressWarnings("unused")
public class SimpleHashTableTester {
	
	public static void main (String[] args) {
		SimpleHashtable examMarks = new SimpleHashtable(2);
		
		examMarks.put("Ivana", Integer.valueOf(2));
		examMarks.put("Ante", Integer.valueOf(2));
		examMarks.put("Jasna", Integer.valueOf(2));
		examMarks.put("Kristina", Integer.valueOf(5));
		examMarks.put("Ivana", Integer.valueOf(5));
		
//		Integer kristinaGrade = (Integer) examMarks.get("Kristina");
//		System.out.println("Kristina's exam grade is: " + kristinaGrade);
//		
//		System.out.println(examMarks.size());
//		
//		System.out.println(examMarks);
		
		for (SimpleHashtable.TableEntry pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}
		
//		for (SimpleHashtable.TableEntry pair1 : examMarks) {
//			for (SimpleHashtable.TableEntry pair2 : examMarks) {
//				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(), pair2.getValue());
//			}
//		}
		
//		Iterator<SimpleHashtable.TableEntry> iter = examMarks.iterator();
		
//		try {
//			while (iter.hasNext()) {
//				SimpleHashtable.TableEntry pair = iter.next();
//				if (pair.getKey().equals("Ivana")) {
//					examMarks.remove("Ivana");;
//				}
//				
//			}
//			
//			System.out.println(examMarks);
//		} catch (Exception exception) {
//			System.err.println(exception.getMessage());
//		}
	}

	
}
