package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>add</b>.
 * Kao argumente prima tri registra, dva u kojem se nalaze podaci koji se trebaju zbrojiti te 
 * ciljni registar u koji se sprema rezultat.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrAdd implements Instruction {
	/**
	 * Index registra u kojeg moramo upisati vrijednost nakon zbrajanja.
	 */
	private int registerIndex0;
	/**
	 * Index registra prvog operanda.
	 */
	private int registerIndex1;
	/**
	 * Index registra drugog operanda.
	 */
	private int registerIndex2;
	
	/**
	 * Konstruktor koji postavlja vrijednosti indexa registra primljenih 
	 * preko liste argumenata instrukcija.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argumenti nisu valjani.
	 */
	public InstrAdd(List<InstructionArgument> arguments) throws IllegalArgumentException {
		if (arguments.size() != 3) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument 0 should be a register!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Argument 1 should be a register!");
		}
		if (!arguments.get(2).isRegister()) {
			throw new IllegalArgumentException("Argument 2 should be a register!");
		}
		
		this.registerIndex0 = ((Integer) arguments.get(0).getValue()).intValue();
		this.registerIndex1 = ((Integer) arguments.get(1).getValue()).intValue();
		this.registerIndex2 = ((Integer) arguments.get(2).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex2);
		computer.getRegisters().setRegisterValue(
				registerIndex0,
				Integer.valueOf(
						((Integer)value1).intValue() + ((Integer)value2).intValue()
				)
		);
		return false;
	}
}
