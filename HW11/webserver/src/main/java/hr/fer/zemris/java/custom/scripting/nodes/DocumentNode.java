package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing an entire document. Inherits from Node class.
 *
 * @author Goran Brlas
 *
 */
public class DocumentNode extends Node {
	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
