package hr.fer.zemris.java.tecaj.hw3.demo;

import hr.fer.zemris.java.tecaj.hw3.IntegerSequence;

/**
 * Tests the IntegerSequence class with the given example.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class IntegerSequenceTester {
	
	/**
	 * Main method.
	 * @param args No arguments needed.
	 */ 
	public static void main (String[] args) {
		
		try {
			IntegerSequence range = new IntegerSequence(1, 11, 3);
			
			for (int i : range) {
				for (int j : range) {
					System.out.println("i = " + i + ", j = " + j);
				}
			}
			
			range = new IntegerSequence(10, 1, -1);
			for (int i : range) {
				System.out.println(i);
			}
			
		} catch (Exception exception) {
			System.err.println(exception.getLocalizedMessage());
		}
				
	}
}
