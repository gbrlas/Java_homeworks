package hr.fer.zemris.java.gui.calc.otherOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating log(label number).
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Log implements OtherOperation {
	
	@Override
	public double executeOperation(JLabel label) {
		return Math.log10(Double.parseDouble(label.getText()));
	}
}
