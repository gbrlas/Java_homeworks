package hr.fer.zemris.java.gui.calc.basicOperations;

/**
 * Class representing a multiplication operation.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Mul implements BasicOperation {
	
	@Override
	public double executeOperation(double tempResult, double num) {
		tempResult *= num;
		return tempResult;
	}
}
