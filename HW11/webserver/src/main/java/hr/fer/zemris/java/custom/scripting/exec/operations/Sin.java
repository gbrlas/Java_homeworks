package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which calculates the sinus from given arugument and stores the result
 * back to the stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Sin implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		double num;
		try {
			num = (double) temporaryStack.peek().getValue();
		} catch (final Exception exception) {
			num = Double.parseDouble((String) temporaryStack.peek().getValue());
		}

		temporaryStack.pop();

		final double numTemp = Math.toRadians(num);

		final double result = Math.sin(numTemp);

		temporaryStack.push(new ValueWrapper(result));
	}
}
