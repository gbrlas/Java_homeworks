package hr.fer.zemris.java.tecaj.hw5.observer2;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageObserver;

/**
 * Class which stores a single integer value. Can be observed and provides basic functions to enable 
 * observation.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class IntegerStorage {
	/**
	 * Value of the class instance.
	 */
	private int value;
	/**
	 * List containing the observers currently registered with this class instance.
	 */
	List<IntegerStorageObserver> observers;
	
	
	/**
	 * Constructor which sets the initial value.
	 * @param initialValue Initial value to be set.
	 */
	public IntegerStorage(int initialValue) {
		value = initialValue;
	}
	
	/**
	 * Adds the provided observer to the observer list.
	 * @param observer Observer to be added to the observer list.
	 * @throws IllegalArgumentException - if provided observer is null.
	 */
	public void addObserver (IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<IntegerStorageObserver>();
		}
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes the provided observer from the observer list.
	 * @param observer Observer to be deleted from the observer list.
	 */
	public void removeObserver (IntegerStorageObserver observer) {
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}
	
	/**
	 * De-registers all the observers from the class and clears the observer list.
	 */
	public void clearObservers () {
		if (observers.size() != 0) {
			observers.clear();
		}
	}
	
	/**
	 * Gets the value.
	 * @return Value of this instance.
	 */
	public int getValue () {
		return value;
	}
	
	/**
	 * Sets the value and notifies the observers of the change if the provided value is different from the
	 * old value.
	 * @param value Value to be set.
	 */
	public void setValue (int value) {
		if (this.value != value) {
			IntegerStorageChange change = new IntegerStorageChange(this, this.value, value);
			this.value = value;
			
			if (observers != null) {
				for (int i = 0; i < observers.size(); i++) {
					observers.get(i).valueChanged(change);
				}
			}
		}
		
		
	}
}
