package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>jumpIfTrue</b>.
 * Kao argumente prima vrijednost adrese na koju treba postaviti PC ako je zastavica flag postavljena
 * na true.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrJumpIfTrue implements Instruction {
	/**
	 * Memorijska lokacija sa koje trebamo uzeti podatak i upisati ga u zadani registar.
	 */
	private int memoryAdress;
	
	/**
	 * Konstruktor koji postavlja vrijednosti memorijske adrese na zadanu vrijednost.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argument nije valjan.
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) throws IllegalArgumentException  {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isNumber()) {
			throw new IllegalArgumentException("Argument 1 should be a number!");
		}
		
		this.memoryAdress = ((Integer) arguments.get(0).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		if (computer.getRegisters().getFlag()) {
			computer.getRegisters().setProgramCounter(memoryAdress);
		}
		return false;
	}
}
