package hr.fer.zemris.java.tecaj.hw1;

import java.text.DecimalFormat;

/**
 * Takes user provided complex number and required root and then computes and prints all requested roots of given complex number.
 * 
 * @author Goran Brlas - 0036476746
 *
 */
public class Roots {
	
	/**
	 * Method called when starting the program.
	 * @param args Command line arguments.
	 */
	public static void main (String[] args) {
		double real, imaginary;
		int root, k;
		double r, angle;
		
		if (args.length != 3) {
			if (args.length != 1) {
				System.err.println("You have to enter valid 3 values, closing the program.");
				return;
			}
		}
		
		real = Double.parseDouble(args[0]);
		imaginary = Double.parseDouble(args[1]);
		root = Integer.parseInt(args[2]);
		
		r = Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
		angle = (Math.atan(imaginary/real));
		
		r = Math.pow(r, (1.0/root));
		
		System.out.println("You requested calculation of " + root + ". roots. Solutions are: ");
		
		for (k = 0; k < root; k++) {
			real = r*Math.cos((angle + 2*k*Math.PI)/root);
			imaginary = r*Math.sin((angle + 2*k*Math.PI)/root);
				
			System.out.print(k+1 + ") ");
			ispisi(real, "0.##");
			ispisi(imaginary, " + 0.##; - 0.##");
			System.out.println("i");
		}
		
	}
	
	/**
	 * Prints out the given number according to the given format.
	 * @param broj Number to be printed out.
	 * @param format Specifies how to print the given number.
	 */
	public static void ispisi (double number, String format) {
		DecimalFormat formatter = new DecimalFormat(format);
		
		System.out.print(formatter.format(number));
	}
	
}
