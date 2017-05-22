package hr.fer.zemris.java.tecaj.hw5.observer1;

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
	 * @param iStorage Reference to the IntegerStorage class.
	 */
	public void valueChanged (IntegerStorage iStorage);
}
