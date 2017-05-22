package hr.fer.zemris.java.simplecomp;

import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Razred koji simulira mikroprocesor implementiran kao rjesenje 1. zadatka u ovoj domacoj zadaci.
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Simulator {
	
	/**
	 * Glavna metoda koja pokrece program.
	 * Moze primiti jedan argument - put do datoteke s asemblerskim kodom programa koji je potrebno
	 * prevesti i pokrenuti.
	 * Ako taj argument nije predan, onda program pita korisnika da naknadno unese put do datoteke.
	 * @param args Put do datoteke s asemblerskim kodom programa.
	 */
	public static void main(String[] args) {
		if (args.length > 1) {
			System.err.println("Wrong number of command line arguments!");
		}
		
		String path;
		if (args.length == 1) {
			path = args[0];
		} else {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the source path: ");
			path = scanner.nextLine().replaceAll("\"", "");
			scanner.close();
		}
		Computer comp = new ComputerImpl(256, 16);
		
		
		InstructionCreator creator = new InstructionCreatorImpl(
				"hr.fer.zemris.java.simplecomp.impl.instructions"
		);
		
		try {
			ProgramParser.parse(path, comp, creator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ExecutionUnit exec = new ExecutionUnitImpl();
		
		exec.go(comp);
		
		
	}
}
