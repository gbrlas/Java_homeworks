package hr.fer.zemris.java.gui.calc.trigonometricOperations;

import javax.swing.JLabel;

/**
 * Interface representing basic trigonometric functions.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface TrigonometricOperation {
	
	/**
	 * Calculates the result and sets it as a label text.
	 * 
	 * @param label Label which holds the result.
	 * @param tempResult Current temporary result.
	 */
	public void executeOperation(JLabel label);

}
