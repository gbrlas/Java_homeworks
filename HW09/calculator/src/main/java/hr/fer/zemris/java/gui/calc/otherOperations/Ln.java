package hr.fer.zemris.java.gui.calc.otherOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating ln(label number).
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Ln implements OtherOperation {
	
	@Override
	public double executeOperation(JLabel label) {
		return Math.log(Double.parseDouble(label.getText()));
	}
}
