package hr.fer.zemris.java.gui.calc.basicOperations;

/**
 * Class representing calculating n-th root of a number.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Root implements BasicOperation {
	
	@Override
	public double executeOperation(double tempResult, double num) {
		tempResult = Math.pow(tempResult, 1.0 / num);
		return tempResult;
	}
}
