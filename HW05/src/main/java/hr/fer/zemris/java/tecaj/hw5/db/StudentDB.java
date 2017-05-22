package hr.fer.zemris.java.tecaj.hw5.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Program which reads the data from current directory from file database.txt.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class StudentDB {
	
	/**
	 * Main method which starts the program. Takes no arguments.
	 * Program ends when the user types "quit" (without the parentheses).
	 * @param args - none.
	 */
	public static void main (String[] args) {
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
			Scanner input = new Scanner(System.in);
			StudentDatabase database = new StudentDatabase(lines);
			
			String line;

			ArrayList<StudentRecord> records = null;
			QueryFilter filter = null;
			
			System.out.println("The program ends when you type \"quit\" (without the parentheses).");
			System.out.println();
			
			boolean end = true;
			while (end) {
				try {
					System.out.print("> ");
					line = input.nextLine();
					line = line.replaceAll("\\s+", " ");
					
					end = loop(records, filter, line, database);
					
				} catch (Exception exception) {
					System.err.println("There is something wrong with your query, try again.");
					continue;
				}
			}
			
			input.close();
			
		} catch (Exception exception) {
			System.out.println(exception.getLocalizedMessage());
		}
	}
	
	/**
	 * Method which does the querying and prints out the results of the query.
	 * @param records Reference to a null StudentRecord list.
	 * @param filter Reference to a null filter.
	 * @param line Line read from standard input.
	 * @param Reference to a initialized StudentDatabase.
	 * @return Boolean representing whether the program needs to end.
	 */
	private static boolean loop (ArrayList<StudentRecord> records, QueryFilter filter, String line, StudentDatabase database) {
		if (line.equals("quit")) {
			return false;
		}
		
		if (!line.trim().substring(0, 5).equals("query")) {
			System.out.println("Invalid query!");
			return true;
		}
		//line without "query "
		String temp;
		String temp2;
		temp = line.substring(6);
		temp2 = temp.replaceAll("\\s+", "");
		
		if (temp.length() == 0) {
			System.out.println("Invalid query!");
			return true;
		}
		
		if (temp2.contains("jmbag=") && temp2.length() < 20) {
			filter = new QueryFilter(temp);
			records = new ArrayList<StudentRecord>();
			
			String temp3 = "";
			
			for (int i = 0; i < temp2.length(); i++) {
				if (Character.isDigit(temp2.charAt(i))) {
					temp3 += temp2.charAt(i);
				}
			}
			
			filter.jmbagSearch(temp3);
			
			try {
				records.add(database.forJMBAG(filter.getValue()));
			} catch (Exception exception) {
				records.add(null);
			}
			
			if (records.get(0) == null) {
				Optional<String> optional = filter.getJMBAG();
				if (optional.isPresent()) {
					System.out.println("Using index for record retrieval.");
				}
				
				print2(0, 0);
				print2(0, 0);
			} else {
				print1(records, filter);
			}
			
			
			
		} else {
			filter = new QueryFilter(temp);
			
			records = (ArrayList<StudentRecord>) database.filter(filter);
			print1(records, filter);
		}
		
		return true;
	}
	
	
	/**
	 * Main print method used for printing out the results of the queries.
	 * @param list List of StudentRecords to be printed out according to expected format.
	 * @param filter Filter used to filter out the StudentRecords.
	 */
	public static void print1 (ArrayList<StudentRecord> list, QueryFilter filter) {
		int longestName = 0;
		int longestLastName = 0;
		
		for (StudentRecord record : list) {
			if (record.getFirstName().length() >= longestName) {
				longestName = record.getFirstName().length();
			}
			
			if (record.getLastName().length() >= longestLastName) {
				longestLastName = record.getLastName().length();
			}
		}
		
		Optional<String> optional = filter.getJMBAG();
		if (optional.isPresent()) {
			System.out.println("Using index for record retrieval.");
		}
		
		print2(longestName, longestLastName);
		for (StudentRecord record : list) {
			System.out.print("| " + record.getJmbag() + " | " + record.getLastName() + blank(record.getLastName(), longestLastName) + " | " + record.getFirstName() + blank(record.getFirstName(), longestName) + " | " + record.getFinalGrade() + " |");
			System.out.println();
		}
		print2(longestName, longestLastName);
		
		System.out.println("Records selected: " + list.size());
		System.out.println();
		
	}
	
	/**
	 * Prints the needed amount of blank spaces (size of the longest string - size of the test string).
	 * @param name1 Test string.
	 * @param longest Longest string.
	 * @return String consisting of blank spaces.
	 */
	public static String blank (String name1, int longest) {
		int size = longest - name1.length();
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < size; i++) {
			builder.append(" ");
		}
		
		return builder.toString();
	}
	
	/**
	 * Prints the needed surrounding string.
	 * @param longestName Int value of the longest first name.
	 * @param longestLastName Int value of the longest last name.
	 */
	public static void print2 (int longestName, int longestLastName) {
		System.out.print("+============+");
		for (int i = 0; i < longestLastName + 2; i++) {
			System.out.print("=");
		}
		System.out.print("+");
		for (int i = 0; i < longestName + 2; i++) {
			System.out.print("=");
		}
		System.out.print("+===+");
		System.out.println();
	}
	
}
