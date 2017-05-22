package hr.fer.zemris.java.gui.calc.trigonometricOperations;

import javax.swing.JLabel;

/**
 * Class used for calculating the sin value of the label text.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Sin implements TrigonometricOperation {
	
	public void executeOperation(JLabel label) {
		label.setText(Double.toString(Math.sin(Double.parseDouble(label.getText()))));		
	};
}
