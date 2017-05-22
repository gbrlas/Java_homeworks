package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing an object multi-stack which allows users to store multiple values for the same
 * key and provides a stack-like abstraction for those values. 
 * Keys for the ObjectMultistack will be instances of the class String, and the values associated
 * with those keys will be instances of ValueWrapper.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ObjectMultistack {
	
	/**
	 * Class which represents a single ObjectMultistackEntry. 
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	public static class MultistackEntry {
		private MultistackEntry next;
		private ValueWrapper value;
		
		/**
		 * Constructor which sets the entry value and the reference to the next entry.
		 * @param value Value of the entry.
		 * @param entry Next entry on the stack.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry entry) {
			this.value = value;
			this.next = entry;
		}
		
		/**
		 * Gets the next entry on the stack.
		 * @return Entry value.
		 */
		public MultistackEntry getNext() {
			return next;
		}
		
		/**
		 * Gets the value of this entry.
		 * @return Entry value.
		 */
		public ValueWrapper getValue() {
			return value;
		}
		
		public void setValue(ValueWrapper value) {
			this.value = value;
		}
	
	}
	
	/**
	 * Map with strings as keys and MultistackEntries as values.
	 */
	private Map<String, MultistackEntry> map = new HashMap<String, MultistackEntry>();
	
	/**
	 * Pushes the provided valueWrapper on the stack at the correct slot.
	 * @param name Key of the hash map.
	 * @param valueWrapper Value to be pushed on the multistack as a multistackEntry.
	 * @throws IllegalArgumentException - if provided name is null.
	 */
	public void push (String name, ValueWrapper valueWrapper) throws IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("Map key cannot be null!");
		}
		
		
		if (!map.containsKey(name)) {
			MultistackEntry newEntry = new MultistackEntry(valueWrapper, null);
			map.put(name, newEntry);
		} else {
			MultistackEntry newEntry = new MultistackEntry(valueWrapper, map.get(name));
			map.replace(name, newEntry);
		}
	}
	
	/**
	 * Pops the first multistack entry on the stack located at the slot calculated by hashing 
	 * the provided name.
	 * @param name Key of the hash map.
	 * @return ValueWrapper popped off the stack.
	 * @throws IllegalArgumentException - if provided name is null.
	 */
	public ValueWrapper pop (String name) {
		if (name == null) {
			throw new IllegalArgumentException("Map key cannot be null!");
		}
		
		MultistackEntry temp = map.get(name);
		
		if (temp != null) {
			if (temp.getNext() != null) {
				map.replace(name, temp.getNext());
			} else {
				map.remove(name);
			}
		}
		
		if (temp == null) {
			return null;
		} else {
			return temp.getValue();
		}
	}
	
	/**
	 * Returns the first multistack entry on the stack located at the slot calculated by hashing 
	 * the provided name without removing it from the stack.
	 * @param name Key of the hash map.
	 * @return ValueWrapper located at the top of the stack.
	 * @throws IllegalArgumentException - if provided name is null.
	 */
	public ValueWrapper peek (String name) {
		if (name == null) {
			throw new IllegalArgumentException("Map key cannot be null!");
		}
		
		MultistackEntry temp = map.get(name);
		
		if (temp == null) {
			return null;
		} else {
			return temp.getValue();
		}
	}
	
	/**
	 * Boolean representation of whether the multistack at the slot calculated by hashing the 
	 * provided name is empty.
	 * @param name Key of the hash map.
	 * @return True if the slot is empty, false otherwise.
	 * @throws IllegalArgumentException - if provided name is null.
	 */
	public boolean isEmpty(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Map key cannot be null!");
		}
		
		return !map.containsKey(name);
	}
}
