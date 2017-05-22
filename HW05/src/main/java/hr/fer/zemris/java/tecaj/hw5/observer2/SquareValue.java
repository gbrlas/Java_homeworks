package hr.fer.zemris.java.tecaj.hw5.observer2;

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
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Provided new value: " + change.getNextValue() + ", square is " + Math.pow(change.getiStorage().getValue(), 2));
	}
}
