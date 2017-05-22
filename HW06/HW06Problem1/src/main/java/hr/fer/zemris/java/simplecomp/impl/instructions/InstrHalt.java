package hr.fer.zemris.java.simplecomp.impl.instructions;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import java.util.List;

/**
 * Implementacija instrukcije <b>halt</b>.
 * Kao argumente prima praznu listu.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrHalt implements Instruction {
	
	/**
	 * Konstruktor koji stvara instrukciju, ne uzimam nikakve argumente iz primljene liste.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako ima primljenih argumenata.
	 */
	public InstrHalt(List<InstructionArgument> arguments) throws IllegalArgumentException {
		if (arguments != null && arguments.size() != 0) {
			throw new IllegalArgumentException("Expected 0 arguments!");
		}
	}
	
	public boolean execute(Computer computer) {
		return true;
	}
}
