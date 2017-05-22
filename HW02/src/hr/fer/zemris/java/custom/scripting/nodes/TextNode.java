package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing a piece of textual data. Inherits from Node class.
 * 
 * @author Goran Brlas
 *
 */
public class TextNode extends Node {
	private String text;
	
	/**
	 * Constructor which sets the text value to string which is to be represented by this node.
	 * @param text String to be represented.
	 */
	public TextNode (String text) {
		this.text = text;
	}
	
	/**
	 * Returns string represented by this node.
	 * @return Node string.
	 */
	public String asText() {
		return text;
	}
	
	
}
