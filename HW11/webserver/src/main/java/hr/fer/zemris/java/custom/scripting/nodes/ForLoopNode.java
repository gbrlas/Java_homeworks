package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Node representing a single for-loop construct. Inherits from Node class.
 *
 * @author Goran Brlas
 *
 */
public class ForLoopNode extends Node {
	/**
	 * For loop variable.
	 */
	private TokenVariable variable;
	/**
	 * For loop start expression.
	 */
	private Token startExpression;
	/**
	 * For loop end expression.
	 */
	private Token endExpression;
	/**
	 * For loop step.
	 */
	private Token stepExpression = null;

	/**
	 * Constructor which sets read-only property variable and tokens.
	 *
	 * @param variable
	 *            For loop variable to be set.
	 * @param tokens
	 *            For loop tokens to be set.
	 */
	public ForLoopNode(final TokenVariable variable, final Token[] tokens) {
		if (tokens.length == 2) {
			this.variable = variable;
			startExpression = tokens[0];
			endExpression = tokens[1];
		} else {
			this.variable = variable;
			startExpression = tokens[0];
			endExpression = tokens[1];
			stepExpression = tokens[2];
		}
	}

	/**
	 * Returns string representation of the loop variable.
	 *
	 * @return Loop variable.
	 */
	public String getVariable() {
		return variable.asText();
	}

	/**
	 * Returns string representation of the loop start expression.
	 *
	 * @return Loop start expression.
	 */
	public String getStartExpression() {
		return startExpression.asText();
	}

	/**
	 * Returns string representation of the loop end expression.
	 *
	 * @return Loop end expression.
	 */
	public String getEndExpression() {
		return endExpression.asText();
	}

	/**
	 * Returns string representation of the loop step expression.
	 *
	 * @return Loop step expression.
	 */
	public String getStepExpression() {
		if (stepExpression == null) {
			return "";
		} else {
			return stepExpression.asText();
		}
	}

	@Override
	public void accept(final INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

}
