package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Class which starts the program and displays the bar chart whose values are
 * provided via a file.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class BarChartDemo extends JFrame {
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * File name.
	 */
	static String name;
	/**
	 * Bar chart components used for displaying the bar chart.
	 */
	static BarChartComponent component;

	/**
	 * Constructor which sets the frame size, location and default close
	 * operation.
	 */
	public BarChartDemo() {
		// setLocationRelativeTo(null);
		setSize(500, 500);
		setMinimumSize(component.getMinimumSize());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initGUI();
	}

	/**
	 * Method which initializes the graphical interface and adds the components
	 * to the content pane.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		JLabel label = new JLabel(name);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(label, BorderLayout.PAGE_START);
		getContentPane().add(component, BorderLayout.CENTER);
	}

	/**
	 * Main method which starts the program. Takes one argument - a path to the
	 * file holding the bar chart description.
	 * 
	 * @param args
	 *            - path to the file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid argument provided.");
			return;
		}

		String xDesc;
		String yDesc;
		int yMin;
		int yMax;
		int spacing;
		XYValue[] values;

		String fileName = args[0].trim().replace("\"", "");
		name = fileName;
		try {
			String[] lines = Files.readAllLines(Paths.get(fileName),
					StandardCharsets.UTF_8).toArray(new String[0]);

			xDesc = lines[0];
			yDesc = lines[1];

			String[] valuesStr = lines[2].split(" ");
			values = new XYValue[valuesStr.length];

			int i = 0;
			for (String value : valuesStr) {
				String[] temp = value.split(",");
				values[i++] = new XYValue(Integer.parseInt(temp[0]),
						Integer.parseInt(temp[1]));
			}

			yMin = Integer.parseInt(lines[3]);
			yMax = Integer.parseInt(lines[4]);
			spacing = Integer.parseInt(lines[5]);

			BarChart model = new BarChart(Arrays.asList(values), xDesc, yDesc,
					yMin, yMax, spacing);
			component = new BarChartComponent(model);
			component.setMinimumSize(new Dimension(300, 300));

		} catch (Exception e) {
			System.err.println("Error while rading from file.");
			;
		}

		SwingUtilities.invokeLater(() -> {
			JFrame frame = new BarChartDemo();

			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
		});

	}
}
