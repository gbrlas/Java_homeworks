package hr.fer.zemris.java.tecaj.hw1;

/**
 * Takes a single command-line number n and then computes and prints first n prime numbers.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class PrimeNumbers {
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int n;
		
		if (args.length != 1) {
			System.err.println("You have to enter a single command-line argument, closing the program.");
			return;
		}
		n = Integer.parseInt(args[0]);
		
		System.out.println("You requested calculation of " + n + " prime numbers. Here they are: ");
		calculatePrime(n);
	}
	
	/**
	 * Calculates and prints out first n prime numbers.
	 * @param n Number of prime numbers to be printed out.
	 */
	public static void calculatePrime (int n) {
		int i = 2, j, counter = 0, test;
		
		if (n < 1) {
			System.out.println("Invalid number, program is closing.");
			return;
		}
		
		while (true) {
			test = 0;
			for (j = 2; j < n; j++) {
				if (i % j == 0 && i > j)
					test = 1;
			}
				
			if (test == 0) {
				System.out.println((counter+1) + ". " + i);
				counter++;
				if (counter == n)
					break;
			}
			
			i++;
		}
		
	}
}
