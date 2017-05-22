package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Inherits Token and has a single read-only string property.
 * 
 * @author Goran Brlas
 *
 */
public class TokenFunction extends Token {
	private String name;
	
	/**
	 * Constructor which sets the token name.
	 * @param value String name to be set.
	 */
	public TokenFunction (String name) {
		this.name = name;
	}
	
	/**
	 * Returns the token function name.
	 */
	@Override
	public String asText() {
		return name;
	}
	
}
