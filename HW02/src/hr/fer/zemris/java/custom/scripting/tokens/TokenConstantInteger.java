package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Inherits Token and has a single read only integer property.
 * 
 * @author Goran Brlas
 *
 */
public class TokenConstantInteger extends Token {
	private int value;
	
	/**
	 * Constructor which sets the token value.
	 * @param value Integer value to be set.
	 */
	public TokenConstantInteger (int value) {
		this.value = value;
	}
	
	/**
	 * Returns the token value.
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
