package hr.fer.zemris.java.custom.collections;

/**
 * Exception thrown when stack is empty.
 *
 * @author Goran Brlas
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Empty constructor.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor with a single argument - message to be printed out.
	 *
	 * @param message
	 *            Message to be displayed.
	 */
	public EmptyStackException(final String message) {
		super(message);
	}

	/**
	 * Constructor with a single argument -cause.
	 *
	 * @param cause
	 *            Exception cause.
	 */
	public EmptyStackException(final Throwable cause) {
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
	public EmptyStackException(final String message, final Throwable cause) {
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
	 *            Enable suppression.
	 * @param writableStackTrace
	 *            WritableStackTrace.
	 */
	public EmptyStackException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
