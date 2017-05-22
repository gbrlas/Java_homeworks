package hr.fer.zemris.java.custom.scripting.tokens;

/**
 *Inherits Token and has a single read-only double property.
 * 
 * @author Goran Brlas
 *
 */
public class TokenConstantDouble extends Token {
	private double value;
	
	/**
	 * Constructor which sets the token value.
	 * @param value Double value to be set.
	 */
	public TokenConstantDouble (double value) {
		this.value = value;
	}
	
	/**
	 * Returns the token value.
	 */
	@Override
	public String asText () {
		return Double.toString(value);
	}
}
