package hr.fer.zemris.java.tecaj.hw3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class which allows user to loop from specified integer to specified integer with given step.
 * Implements Iterable. 
 * There are two ways to use this class: end must be greater than start and step must be positive,
 * or start must be greater than end and step must be negative.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class IntegerSequence implements Iterable<Integer> {
	/**
	 * Array of numbers to be iterated over.
	 */
	private int[] numbers;
	
	
	@Override
	/**
	 * Creates a new iterator over the given numbers array.
	 */
	public Iterator<Integer> iterator() {	
		return new ArrayIterator();
	}
	
	/**
	 * Constructor which sets the ArrayList values according to the given start, end and step.
	 * @param start Starting integer.
	 * @param end Ending integer.
	 * @param step Integer representing the step.
	 * @throws IllegalArgumentException if given start, step and end are incompatible.
	 */
	public IntegerSequence (int start, int end, int step) {
		
		if (end > start && step > 0) {
			stepPositive(start, end, step);
		} 
		else if (end < start && step < 0) {
			stepNegative(start, end, step);
		} else {
			throw new IllegalArgumentException("Given start, step and end are incompatible");
		}
	}
	
	/**
	 * Initializes integer array according to the given start, end and step if the given step is positive and
	 * end value is greater than the start value.
	 * @param start Starting integer.
	 * @param end Ending integer.
	 * @param step Integer representing the step.
	 */
	private void stepPositive (int start, int end, int step) {
		int[] temp = new int[end - start +1];
		
		//create an array of all the elements from start to end
		for(int i = start, j = 0; i <= end; i++, j++) {
			temp[j] = i;
		}
		
		numbers = new int[(end - start) / step + 1];
		
		//put only the needed integers in the numbers array
		for(int i = 0, j = 0; i < temp.length; i++) {
			if (i % step == 0) {
				numbers[j] = temp[i];
				j++;
			}
		}
	}
	
	/**
	 * Initializes integer array according to the given start, end and step if the given step is negative and
	 * start value is greater than the end value.
	 * @param start Starting integer.
	 * @param end Ending integer.
	 * @param step Integer representing the step.
	 */
	private void stepNegative (int start, int end, int step) {
		int[] temp = new int[start - end +1];
		
		for(int i = start, j = 0; i >= end; i--, j++) {
			temp[j] = i;
		}
		
		numbers = new int[(start - end) / Math.abs(step) + 1];
		
		for (int i = 0, j = 0; i < temp.length; i++) {
			if (i % step == 0) {
				numbers[j] = temp[i];
				j++;
			}
		}
	}
	
	/**
	 * Private class which iterates over the given Integer array. 
	 * Implements Iterator.
	 * 
	 * @author Goran Brlas
	 * @version 1.0
	 *
	 */
	private class ArrayIterator implements Iterator<Integer> {
		/**
		 * Iterator counter, can range from 0 to length - 1.
		 */
		private int counter;
		
		/**
		 * Constructor which sets the iterator counter to 0.
		 */
		public ArrayIterator() {
			counter = 0;
		}
		
		/**
		 * Checks whether the integer array has more elements.
		 */
		public boolean hasNext() {
			if (counter < numbers.length) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Returns the next element in the integer array.
		 * @throws NoSuchElementException if there are no more elements.
		 */
		public Integer next() throws NoSuchElementException {
			if(this.hasNext()) {
				return numbers[counter++];
			} else {
				throw new NoSuchElementException("There are no more elements to iterate through.");
			}
		}
		
		@Override
		/**
		 * Overrides java.util.iterator.remove.
		 * @throws UnsupportedOperationException
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
	
}
