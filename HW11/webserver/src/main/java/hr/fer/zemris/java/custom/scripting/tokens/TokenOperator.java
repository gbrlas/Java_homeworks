package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Inherits Token and has a single read-only string property.
 *
 * @author Goran Brlas
 *
 */
public class TokenOperator extends Token {
	/**
	 * Token operator symbol.
	 */
	private final String symbol;

	/**
	 * Constructor which sets the token symbol value.
	 *
	 * @param symbol
	 *            String symbol to be set.
	 */
	public TokenOperator(final String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the token operator.
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
