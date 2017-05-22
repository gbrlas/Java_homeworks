package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Base class for all graph nodes.
 *
 * @author Goran Brlas
 *
 */
public abstract class Node {
	/**
	 * Collection used for storing nested nodes.
	 */
	private ArrayBackedIndexedCollection collection;
	/**
	 * Number of stored nodes.
	 */
	private int number = 0;

	/**
	 * Adds given child to an internally managed collection of children.
	 *
	 * @param child
	 *            Child to be added into the collection of children.
	 */
	public void addChildNode(final Node child) {
		if (number == 0) {
			collection = new ArrayBackedIndexedCollection();
		}

		collection.insert(child, number);
		number++;
	}

	/**
	 * Returns a number of direct children of this node.
	 *
	 * @return Number of children.
	 */
	public int numberOfChildren() {
		return number;
	}

	/**
	 * Returns selected child or throws an exception.
	 *
	 * @param index
	 *            Index of the looked for node.
	 * @return Node located at the given index.
	 * @throws IndexOutOfBoundsException
	 *             if provided index is invalid.
	 */
	public Node getChild(final int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= number) {
			throw new IndexOutOfBoundsException(
					"Index must be higher than 0 and lower than the number of children nodes.");
		}

		return (Node) collection.get(index);
	}

	/**
	 * Accepts a node visitor.
	 *
	 * @param visitor
	 *            Instance of a INodeVisitor implementation.
	 */
	public abstract void accept(INodeVisitor visitor);

}
