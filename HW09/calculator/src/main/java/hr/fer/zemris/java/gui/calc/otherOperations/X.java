package hr.fer.zemris.java.gui.calc.otherOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating 1/(label number).
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class X implements OtherOperation {
	
	@Override
	public double executeOperation(JLabel label) {
		return 1.0 / Double.parseDouble(label.getText());
	}
}
