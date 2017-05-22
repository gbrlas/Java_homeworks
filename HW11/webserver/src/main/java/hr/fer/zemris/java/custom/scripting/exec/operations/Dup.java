package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which duplicates current top value from stack. Pops the top value and
 * then pushes it back on the stack twice.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Dup implements Operations {

	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper x = temporaryStack.pop();
		temporaryStack.push(x);
		temporaryStack.push(x);
	}
}
