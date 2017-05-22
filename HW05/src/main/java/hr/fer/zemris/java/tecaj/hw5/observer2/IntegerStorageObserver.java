package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Interface which observes the changes in the IntegerStorage class and reacts accordingly.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Action to be performed when the value in the IntegerStorage class changes.
	 * @param change Reference to the IntegerStorage class.
	 */
	public void valueChanged (IntegerStorageChange change);
}
