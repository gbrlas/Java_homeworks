package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Class which starts the program and displays the frame.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class PrimDemo {

	/**
	 * Main method which starts the program. Takes no arguments.
	 * 
	 * @param args
	 *            - none.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

		List<Integer> primList = new ArrayList<Integer>();
		primList.add(1);

		PrimListModel model = new PrimListModel(primList);

		JPanel panel = new JPanel(new GridLayout(1, 2));
		JButton button = new JButton("Sljedeći");

		JList<Integer> list1 = new JList<Integer>(model);
		JList<Integer> list2 = new JList<Integer>(model);
		JScrollPane scrollPane = new JScrollPane();
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane.setViewportView(list1);
		scrollPane2.setViewportView(list2);

		ActionListener nextListener = e -> {
			model.next();
		};

		button.addActionListener(nextListener);

		panel.add(scrollPane);
		panel.add(scrollPane2);

		frame.add(panel, BorderLayout.CENTER);
		frame.add(button, BorderLayout.PAGE_END);

		frame.setSize(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
