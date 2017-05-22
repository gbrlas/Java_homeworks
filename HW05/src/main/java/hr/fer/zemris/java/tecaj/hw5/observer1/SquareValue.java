package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Observer which prints out the square of the integer stored in the IntegerStorage class it is registered
 * to.
 * Implements hr.fer.zemris.java.tecaj.hw5.observer1.IntegerStorageObserver.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class SquareValue implements IntegerStorageObserver {
	
	@Override
	/**
	 * Prints out the square of the integer stored in the IntegerStorage class it is registered to .
	 */
	public void valueChanged(IntegerStorage iStorage) {
		System.out.println("Provided new value: " + iStorage.getValue() + ", square is " + Math.pow(iStorage.getValue(), 2));
	}
}
