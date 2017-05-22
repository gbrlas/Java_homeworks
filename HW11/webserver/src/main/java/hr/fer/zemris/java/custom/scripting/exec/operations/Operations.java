package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Interface which represents a script operation.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface Operations {
	/**
	 * Executes the needed operation. Uses a stack for dealing with parameters
	 * and a RequestContext for displaying data.
	 *
	 * @param temporaryStack
	 *            Stack used for taking/storing parameters.
	 * @param rc
	 *            RequestContext used for displaying data.
	 */
	void execute(Stack<ValueWrapper> temporaryStack, RequestContext rc);
}
