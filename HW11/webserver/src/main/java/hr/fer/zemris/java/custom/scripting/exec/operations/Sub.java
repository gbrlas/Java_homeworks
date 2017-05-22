package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which represents an substitution operation. Pops two arguments from the
 * stack, does the required operation, and pushes the result back onto the
 * stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Sub implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper num1 = temporaryStack.pop();
		final ValueWrapper num2 = temporaryStack.pop();

		num1.decrement(num2);

		temporaryStack.push(num1);
	}
}
