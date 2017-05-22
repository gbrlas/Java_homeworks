package hr.fer.zemris.java.tecaj.hw1;

/**
 * Takes user provided number n and calculates n-th number of Hofstadter's Q sequence.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class HofstadterQ {
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main (String[] args) {
		int i;
		long result;
		
		if (args.length != 1) {
			System.err.println("You have to enter a valid number, closing the program.");
			return;
		}
		
		i = Integer.parseInt(args[0]);
		
		result = calculate(i);
		
		System.out.println("You requested calculation of " + i + ". number of Hofstadter's Q-sequence. The requested number is " + result + ".");
	}
	
	/**
	 * Calculates n-th number of Hofstadter's sequence.
	 * @param n Number of the sequence.
	 * @return n-th number of Hofstadter's sequence.
	 */
	public static long calculate(long n) {
		if (n == 1 || n == 2) 
			return 1;
		else 
			return calculate(n - calculate(n-1)) + calculate(n - calculate(n-2));
	}
}
