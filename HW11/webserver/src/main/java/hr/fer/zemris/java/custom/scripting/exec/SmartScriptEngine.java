package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.exec.operations.Add;
import hr.fer.zemris.java.custom.scripting.exec.operations.Decfmt;
import hr.fer.zemris.java.custom.scripting.exec.operations.Div;
import hr.fer.zemris.java.custom.scripting.exec.operations.Dup;
import hr.fer.zemris.java.custom.scripting.exec.operations.Mul;
import hr.fer.zemris.java.custom.scripting.exec.operations.Operations;
import hr.fer.zemris.java.custom.scripting.exec.operations.PParamDel;
import hr.fer.zemris.java.custom.scripting.exec.operations.PParamGet;
import hr.fer.zemris.java.custom.scripting.exec.operations.PParamSet;
import hr.fer.zemris.java.custom.scripting.exec.operations.ParamGet;
import hr.fer.zemris.java.custom.scripting.exec.operations.SetMimeType;
import hr.fer.zemris.java.custom.scripting.exec.operations.Sin;
import hr.fer.zemris.java.custom.scripting.exec.operations.Sub;
import hr.fer.zemris.java.custom.scripting.exec.operations.Swap;
import hr.fer.zemris.java.custom.scripting.exec.operations.TParamDel;
import hr.fer.zemris.java.custom.scripting.exec.operations.TParamGet;
import hr.fer.zemris.java.custom.scripting.exec.operations.TParamSet;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Class which executes the document whose parsed tree it obtains.
 *
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class SmartScriptEngine {
	/**
	 * Document node created after parsing the document.
	 */
	private final DocumentNode documentNode;
	/**
	 * Request context used for displaying needed data.
	 */
	private final RequestContext requestContext;
	/**
	 * Multistack used for storing node values respectively.
	 */
	private final ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Map containing all available script operations.
	 */
	private final Map<String, Operations> operations = new HashMap<String, Operations>();

	/**
	 * Node visitor which visits all the nodes contained in the document node
	 * and executes needed operation.
	 */
	private final INodeVisitor visitor = new INodeVisitor() {
		@Override
		public void visitDocumentNode(final DocumentNode node) {
			if (node.numberOfChildren() == 0) {
				System.out.println("");
				return;
			}

			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}

		@Override
		public void visitEchoNode(final EchoNode node) {
			final Stack<ValueWrapper> temporaryStack = new Stack<ValueWrapper>();

			for (final Token token : node.getTokens()) {
				if (token instanceof TokenConstantDouble
						|| token instanceof TokenConstantInteger
						|| token instanceof TokenString) {
					temporaryStack.push(new ValueWrapper(token.asText()));
				} else if (token instanceof TokenVariable) {
					temporaryStack.push(new ValueWrapper(multistack.peek(
							token.asText()).getValue()));
				} else if (token instanceof TokenOperator
						|| token instanceof TokenFunction) {
					final Operations op = operations.get(token.asText());
					op.execute(temporaryStack, requestContext);
				}
			}

			final Stack<ValueWrapper> temp2 = new Stack<ValueWrapper>();
			while (!temporaryStack.isEmpty()) {
				temp2.push(temporaryStack.pop());
			}

			while (!temp2.isEmpty()) {
				final ValueWrapper vWrapper = temp2.pop();

				try {
					if ((vWrapper.getValue()) instanceof String) {
						// String valueString = (String) vWrapper.getValue();
						requestContext.write((String) (vWrapper.getValue()));
					} else if ((vWrapper.getValue()) instanceof Double) {
						final double value = (Double) (vWrapper.getValue());
						final String text = Double.toString(value);
						requestContext.write(text);
					} else {
						final Integer value = (Integer) (vWrapper.getValue());
						final String text = Integer.toString(value);
						requestContext.write(text);
					}
				} catch (final IOException exception) {
					System.err
							.println("Error while writing echo node elements.");
				}
			}

		}

		@Override
		public void visitForLoopNode(final ForLoopNode node) {
			final double start = Double.parseDouble(node.getStartExpression());
			multistack.push(node.getVariable(), new ValueWrapper(start));

			for (; (double) multistack.peek(node.getVariable()).getValue() <= Double
					.parseDouble(node.getEndExpression()); multistack.peek(
					node.getVariable()).increment(
					Double.parseDouble(node.getStepExpression()))) {

				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
			}

			multistack.pop(node.getVariable());
		}

		@Override
		public void visitTextNode(final TextNode node) {
			if (node.asText().length() == 0) {
				return;
			}
			try {
				requestContext.write(node.asText());
			} catch (final IOException e) {
				System.err.println("Error while printing TextNode.");
				System.exit(-1);
			}
		}
	};

	/**
	 * Constructor which sets the engine parameters to the provided ones.
	 *
	 * @param documentNode
	 *            Document node created after parsing the document.
	 * @param requestContext
	 *            Request context used for displaying needed data.
	 */
	public SmartScriptEngine(final DocumentNode documentNode,
			final RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;

		addOperations();
	}

	/**
	 * Adds all available operations to a map.
	 */
	private void addOperations() {
		operations.put("+", new Add());
		operations.put("@decfmt", new Decfmt());
		operations.put("/", new Div());
		operations.put("@dup", new Dup());
		operations.put("*", new Mul());
		operations.put("@paramGet", new ParamGet());
		operations.put("@pparamDel", new PParamDel());
		operations.put("@pparamGet", new PParamGet());
		operations.put("@pparamSet", new PParamSet());
		operations.put("@setMimeType", new SetMimeType());
		operations.put("@sin", new Sin());
		operations.put("-", new Sub());
		operations.put("@swap", new Swap());
		operations.put("@tparamDel", new TParamDel());
		operations.put("@tparamGet", new TParamGet());
		operations.put("@tparamSet", new TParamSet());
	}

	/**
	 * Starts the engine by sending a visitor to the document node created after
	 * parsing the document.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
