package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>echo</b>.
 * Kao argumente prima registar koji treba isprintati.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrEcho implements Instruction {
	/**
	 * Index registra u kojeg moramo upisati vrijednost sa zadane adrese.
	 */
	private int registerIndex;
	
	/**
	 * Konstruktor koji postavlja vrijednosti indexa preko liste argumenata instrukcije.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argument nije valjan.
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 1 argument!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument 0 should be a register!");
		}
		
		this.registerIndex = ((Integer) arguments.get(0).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		System.out.print((String) computer.getRegisters().getRegisterValue(registerIndex).toString());
		return false;
	}
}
