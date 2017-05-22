package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementacija instrukcije <b>testEquals</b>.
 * Kao argumente prima dva registra koja se usporedjuju. Ako je njihov sadrzaj jednak, zastavica
 * flags se postavlja na true.
 * Implementira {@linkplain Instruction}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class InstrTestEquals implements Instruction {
	/**
	 * Index prvog registra kojeg usporedjujemo.
	 */
	private int registerIndex0;
	/**
	 * Index drugog registra kojeg usporedjujemo.
	 */
	private int registerIndex1;
	
	
	/**
	 * Konstruktor koji postavlja vrijednosti indexa registra primljenih 
	 * preko liste argumenata instrukcija.
	 * @param arguments Lista argumenata instrukcije.
	 * @throws IllegalArgumentException - ako primljeni argumenti nisu valjani.
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) throws IllegalArgumentException {
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
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex0);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex1);
		if (value1.equals(value2)) {
			computer.getRegisters().setFlag(true);
		}
		return false;
	}
}
