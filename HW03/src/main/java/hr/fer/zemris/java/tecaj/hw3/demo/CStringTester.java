package hr.fer.zemris.java.tecaj.hw3.demo;

import hr.fer.zemris.java.tecaj.hw3.CString;

/**
 * Tests the CString class with the given example.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CStringTester {
	
	/**
	 * Main method.
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		
		try {
			CString c = new CString("ababab");
			CString c1 = c.substring(1, 3).substring(0, 2);

			System.out.println(c1);
			System.out.println(c);
			c = c.replaceAll(new CString("ab"), new CString("abab"));
			System.out.println(c);
		} catch (Exception exception) {
			System.err.println(exception.getLocalizedMessage());
		}
		
	}

}