package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which removes association for name from requestContext's persistent
 * parameters map. Pops two arguments from the stack, does the required
 * operation, and pushes the result back onto the stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class PParamDel implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper name = temporaryStack.pop();

		rc.removePersistentParameter((String) name.getValue());
	}
}
