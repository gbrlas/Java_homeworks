package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Inherits Token and has a single read-only string property.
 *
 * @author Goran Brlas
 *
 */
public class TokenString extends Token {
	/**
	 * Token string value.
	 */
	private final String value;

	/**
	 * Constructor which sets the token string value.
	 *
	 * @param value
	 *            String value to be set.
	 */
	public TokenString(final String value) {
		this.value = value;
	}

	/**
	 * Returns the token value.
	 */
	@Override
	public String asText() {
		return value;
	}
}
