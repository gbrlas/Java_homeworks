package hr.fer.zemris.java.gui.calc.otherOperations;

import javax.swing.JLabel;

/**
 * Interface representing other mathematical functions provided by the calculator.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public interface OtherOperation {
	/**
	 * Executes the needed operation and changes the label text.
	 * 
	 * @param label Label holding the result.
	 */
	public double executeOperation(JLabel label);
}
