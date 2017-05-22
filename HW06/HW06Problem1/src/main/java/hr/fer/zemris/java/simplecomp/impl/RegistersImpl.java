package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija registara u mikroprocesoru.
 * Klasa sadrzi registre opce namjene, program counter te registar zastavice.
 * Implementira {@linkplain Registers}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class RegistersImpl implements Registers {
	/**
	 * Polje koje predstavlja registre opce namjene mikroprocesora.
	 */
	private Object[] basicRegisters;
	
	/**
	 * Program counter mikroprocesora.
	 */
	private int programCounter;
	/**
	 * Registar zastavice mikroprocesora.
	 */
	private boolean flag;
	
	/**
	 * Konstruktor koji inicijalizira polje registara opce namjene na zadanu velicinu, 
	 * program counter na 0, te registar zastavice na false.
	 * @param regsLen Broj registara opce namjene koje treba inicijalizirati.
	 * @throws IndexOutOfBoundsException ako je zadan broj registara manji od 0.
	 */
	public RegistersImpl (int regsLen) throws IndexOutOfBoundsException {
		if (regsLen < 0) {
			throw new IndexOutOfBoundsException("Pocetni broj registara ne moze biti manji od 0!");
		}
		basicRegisters = new Object[regsLen];
		programCounter = 0;
		flag = false;
	}
	
	public Object getRegisterValue(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > basicRegisters.length) {
			throw new IndexOutOfBoundsException("Provided register does not exist, given index out of bounds!");
		}
		
		return basicRegisters[index];
	}
	
	public boolean getFlag() {
		return flag;
	}
	
	public int getProgramCounter() {
		return programCounter;
	}
	
	public void incrementProgramCounter() {
		programCounter++;
	}
	
	public void setFlag(boolean value) {
		flag = value;
	}
	
	public void setProgramCounter(int value) {
		programCounter = value;
	}
	
	public void setRegisterValue(int index, Object value) throws IndexOutOfBoundsException {
		if (index < 0 || index > basicRegisters.length) {
			throw new IndexOutOfBoundsException("Provided register does not exist, given index out of bounds!");
		}
		
		basicRegisters[index] = value;
		
	}
}
