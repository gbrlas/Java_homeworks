package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Class which formats an decimal number using given format f which is
 * compatible with DecimalFormat. Pops two arguments from the stack, does the
 * required operation, and pushes the result back onto the stack.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Decfmt implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final String format = (String) temporaryStack.pop().getValue();
		final DecimalFormat df = new DecimalFormat(format);

		double num1;
		try {
			num1 = (double) temporaryStack.peek().getValue();
		} catch (final Exception exception) {
			num1 = Double
					.parseDouble((String) temporaryStack.peek().getValue());
		}

		temporaryStack.pop();

		String result = df.format(num1);
		result = result.replace("\"", "");

		temporaryStack.push(new ValueWrapper(result));
	}
}
