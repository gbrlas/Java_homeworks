package hr.fer.zemris.java.custom.collections;

import java.util.EmptyStackException;

/**
 * Provides methods which are natural for a stack.
 * 
 * @author Goran Brlas
 *
 */
public class ObjectStack {
	private ArrayBackedIndexedCollection collection = new ArrayBackedIndexedCollection();
	
	/**
	 * Returns true if the stack has no objects and false otherwise.
	 * @return Boolean representation of whether the stack is empty.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns size of the elements on the stack.
	 * @return Number of elements on the stack.
	 */
	public int size () {
		return collection.size();
	}
	
	/**
	 * Pushes given value on the stack.
	 * @param value Values to be pushed on the stack.
	 */
	public void push (Object value) {
		collection.add(value);
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * @return Object popped of the stack.
	 */
	public Object pop () throws EmptyStackException {
		if (collection.isEmpty())
			throw new EmptyStackException();
		
		Object object = collection.get(collection.size()-1);
		collection.remove(collection.size()-1);
		
		
		return object;
	}
	
	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * @return Object last pushed on the stack.
	 * @throws EmptyStackException
	 */
	public Object peek () throws EmptyStackException {
		if (collection.isEmpty())
			throw new EmptyStackException();
		
		Object object = collection.get(collection.size()-1);
		
		return object;
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear () {
		collection.clear();
	}
}
