package hr.fer.zemris.java.tecaj.hw1;

/**
 * Takes a single command-line natural number greater than 1 and then calculates and prints the decomposition of this number onto prime factors.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class NumberDecomposition {
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		int number;
		
		if (args.length != 1) {
			System.err.println("You have to enter a valid number, closing the program.");
			return;
		}
		
		number = Integer.parseInt(args[0]);
		if (number <= 1) {
			System.err.println("You have to enter a number greater than 1, closing the program");
		}
		
		calculate(number);
	}
	
	/**
	 * Decomposes given number onto prime numbers and prints those prime numbers.
	 * @param number Number to be decomposed onto prime numbers.
	 */
	public static void calculate(int number) {
		int i = 2, counter = 1;
		
		while (true) {
			if (number % i == 0) {
				number /= i;
				System.out.println(counter + ". " + i);
				counter++;
				
				if (number == 1)
					break;
			} else 
				i++;
		}
	}

}
