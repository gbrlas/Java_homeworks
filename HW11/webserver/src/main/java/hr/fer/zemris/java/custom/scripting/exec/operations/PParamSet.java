package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which stores a value to therequestContext's persistent parameters map.
 * Pops two arguments from the stack, does the required operation, and pushes
 * the result back onto the stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class PParamSet implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper name = temporaryStack.pop();
		final ValueWrapper value = temporaryStack.pop();

		final String name2 = ((String) name.getValue()).replace("\"", "")
				.trim();
		rc.setPersistentParameter(name2, value.getValue().toString());
	}
}
