package hr.fer.zemris.java.tecaj.hw4.collections;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Class which allows the user to create a simple hash table. Provides basic hash functions.
 * Implements Iterable.
 * 
 * @author Goran Brlas
 * @version 1.0
 * 
 */
public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry> {
	/**
	 * Array of table entries.
	 */
	private TableEntry[] table;
	/**
	 * Size of the hash table.
	 */
	private int size;
	/**
	 * Counts how many times the hash table has been modified.
	 */
	private int modificationCount;
	
	
	
	/**
	 * Private class representing each table entry.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 */
	public static class TableEntry {
		/**
		 * Key of this entry.
		 */
		private Object key;
		/**
		 * Value of this entry.
		 */
		private Object value;
		/**
		 * Next entry in the slot. null by default.
		 */
		private TableEntry next;
		
		/**
		 * Constructor which sets the table entry according to the provided values.
		 * @param key Key of the entry. Used for calculating the table slot.
		 * @param value Value of the entry.
		 * @param next Next entry in the slot.
		 */
		public TableEntry (Object key, Object value, TableEntry next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Gets the enty key.
		 * @return Entry key.
		 */
		public Object getKey () {
			return key;
		}
		
		/**
		 * Gets the entry value.
		 * @return Entry value.
		 */
		public Object getValue () {
			return value;
		}
		
		/**
		 * Sets the entry value to the provided value.
		 * @param value Value to be set for this entry.
		 */
		public void setValue (Object value) {
			this.value = value;
		}
		
		@Override
		/**
		 * Returns the string representation of this entry in the form of: key=value. 
		 * Overrides java.lang.Object.toString().
		 */
		public String toString () {
			StringBuilder builder = new StringBuilder();
			
			builder.append(key.toString()).append('=').append(value.toString());
			
			return builder.toString();
		}
	}
	
	
	
	

	
	/**
	 * Default constructor which sets the hash table size to 16 and initializes the array of table
	 * entries.
	 */
	public SimpleHashtable () {
		size = 0;
		table = new TableEntry[16];
		modificationCount = 0;
	}
	
	/**
	 * Constructor which sets the hash table size to the first power of 2 greater or equal to the 
	 * user provided size and initializes the array of table entries.
	 * @param size Size of the new hash table.
	 * @throws IllegalArgumentException if given size is lower than 0.
	 */
	public SimpleHashtable (int size) throws IllegalArgumentException {
		if (size < 1) {
			throw new IllegalArgumentException("Given size must be greater or equal to 1!");
		}
		
		int i = 1;
		for (; i < size; i *= 2) {}
		
		this.size = 0;
		table = new TableEntry[i];
		modificationCount = 0;
	}
	
	/**
	 * Puts the given value in the hash table according to the given key.
	 * The value slot is calculated by using the java.lang.Object.hashCode() method and then 
	 * using the modulo operator on the received number.
	 * @param key Key used for calculating the table slot.
	 * @param value Value to be put into the hash table.
	 * @throws IllegalArgumentException if the provided key is null.
	 */
	public void put (Object key, Object value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("The key cannot be null!");
		}
		
		int slot = Math.abs(key.hashCode());
		slot %= table.length;
		TableEntry newEntry = new TableEntry(key, value, null);
		
		if (table[slot] == null) {
			table[slot] = newEntry;
			size++;
		} else {
			TableEntry temp = table[slot];
			boolean exists = false;
			
			//traverse the list until the end
			while (temp.next != null) {
				if (temp.key.equals(key)) {
					temp.setValue(value);
					exists = true;
					break;
				}
				temp = temp.next;
			}
			
			if (!exists) {
				temp.next = newEntry;
				size++;
			}
		}
		
		int filled = 0;
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				filled++;
			}
		}
		
		if (filled >= 0.75 * table.length) {
			rehash();
		}
		
		modificationCount++;
	}
	
	/**
	 * Gets the value stored in the hash table with the provided key.
	 * Returns null if the object is not found.
	 * @param key Key of the searched for table entry.
	 * @return Value of the table entry with the provided key.
	 */
	public Object get (Object key) {
		if (key == null) {
			return null;
		}
		
		Object value = null;
		int slot = Math.abs(key.hashCode()) % table.length;
		
		if (table[slot].getKey().equals(key)) {
			value = table[slot].getValue();
		} else {
			TableEntry temp = table[slot].next;
			
			while (temp != null) {
				if(temp.getKey().equals(key)) {
					value = temp.getValue();
					break;
				} else {
					temp = temp.next;
				}
			}
		}
		
		return value;
	}
	
	/**
	 * Returns the size of the hash table.
	 * @return SIze of the hash table.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Boolean representation of whether there is an entry in the table with the provided key.
	 * @param key Key to be searched for.
	 * @return True if the hash table contains the provided key, false otherwise.
	 */
	public boolean containsKey (Object key) {
		boolean contains = false;
		
		for (TableEntry entry : table) {
			if (entry != null) {
				if (entry.getKey().equals(key)) {
					contains = true;
					break;
				
				//if there are more entries in the same slot
				} else if (entry.next != null) {
					TableEntry temp = entry.next;
					
					while (temp != null) {
						if (temp.getKey().equals(key)) {
							contains = true;
							break;
						} else {
							temp = temp.next;
						}
					}
				}
			}
		}
		
		return contains;
	}
	
	/**
	 * Boolean representation of whether there is an entry in the table with the provided key.
	 * @param value Value to be searched for.
	 * @return True if the hash table contains the provided value, false otherwise.
	 */
	public boolean containsValue (Object value) {
		boolean contains = false;
		
		for (TableEntry entry : table) {
			if (entry != null) {
				if (entry.getValue().equals(value)) {
					contains = true;
					break;
				
				//if there are more entries in the same slot
				} else if (entry.next != null) {
					TableEntry temp = entry.next;
					
					while (temp != null) {
						if (temp.getValue().equals(value)) {
							contains = true;
							break;
						} else {
							temp = temp.next;
						}
					}
				}
			}
		}
		
		return contains;
	}
	
	/**
	 * Removes the entry with the provided key.
	 * @param key Key of the entry to be deleted.
	 */
	public void remove (Object key) {
		
		if (!containsKey(key)) {
			return;
		}
		
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				if (table[i].getKey().equals(key)) {
					if (table[i].next == null) {
						table[i] = null;
						size--;
					} else {
						table[i] = table[i].next;
						size--;
					}
				} else if (table[i].next != null) {
					TableEntry temp = table[i].next;
					TableEntry prev = table[i];
					
					while (temp.next != null) {
						if (temp.getKey().equals(key)) {
							prev.next = temp.next;
							temp = null;
							size--;
							break;
						} else {
							prev = temp;
							temp = temp.next;
						}
					}
				}
			}
		}
		
		modificationCount++;
	}
	
	/**
	 * Boolean representation of whether the hash table is empty.
	 * @return True if the table is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return (size == 0);
	}
	
	@Override
	/**
	 * Returns string representation of the hash table.
	 * Overrides java.lang.Object.toString().
	 */
	public String toString () {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		
		for (TableEntry entry : table) {
			if (entry != null) {
				builder.append(entry.toString() + ", ");
				
				if (entry.next != null) {
					TableEntry temp = entry.next;
					builder.append(temp.toString() + ", ");
					
					while (temp.next != null) {
						temp = temp.next;
						builder.append(temp.toString() + ", ");
					}
				}
			}
		}
		
		builder.deleteCharAt(builder.length() - 1);
		builder.deleteCharAt(builder.length() - 1);
		builder.append("]");
		
		return builder.toString();
	}
	
	/**
	 * Clears the hash table by setting all the slots to null and by setting the size value to 0.
	 */
	public void clear () {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		
		size = 0;
	}
	
	/**
	 * Rehashes the hash table if more than 75% of the slots become occupied.
	 * The hash table size is doubled, and the existing elements are rehashed.
	 * Element position may change after rehashing.
	 */
	private void rehash () {
		TableEntry[] table2 = new TableEntry[table.length * 2]; 
		int size2 = 0;
		
		for (TableEntry entry : table) {
			
			TableEntry temp2 = entry;
			
			while (temp2 != null) {
				int slot = Math.abs(temp2.getKey().hashCode());
				slot %= table2.length;
				TableEntry newEntry = new TableEntry(temp2.getKey(), temp2.getValue(), null);
			
				if (table2[slot] == null) {
					table2[slot] = newEntry;
					size2++;
				} else {
					TableEntry temp = table2[slot];
				
					//traverse the list until the end
					while (temp.next != null) {
						temp = temp.next;
					}
				
					temp.next = newEntry;
					size2++;
				}
				
				temp2 = temp2.next;
			}
		}
		
		table = table2;
		size = size2;
		modificationCount++;
	}
	
	
	
	//Iterator methods and classes
	
	@Override
	/**
	 * Creates a new iterator over the given numbers array.
	 */
	public Iterator<SimpleHashtable.TableEntry> iterator() {	
		return new IteratorImpl();
	}
	
	
	/**
	 * Private class which iterates over the given hash table. 
	 * Implements Iterator.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry> {
		/**
		 * Iterator counter. Initially set to 0.
		 */
		private int counter;
		/**
		 * Boolean representation of whether the list has been modified.
		 * If the same element is being removed multiple times, an exception will be thrown.
		 */
		private boolean changed;
		/**
		 * Used to compare modifications to the hash table while iterating.
		 */
		private int myModificationCounter;
		
		/**
		 * Constructor which sets the iterator counter to 0.
		 */
		public IteratorImpl() {
			counter = 0;
			changed = false;
			myModificationCounter = modificationCount;
		}
		
		/**
		 * Checks whether the hash table has more elements.
		 * @throws ConcurrentModificationException if the hash table is changed by the user directly while 
		 * 			iterating.
		 */
		public boolean hasNext() throws ConcurrentModificationException {
			if (myModificationCounter != modificationCount) {
				throw new ConcurrentModificationException("Hash table was changed directly, not through an iterator!");
			}
			else if (counter < size) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Returns the next element in the hash table.
		 * @throws NoSuchElementException if there are no more elements.
		 */
		public TableEntry next() throws NoSuchElementException {
			if(this.hasNext()) {
				changed = false;
				return findElement();
				
			} else {
				throw new NoSuchElementException("There are no more elements to iterate through.");
			}
		}
		
		@Override
		/**
		 * Removes the entry at the current index.
		 * Overrides java.util.Iterator.remove().
		 * @throws IllegalStateException if the same element is being deleted multiple times.
		 */
		public void remove () throws IllegalStateException {
			if (changed == true) {
				throw new IllegalStateException ("You cannot remove the same element twice");
			} 
			counter--;
			SimpleHashtable.this.remove(findElement().getKey());
			myModificationCounter++;
			
			changed = true;
		}
		
		/**
		 * Finds the table entry at the current index.
		 * @return Entry at the current index.
		 */
		public SimpleHashtable.TableEntry findElement () {
			int temp = 0;
			SimpleHashtable.TableEntry returnEntry = null;
			
			for (int i = 0; i < table.length; i++) {
				if (table[i] != null) {
					if (temp == counter) {
						counter++;
						returnEntry = table[i];
						break;
					} else {
						temp++;
						boolean found = false;
						
						 if (table[i].next != null) {
							 TableEntry entry = table[i].next;
							 
							 while (entry != null) {
								 
								 if (temp == counter) {
										counter++;
										returnEntry	= entry;
										found = true;
										break;
								 }
								 
								 entry = entry.next;
								 if (entry != null) {
									 temp++;
								 }
								 
							 }
						 }
						 
						 if (found) {
							 break;
						 }
					}
				}
			}
			
			return returnEntry;
		}
		
		
		
	}
	
}
	
	
