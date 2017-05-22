package hr.fer.zemris.java.gui.calc.trigonometricOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating the arc tan value of the label text.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Atan implements TrigonometricOperation {
	
	@Override
	public void executeOperation(JLabel label) {
		label.setText(Double.toString(Math.atan(Double.parseDouble(label.getText()))));
	}
}
