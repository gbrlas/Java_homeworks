package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class which tests the second problem in the homework - IntegerStorage and its observers.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class ObserverExample {
	
	/**
	 * Main method which starts the program.
	 * @param args - none
	 */
	public static void main (String[] args) {
		IntegerStorage iStorage = new IntegerStorage(20);
		iStorage.addObserver(new SquareValue());
		iStorage.addObserver(new ChangeCounter());
		iStorage.addObserver(new DoubleValue());

		
		iStorage.setValue(5);
		iStorage.setValue(2);
		iStorage.setValue(25);
		
		iStorage.setValue(13);
		iStorage.setValue(22);
		iStorage.setValue(15);
	}
}
