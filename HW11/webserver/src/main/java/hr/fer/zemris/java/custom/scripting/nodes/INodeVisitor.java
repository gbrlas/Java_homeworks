package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface which represents a node visitor.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface INodeVisitor {
	/**
	 * Method to be executed when called by a TextNode.
	 *
	 * @param node
	 *            Node calling the visitor.
	 */
	void visitTextNode(TextNode node);

	/**
	 * Method to be executed when called by a ForLoopNode.
	 *
	 * @param node
	 *            Node calling the visitor.
	 */
	void visitForLoopNode(ForLoopNode node);

	/**
	 * Method to be executed when called by a EchoNode.
	 *
	 * @param node
	 *            Node calling the visitor.
	 */
	void visitEchoNode(EchoNode node);

	/**
	 * Method to be executed when called by a DocumentNode.
	 *
	 * @param node
	 *            Node calling the visitor.
	 */
	void visitDocumentNode(DocumentNode node);
}
