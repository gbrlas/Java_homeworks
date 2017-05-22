package hr.fer.zemris.java.student0036476746.hw06;

import hr.fer.zemris.java.student0036476746.hw06.CijeliBrojevi;

import java.util.Scanner;

/**
 * Asks the user for integer input and computes whether the provided number is even, odd or prime.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class CijeliBrojeviKonzola {
	
	/**
	 * Main method which starts the program.
	 * Takes no arguments.
	 * @param args - none.
	 */
	public static void main (String[] args) {
		String line;
		Scanner input = new Scanner(System.in);
		
		while (true) {
			System.out.println("Unesite broj: ");
			line = input.nextLine();
			line = line.replaceAll("\\s+", " ");
			
			if (line.equals("quit")) {
				System.out.println("Hvala na druzenju.");
				break;
			}
			
			try {
				int broj = Integer.parseInt(line);
				StringBuilder builder = new StringBuilder();
				
				builder.append("Paran: ");
				
				boolean paran = CijeliBrojevi.isEven(broj);
				if (paran) {
					builder.append("DA, neparan ");
				} else {
					builder.append("NE, neparan ");
				}
				
				boolean neparan = CijeliBrojevi.isOdd(broj);
				if (neparan) {
					builder.append("DA, prim: ");
				} else {
					builder.append("NE; prim: ");
				}
				
				boolean prim = CijeliBrojevi.isPrime(broj);
				if (prim) {
					builder.append("DA\n");
				} else {
					builder.append("NE\n");
				}
				
				System.out.println(builder.toString());
			} catch (Exception exception) {
				System.err.println("Invalid entry, try again.");
				continue;
			}
			
			
		}
		input.close();
	}
}
