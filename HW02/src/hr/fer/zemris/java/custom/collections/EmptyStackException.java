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
	 * @param message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with a single argument -cause.
	 * @param cause
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor with two arguments - message and cause.
	 * @param message
	 * @param cause
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructor with multiple arguments.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public EmptyStackException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
