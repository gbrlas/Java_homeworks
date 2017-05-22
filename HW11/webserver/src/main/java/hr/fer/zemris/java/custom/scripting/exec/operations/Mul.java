package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which represents an multiplication operation. Pops two arguments from
 * the stack, does the required operation, and pushes the result back onto the
 * stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Mul implements Operations {

	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		double num1;
		double num2;

		try {
			num1 = (double) temporaryStack.peek().getValue();
		} catch (final Exception exception) {
			num1 = Double
					.parseDouble((String) temporaryStack.peek().getValue());
		}

		temporaryStack.pop();

		try {
			num2 = (double) temporaryStack.peek().getValue();
		} catch (final Exception exception) {
			num2 = Double
					.parseDouble((String) temporaryStack.peek().getValue());
		}

		temporaryStack.pop();

		final double result = num1 * num2;

		temporaryStack.push(new ValueWrapper(result));
	}
}
