package hr.fer.zemris.java.gui.calc.trigonometricOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating the cos value of the label text.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Cos implements TrigonometricOperation {
	
	@Override
	public void executeOperation(JLabel label) {
		label.setText(Double.toString(Math.cos(Double.parseDouble(label.getText()))));
	}
}
