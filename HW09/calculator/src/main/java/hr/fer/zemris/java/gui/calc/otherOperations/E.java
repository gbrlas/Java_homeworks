package hr.fer.zemris.java.gui.calc.otherOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating e^(label number).
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class E implements OtherOperation {
	@Override
	public double executeOperation(JLabel label) {
		return Math.pow(Math.E, Double.parseDouble(label.getText()));
	}
}
