package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Observer which prints the double value of the value currently stored in the IntegerStorage class it is 
 * connected to, but only first two times since its registration with subject.
 * After writing the double value for the second time, the observer automatically de-registers itself from 
 * the subject.
 * Implements hr.fer.zemris.java.tecaj.hw5.observer2.IntegerStorageObserver.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Integer counting the number of value changes since registration with the IntegerStorage class.
	 */
	private int changes = 0;
	
	@Override
	/**
	 * Prints the double value of the current IntegerStorage registered to the observer.
	 * De-registers after printing the value for the second time.
	 */
	public void valueChanged(IntegerStorageChange change) {
		changes++;
		System.out.println("Double value: " + change.getNextValue() * 2);
		
		if (changes == 2) {
			change.getiStorage().removeObserver(this);;
		}
	}
}
