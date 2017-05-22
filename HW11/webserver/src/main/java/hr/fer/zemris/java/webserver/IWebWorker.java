package hr.fer.zemris.java.webserver;

/**
 * Interface towards any object that can process current request. It gets
 * RequestContext as parameter and it is expected to create a content for the
 * client.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface IWebWorker {
	/**
	 * Processes the current request according to the provided RequestContext.
	 *
	 * @param context
	 *            Request context.
	 */
	public void processRequest(RequestContext context);
}
