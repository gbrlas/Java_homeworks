package hr.fer.zemris.java.student0036476746.hw06;
/**
 * Class which provides functions for calculating whether a number is odd, even, or prime.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CijeliBrojevi {
	
	/**
	 * Method which calculates whether the provided number is odd.
	 * @param number Number to be evaluated.
	 * @return Boolean representation of whether a number is odd.
	 */
	public static boolean isOdd (int number) {
		return (number % 2 == 1);
	}
	
	/**
	 * Method which calculates whether the provided number is even.
	 * @param number Number to be evaluated.
	 * @return Boolean representation of whether a number is even.
	 */
	public static boolean isEven (int number) {
		return (number % 2 == 0);
	}
	
	/**
	 * Method which calculates whether the provided number is prime.
	 * @param number Number to be evaluated.
	 * @return Boolean representation of whether a number is prime.
	 */
	public static boolean isPrime (int number) {
		boolean test = true;
		
		for (int i = 2; i < Math.floor(Math.sqrt(number)) + 1; i++) {
			if (number % i == 0)
				test = false;
		}
		
		return test;
	}
}
