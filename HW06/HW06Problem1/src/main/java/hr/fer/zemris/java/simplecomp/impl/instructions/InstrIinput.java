package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

public class InstrIinput implements Instruction {
	/**
	 * Memorijska lokacija sa koje trebamo uzeti podatak i upisati ga u zadani registar.
	 */
	private int memoryAdress;
	
	/**
	 * Konstruktor koji postavlja vrijednosti memorijske adrese na zadanu vrijednost.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argument nije valjan.
	 */
	public InstrIinput(List<InstructionArgument> arguments) throws IllegalArgumentException  {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 arguments!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Argument 0 should be a number!");
		}
		
		this.memoryAdress = ((Integer) arguments.get(0).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		Scanner scanner = new Scanner(System.in);
		String line;
		
		boolean test = true;
		int number = 0;
		while (test) {
			System.out.println("Unesite pocetni broj: ");
			line = scanner.nextLine();
			
			try {
				number = Integer.parseInt(line);
			} catch (Exception exception) {
				System.out.println("Unos nije moguce protumaciti kao cijeli broj");
				continue;
			}
			
			break;
		}
		
		scanner.close();
		computer.getMemory().setLocation(memoryAdress, Integer.valueOf(number));;
		
		return false;
	}
}
