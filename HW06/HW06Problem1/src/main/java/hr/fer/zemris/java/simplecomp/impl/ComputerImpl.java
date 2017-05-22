package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija mikroprocesora. Sadrzi vlastitu memoriju te registre.
 * Implementira {@linkplain Computer}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ComputerImpl implements Computer {
	/**
	 * Memorija mikroprocesora.
	 */
	private MemoryImpl computerMemory;
	/**
	 * Registri mikroprocesora (registri opce namjene, PC te flag registar).
	 */
	private RegistersImpl computerRegisters;
	
	/**
	 * Konstruktor koji inicijalizira memoriju te registre na zadanu velicinu.
	 * @param memorySize Pocetna velicina memorije mikroprocesora.
	 * @param registersSize Pocetna velicina registara mikroprocesora.
	 */
	public ComputerImpl(int memorySize, int registersSize) {
		computerMemory = new MemoryImpl(memorySize);
		computerRegisters = new RegistersImpl(registersSize);
	}
	
	public Memory getMemory() {
		return computerMemory;
	}
	
	public Registers getRegisters() {
		return computerRegisters;
	}
}
