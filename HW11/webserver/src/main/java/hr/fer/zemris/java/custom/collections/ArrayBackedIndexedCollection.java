package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of resizable array-backed collection of objects.
 *
 * @author Goran Brlas
 *
 */
public class ArrayBackedIndexedCollection {
	/**
	 * Size of the collection.
	 */
	private int size;
	/**
	 * Collection capacity.
	 */
	private int capacity;
	/**
	 * Array containing all collection elements.
	 */
	private Object[] elements;

	/**
	 * Default constructor which sets the initial capacity to 16.
	 */
	public ArrayBackedIndexedCollection() {
		this(16);
	}

	/**
	 * Constructor which allows user-defined initial capacity.
	 *
	 * @param initialCapacity
	 *            Integer value of the initial capacity.
	 * @throws IllegalArgumentException
	 *             - if provided capacity is less than 0.
	 */
	public ArrayBackedIndexedCollection(final int initialCapacity)
			throws IllegalArgumentException {

		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		} else {
			size = 0;
			capacity = initialCapacity;
			elements = new Object[capacity];
		}
	}

	/**
	 * Returns true if collection contains no objects and false otherwise.
	 *
	 * @return Boolean representation of whether there are any objects in the
	 *         collection.
	 */
	public final boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of currently stored objects in collections.
	 *
	 * @return Number of currently stored objects.
	 */
	public final int size() {
		return size;
	}

	/**
	 * Adds the given object at the end of the collection.
	 *
	 * @param value
	 *            Object to be put into collection.
	 * @throws IllegalArgumentException
	 *             - if provided value is null.
	 */
	public final void add(final Object value) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException();
		} else {
			if (size == capacity) {
				increaseCapacity();
			}

			elements[size] = value;
			size++;
		}
	}

	/**
	 * Returns the object stored in backing array at position index.
	 *
	 * @param index
	 *            Index of an object in the collection.
	 * @return Return object positioned at given index.
	 * @throws IndexOutOfBoundsException
	 *             if provided index is less than 0 or greater than collection
	 *             size.
	 */
	public final Object get(final int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Invalid index!");
		} else {
			return elements[index];
		}
	}

	/**
	 * Removes the object stored in the backing array at position index.
	 *
	 * @param index
	 *            Position of the object to be removed.
	 * @throws IndexOutOfBoundsException
	 *             if provided index is less than 0 or greater than collection
	 *             size.
	 */
	public final void remove(final int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Invalid index!");
		} else {
			for (int i = index; i < (size - 1); i++) {
				elements[i] = elements[i + 1];
			}
			size -= 1;
		}
	}

	/**
	 * Inserts the given value at the given position in the backing array.
	 *
	 * @param value
	 *            Value to be inserted into backing array at index position.
	 * @param position
	 *            Position to insert the value into.
	 * @throws IndexOutOfBoundsException
	 *             if provided index is less than 0 or greater than collection
	 *             size.
	 * @throws IllegalArgumentException
	 *             if provided value is null.
	 */
	public final void insert(final Object value, final int position)
			throws IndexOutOfBoundsException, IllegalArgumentException {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Invalid position!");
		} else if (value == null) {
			throw new IllegalArgumentException(
					"Invalid argument, can't be null!");
		} else {
			// increase the capacity if needed
			if (size == capacity) {
				increaseCapacity();
			}

			for (int i = size; i > position; i--) {
				elements[i] = elements[i - 1];
			}
		}

		elements[position] = value;
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if not found.
	 *
	 * @param value
	 *            Value being searched for.
	 * @return Index of the given value.
	 */
	final int indexOf(final Object value) {
		int test = -1;

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				test = i;
				break;
			}
		}

		return test;
	}

	/**
	 * Returns true if the collection contains given value, else returns false.
	 *
	 * @param value
	 *            Value being searched for.
	 * @return Boolean representation of whether the given object is in the
	 *         backing array.
	 */
	public final boolean contains(final Object value) {
		boolean test = false;

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				test = true;
				break;
			}
		}

		return test;
	}

	/**
	 * Sets the collection size at 0, effectively removing all the elements from
	 * the collection.
	 */
	final void clear() {
		size = 0;
		for (int i = 0; i < size; i++) {
			elements[i] = null;
			size = 0;
		}
	}

	/**
	 * Doubles the backing array size.
	 */
	public final void increaseCapacity() {
		capacity *= 2;

		final Object[] temp = new Object[capacity];
		for (int i = 0; i < size; i++) {
			temp[i] = elements[i];
		}

		elements = temp;

	}
}
