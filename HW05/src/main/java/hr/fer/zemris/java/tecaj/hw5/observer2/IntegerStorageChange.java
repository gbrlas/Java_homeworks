package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class created whenever there is a change in the IntegerStorage observer class.
 * Contains reference to that class and its previous and next values.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class IntegerStorageChange {
	/**
	 * Reference to the IntegerStorage class whose value has changed.
	 */
	private IntegerStorage iStorage;
	/**
	 * Previous value of the IntegerStorage class.
	 */
	private int previousValue;
	/**
	 * Next value of the IntegerStorage class.
	 */
	private int nextValue;
	
	/**
	 * Constructor which sets the class variables to the provided values.
	 * @param iStorage Reference to the IntegerStorage class whose value has changed.
	 * @param previousValue Previous value of the IntegerStorage class.
	 * @param nextValue Next value of the IntegerStorage class.
	 */
	public IntegerStorageChange(IntegerStorage iStorage, int previousValue, int nextValue) {
		this.iStorage = iStorage;
		this.previousValue = previousValue;
		this.nextValue = nextValue;
	}
	
	/**
	 * Gets the IntegerStorage class whose value has changed.
	 * @return IntegerStorage class whose value has changed.
	 */
	public IntegerStorage getiStorage() {
		return iStorage;
	}
	
	/**
	 * Gets the previous value of the IntegerStorage class.
	 * @return Previous value of the IntegerStorage class.
	 */
	public int getPreviousValue() {
		return previousValue;
	}
	
	/**
	 * Gets the next value of the IntegerStorage class.
	 * @return Next value of the IntegerStorage class.
	 */
	public int getNextValue() {
		return nextValue;
	}
	
	
}
