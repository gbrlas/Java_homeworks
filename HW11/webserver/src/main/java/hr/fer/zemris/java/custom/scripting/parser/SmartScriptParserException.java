package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception thrown when an error occurs while parsing data.
 *
 * @author Goran Brlas
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructor with a single argument - message to be printed out.
	 *
	 * @param message
	 *            Message to be printed out.
	 */
	public SmartScriptParserException(final String message) {
		super(message);
	}

	/**
	 * Constructor with a single argument - cause.
	 *
	 * @param cause
	 *            Exception cause.
	 */
	public SmartScriptParserException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with two arguments - message and cause.
	 *
	 * @param message
	 *            Message to be printed out.
	 * @param cause
	 *            Exception cause.
	 */
	public SmartScriptParserException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with multiple arguments.
	 *
	 * @param message
	 *            Message to be printed out.
	 * @param cause
	 *            Exception cause.
	 * @param enableSuppression
	 *            Enable supression.
	 * @param writableStackTrace
	 *            Write stack trace.
	 */
	public SmartScriptParserException(final String message,
			final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
