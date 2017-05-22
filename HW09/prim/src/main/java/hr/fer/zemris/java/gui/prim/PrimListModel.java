package hr.fer.zemris.java.gui.prim;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class representing a ListModel which calculates and stores prime numbers.
 * Implements ListModel.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/**
	 * List containing prime numbers.
	 */
	List<Integer> primList;
	/**
	 * Last calculated prime number.
	 */
	private int lastPrim = 1;
	/**
	 * List holding event listeners.
	 */
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * Constructor which sets the initial list value to the provided list.
	 * 
	 * @param primList
	 */
	public PrimListModel(List<Integer> primList) {
		this.primList = primList;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listenerList.add(ListDataListener.class, l);
	}

	@Override
	public Integer getElementAt(int index) {
		if (index < 0 || index >= primList.size()) {
			throw new IllegalArgumentException("Invalid index provided.");
		}
		return primList.get(index);
	}

	@Override
	public int getSize() {
		return primList.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}

	/**
	 * Method which calculates the next prime nubmer.
	 */
	public void next() {
		lastPrim = calculatenextPrim();
		primList.add(lastPrim);
		fireIntervalAdded(this, primList.size() - 1, primList.size() - 1);
	}

	/**
	 * Helper method for calculating prime numbers.
	 * 
	 * @return Next prime number.
	 */
	private int calculatenextPrim() {
		for (int i = lastPrim + 1;; i++) {
			if (isPrime(i)) {
				return i;
			}
		}
	}

	/**
	 * Helper method which calculates whether the provided number is prime or
	 * not.
	 * 
	 * @param n
	 *            Number to be tested.
	 * @return True if number is prime, false otherwise.
	 */
	private boolean isPrime(int n) {

		if (n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	/**
	 * Method which notifies all the listeners that a new prime number has been
	 * calculated and added to the mode.
	 * 
	 * @param source
	 *            PrimListModel class.
	 * @param index0
	 *            Index of the added prime number in the list.
	 * @param index1
	 *            Index of the added prime number in the list.
	 */
	protected void fireIntervalAdded(Object source, int index0, int index1) {
		Object[] listeners = listenerList.getListenerList();
		ListDataEvent e = null;

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListDataListener.class) {
				if (e == null) {
					e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED,
							index0, index1);
				}
				((ListDataListener) listeners[i + 1]).intervalAdded(e);
			}
		}
	}

}
