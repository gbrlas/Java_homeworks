package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;

/**
 * Razred koji izvodi sam program, odnosno predstavlja impulse takta za sam procesor.
 * Implementira {@linkplain ExecutionUnit}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ExecutionUnitImpl implements ExecutionUnit {
	
	public boolean go(Computer computer) {
		computer.getRegisters().setProgramCounter(0);
		
		while (true) {
			Instruction instruction = (Instruction) computer.getMemory().getLocation(computer.getRegisters().getProgramCounter());
			computer.getRegisters().incrementProgramCounter();
			
			if (instruction.execute(computer)) {
				break;
			}
		}
		
		return true;
	}
}
