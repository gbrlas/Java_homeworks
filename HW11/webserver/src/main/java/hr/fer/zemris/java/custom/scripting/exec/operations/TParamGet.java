package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which obtains from requestContext's temporary parameters map a value
 * mapped for name and pushes it onto stack. Pops two arguments from the stack,
 * does the required operation, and pushes the result back onto the stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class TParamGet implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper defaultValue = temporaryStack.pop();
		final ValueWrapper name = temporaryStack.pop();

		final String value = rc
				.getTemporaryParameter(((String) name.getValue()).replace("\"",
						"").trim());

		temporaryStack.push(value == null ? defaultValue : new ValueWrapper(
				value));
	}
}