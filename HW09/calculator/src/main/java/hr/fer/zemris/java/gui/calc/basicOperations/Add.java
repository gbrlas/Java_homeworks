package hr.fer.zemris.java.gui.calc.basicOperations;

/**
 * Class representing a add operation.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Add implements BasicOperation {
	
	@Override
	public double executeOperation(double tempResult, double num) {
		tempResult += num;
		return tempResult;
	}
}
