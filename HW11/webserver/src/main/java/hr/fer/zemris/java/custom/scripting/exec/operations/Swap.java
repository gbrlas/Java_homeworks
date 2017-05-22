package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which replaces the order of two topmost items on the stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Swap implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper x = temporaryStack.pop();
		final ValueWrapper y = temporaryStack.pop();

		temporaryStack.push(x);
		temporaryStack.push(y);
	}
}
