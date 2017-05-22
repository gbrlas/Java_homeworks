package hr.fer.zemris.java.gui.calc.basicOperations;

/**
 * Class representing a division operation.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Div implements BasicOperation {
	
	@Override
	public double executeOperation(double tempResult, double num) {
		tempResult /= num;
		return tempResult;
	}
}
