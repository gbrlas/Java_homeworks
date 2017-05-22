package hr.fer.zemris.java.tecaj.hw3.demo;

import hr.fer.zemris.java.tecaj.hw3.ComplexNumber;

/**
 * Tests the ComplexNumber class with the given example.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComplexNumberTester {
	
	/**
	 * Main method.
	 * @param args No arguments needed.
	 */
	public static void main(String[] args) {
		
		try {
			ComplexNumber c1 = new ComplexNumber(2, 3);
			ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
			ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
			
			System.out.println(c1);
			System.out.println(c2);
			System.out.println(c3);
		} catch (Exception exception) {
			System.err.println(exception.getLocalizedMessage());
		}

	}

}
