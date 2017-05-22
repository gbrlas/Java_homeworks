package hr.fer.zemris.java.custom.scripting.exec.operations;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Stack;

/**
 * Class which takes a string from the stack and sets the mime time in the
 * request context. Does not produce any result.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class SetMimeType implements Operations {
	@Override
	public void execute(final Stack<ValueWrapper> temporaryStack,
			final RequestContext rc) {
		final ValueWrapper wrapper = temporaryStack.pop();
		final String str = ((String) wrapper.getValue()).replace("\"", "")
				.trim();
		rc.setMimeType(str);
	}
}
