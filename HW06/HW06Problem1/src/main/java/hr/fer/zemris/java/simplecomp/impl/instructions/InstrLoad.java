package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>load</b>.
 * Kao argumente prima registar i memorijsku lokaciju ciji sadrzaj treba u njega pohraniti.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrLoad implements Instruction {
	/**
	 * Index registra u kojeg moramo upisati vrijednost sa zadane adrese.
	 */
	private int registerIndex;
	/**
	 * Memorijska lokacija sa koje trebamo uzeti podatak i upisati ga u zadani registar.
	 */
	private int memoryAdress;
	
	/**
	 * Konstruktor koji postavlja vrijednosti indexa registra te memorijske lokacije primljenih 
	 * preko liste argumenata instrukcija.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argumenti nisu valjani.
	 */
	public InstrLoad(List<InstructionArgument> arguments) throws IllegalArgumentException {
		if (arguments.size() != 2) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}
		if (!arguments.get(0).isRegister()) {
			throw new IllegalArgumentException("Argument 0 should be a register!");
		}
		if (!arguments.get(1).isNumber()) {
			throw new IllegalArgumentException("Argument 1 should be a number!");
		}
		
		this.registerIndex = ((Integer) arguments.get(0).getValue()).intValue();
		this.memoryAdress = ((Integer) arguments.get(1).getValue()).intValue();
	}
	
	public boolean execute(Computer computer) {
		Object value = computer.getMemory().getLocation(memoryAdress);
		computer.getRegisters().setRegisterValue(registerIndex, value);
		return false;
	}
}
