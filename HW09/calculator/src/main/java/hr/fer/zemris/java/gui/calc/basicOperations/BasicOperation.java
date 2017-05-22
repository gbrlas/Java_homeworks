package hr.fer.zemris.java.gui.calc.basicOperations;

/**
 * Interface representing basic mathematical functions.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface BasicOperation {
	/**
	 * Executes the needed operation on the provided parameters.
	 * 
	 * @param tempResult Temporary result.
	 * @param num Number used in operation execution.
	 * @return Result of the operation.
	 */
	public double executeOperation(double tempResult, double num);
}
