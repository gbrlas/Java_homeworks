package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implementacija memorije u mikroprocesoru. 
 * Implementira {@linkplain Computer}.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class MemoryImpl implements Memory {
	/**
	 * Polje koje predstavlja memorijske adrese. 
	 * Na svakoj valjanoj lokaciji se moze nalaziti referenca na proizvoljno velik objekt.
	 */
	private Object[] memoryArray;
	
	/**
	 * Konstruktor koji inicijalizira privatno polje koje predstavlja memoriju na zadanu velicinu.
	 * @param size Velicina na koju trebamo inicijalzirati memorijsko polje.
	 * @throws IndexOutOfBoundsException ako je zadana velicina memorije manja od 0.
	 */
	public MemoryImpl (int size) throws IndexOutOfBoundsException {
		if (size < 0) {
			throw new IndexOutOfBoundsException("Pocetna velicina memorijskog polja ne moze biti manja od 0!");
		}
		memoryArray = new Object[size];
	}
	
	public void setLocation(int location, Object value) throws IndexOutOfBoundsException {
		if (location < 0 || location >= memoryArray.length) {
			throw new IndexOutOfBoundsException("Given memory location is out of bounds!");
		}
		
		memoryArray[location] = value;
	}
	
	public Object getLocation(int location) throws IndexOutOfBoundsException {
		if (location < 0 || location >= memoryArray.length) {
			throw new IndexOutOfBoundsException("Given memory location is out of bounds!");
		}
		
		return memoryArray[location];
	}

}
