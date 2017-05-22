package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Node representing a command which generates some textual output dynamically. Inherits from Node class.
 * 
 * @author Goran Brlas
 *
 */
public class EchoNode extends Node {
	private Token[] tokens;
	
	/**
	 * Constructor which sets read-only property tokens. 
	 * @param tokens Array of tokens.
	 */
	public EchoNode (Token[] tokens) {
		this.tokens = tokens;
	}
	
	/**
	 * Return array of tokens of this node.
	 * @return Array of tokens.
	 */
	public Token[] getTokens() {
		return tokens;
	}
}
