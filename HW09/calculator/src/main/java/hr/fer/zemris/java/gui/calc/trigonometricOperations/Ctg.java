package hr.fer.zemris.java.gui.calc.trigonometricOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating the ctg value of the label text.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Ctg implements TrigonometricOperation {
	
	@Override
	public void executeOperation(JLabel label) {
		label.setText(Double.toString(1.0 / Math.tan(Double.parseDouble(label.getText()))));		
	}
}
