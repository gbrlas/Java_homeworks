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
	 * @param message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with a single argument - cause.
	 * @param cause
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor with two arguments - message and cause.
	 * @param message
	 * @param cause
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with multiple arguments.
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SmartScriptParserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
