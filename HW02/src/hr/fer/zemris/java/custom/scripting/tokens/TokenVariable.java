package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Inherits Token and has a single read-only string.
 * 
 * @author Goran Brlas
 *
 */
public class TokenVariable extends Token {
	private String name;
	
	/**
	 * Constructor which sets the token variable name.
	 * @param value String value to be set.
	 */
	public TokenVariable (String name) {
		this.name = name;
	}
	
	/**
	 * Returns the token name.
	 */
	@Override
	public String asText() {
		return name;
	}

}
