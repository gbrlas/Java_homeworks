package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class which tests the layout manager.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Demo {
	
	/**
	 * Main method which starts the program.
	 * Takes no arguments.
	 * 
	 * @param args - none.
	 */
	public static void main(String[] args) {
		
		JFrame guiFrame = new JFrame();
		JPanel p = new JPanel(new CalcLayout(3));
		
		JLabel label = new JLabel("250", SwingConstants.RIGHT);
		label.setOpaque(true);
		label.setBackground(Color.yellow);
		
		p.add(label, new RCPosition(1, 1));
		p.add(new JButton("7"), new RCPosition(2, 3));
		p.add(new JButton("7"), new RCPosition(1, 6));
		p.add(new JButton("8"), new RCPosition(2, 4));
		p.add(new JButton("9"), new RCPosition(2, 5));
		p.add(new JButton("a"), new RCPosition(4, 5));
		p.add(new JButton("b"), new RCPosition(4, 7));
		
		guiFrame.add(p);
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setSize(355,280);
		
		guiFrame.setVisible(true);
		guiFrame.setLocationRelativeTo(null);
	}
}
