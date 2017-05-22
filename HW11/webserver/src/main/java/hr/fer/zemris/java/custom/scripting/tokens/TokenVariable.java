package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Inherits Token and has a single read-only string.
 *
 * @author Goran Brlas
 *
 */
public class TokenVariable extends Token {
	/**
	 * Token variable name.
	 */
	private final String name;

	/**
	 * Constructor which sets the token variable name.
	 *
	 * @param name
	 *            String value to be set.
	 */
	public TokenVariable(final String name) {
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
