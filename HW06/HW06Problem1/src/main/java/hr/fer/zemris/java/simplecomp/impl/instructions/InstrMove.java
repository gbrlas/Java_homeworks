package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>move</b>.
 * Kao argumente prima dva registra gdje u prvi registar stavljamo vrijednost koja se nalazi u drugom.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrMove implements Instruction {
	/**
	 * Index registra u kojeg moramo upisati vrijednost drugog registra.
	 */
	private int registerIndex0;
	/**
	 * Index registra ciju vrijednost upisujemo u prvi registar.
	 */
	private int registerIndex1;
	
	
	/**
	 * Konstruktor koji postavlja vrijednosti indexa registra primljenih 
	 * preko liste argumenata instrukcija.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argumenti nisu valjani.
	 */
	public InstrMove(List<InstructionArgument> arguments) throws IllegalArgumentException {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 3 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument 0 should be a register!");
		}
		if (!arguments.get(1).isRegister()) {
			throw new IllegalArgumentException("Argument 1 should be a register!");
		}
		
		this.registerIndex0 = ((Integer) arguments.get(0).getValue()).intValue();
		this.registerIndex1 = ((Integer) arguments.get(1).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex1);
		computer.getRegisters().setRegisterValue(
				registerIndex0,
				Integer.valueOf(((Integer)value1).intValue()));
		return false;
	}
}
