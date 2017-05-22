package hr.fer.zemris.java.gui.calc.basicOperations;

/**
 * Class representing calculating the n-th power of a number.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Pow implements BasicOperation {
	 @Override
	public double executeOperation(double tempResult, double num) {
		return Math.pow(tempResult, num);
	}
}	

