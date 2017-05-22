package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>decrement</b>.
 * Kao argumente prima registar ciju vrijednost zelimo smanjiti za 1.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrIncrement implements Instruction {
	/**
	 * Index registra ciju vrijednost zelimo povecati za 1.
	 */
	private int registerIndex;
	
	/**
	 * Konstruktor koji postavlja index registra ciju vrijednost zelimo smanjiti za 1.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argument nije valjan.
	 */
	public InstrIncrement(List<InstructionArgument> arguments) throws IllegalArgumentException  {
		if (arguments.size() != 1) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument 0 should be a register!");
		}
		
		this.registerIndex = ((Integer) arguments.get(0).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		Object value = Integer.valueOf(((Integer)computer.getRegisters().getRegisterValue(registerIndex)).intValue() + 1);
		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}
}
