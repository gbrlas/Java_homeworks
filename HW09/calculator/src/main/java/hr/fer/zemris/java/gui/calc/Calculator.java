package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.basicOperations.Add;
import hr.fer.zemris.java.gui.calc.basicOperations.BasicOperation;
import hr.fer.zemris.java.gui.calc.basicOperations.Div;
import hr.fer.zemris.java.gui.calc.basicOperations.Mul;
import hr.fer.zemris.java.gui.calc.basicOperations.Pow;
import hr.fer.zemris.java.gui.calc.basicOperations.Root;
import hr.fer.zemris.java.gui.calc.basicOperations.Sub;
import hr.fer.zemris.java.gui.calc.otherOperations.E;
import hr.fer.zemris.java.gui.calc.otherOperations.Ln;
import hr.fer.zemris.java.gui.calc.otherOperations.Log;
import hr.fer.zemris.java.gui.calc.otherOperations.OtherOperation;
import hr.fer.zemris.java.gui.calc.otherOperations.Ten;
import hr.fer.zemris.java.gui.calc.otherOperations.X;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Acos;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Actg;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Asin;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Atan;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Cos;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Ctg;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Sin;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.Tan;
import hr.fer.zemris.java.gui.calc.trigonometricOperations.TrigonometricOperation;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Class representing a calculator which provides basic mathematical functions
 * and a simple stack implementation.
 * 
 * @author Goran Brlas
 * @version 1.0
 *
 */
public class Calculator {
	/**
	 * Label holding the current result or number entered.
	 */
	static JLabel label = new JLabel("", SwingConstants.RIGHT);
	/**
	 * Stored operation.
	 */
	static String operation = "";
	/**
	 * Temporary result.
	 */
	static double tempResult = 0;
	/**
	 * Used for determining whether the displayed result is correct.
	 */
	static int counter;
	/**
	 * Calculator stack.
	 */
	static Stack<Double> stack = new Stack<Double>();
	/**
	 * Integer representation of whether the calculator is in inverse state 1 -
	 * inverse 0 - not inverse
	 */
	static int inverse = 0;
	/**
	 * List holding all trigonometric operations.
	 */
	static Map<String, TrigonometricOperation> trigonometricOperations = new HashMap<String, TrigonometricOperation>();
	/**
	 * List holding all basic operations.
	 */
	static Map<String, BasicOperation> basicOperations = new HashMap<String, BasicOperation>();
	/**
	 * List holding all other operations.
	 */
	static Map<String, OtherOperation> otherOperations = new HashMap<String, OtherOperation>();
	/**
	 * List holding all changeable buttons(for using inverse).
	 */
	static List<JButton> changableButtons = new ArrayList<JButton>();

	/**
	 * Main method which starts the calculator. Takes no arguments.
	 * 
	 * @param args
	 *            - none.
	 */
	public static void main(String[] args) {

		putOperations();

		JFrame guiFrame = new JFrame();
		JPanel p = new JPanel(new CalcLayout(3));

		label.setOpaque(true);
		label.setBackground(Color.yellow);
		label.setFont(new Font("Serif", Font.PLAIN, 18));

		p.add(label, new RCPosition(1, 1));

		createNumberButtons(p);
		createBasicOps(p);
		createTrigOps(p);
		createOtherOps(p);

		guiFrame.add(p);

		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setSize(485, 370);

		guiFrame.setVisible(true);
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setMinimumSize(new Dimension(470, 360));
	}

	/**
	 * Private method which creates buttons used for other operations.
	 * 
	 * @param p
	 *            Frame panel.
	 */
	private static void createOtherOps(JPanel p) {

		ActionListener othersListener = e -> {
			JButton othersGumb = (JButton) e.getSource();
			computeOthers(othersGumb.getText());

			tempResult = 0;
			counter = 0;
			inverse = 0;
		};

		ActionListener powListener = e -> {
			if (label.getText().isEmpty()) {
				System.err.println("Label string is empty!");
				return;
			}
			tempResult = Double.parseDouble(label.getText());

			if (inverse == 0) {
				operation = "p";
			} else {
				operation = "r";
			}

			counter = 0;
		};

		JButton x = new JButton("1/x");
		x.addActionListener(othersListener);
		JButton log = new JButton("log");
		log.addActionListener(othersListener);
		JButton ln = new JButton("ln");
		ln.addActionListener(othersListener);
		JButton pow = new JButton("x^n");
		pow.addActionListener(powListener);

		p.add(x, new RCPosition(2, 1));
		p.add(ln, new RCPosition(4, 1));
		p.add(pow, new RCPosition(5, 1));
		p.add(log, new RCPosition(3, 1));

		changableButtons.add(log);
		changableButtons.add(ln);
		changableButtons.add(pow);
	}

	/**
	 * Private method which creates buttons used for trigonometric operations.
	 * 
	 * @param p
	 *            Frame panel.
	 */
	private static void createTrigOps(JPanel p) {

		ActionListener trigonometricListener = e -> {
			JButton trigonometricGumb = (JButton) e.getSource();
			computeTrigonometric(trigonometricGumb.getText());

			tempResult = 0;
			counter = 0;
			inverse = 0;
		};

		JButton sin = new JButton("sin");
		sin.addActionListener(trigonometricListener);
		JButton cos = new JButton("cos");
		cos.addActionListener(trigonometricListener);
		JButton tan = new JButton("tan");
		tan.addActionListener(trigonometricListener);
		JButton ctg = new JButton("ctg");
		ctg.addActionListener(trigonometricListener);

		p.add(sin, new RCPosition(2, 2));
		p.add(cos, new RCPosition(3, 2));
		p.add(tan, new RCPosition(4, 2));
		p.add(ctg, new RCPosition(5, 2));

		changableButtons.add(sin);
		changableButtons.add(cos);
		changableButtons.add(ctg);
		changableButtons.add(tan);
	}

	/**
	 * Private method which creates buttons used for basic operations.
	 * 
	 * @param p
	 *            Frame panel.
	 */
	private static void createBasicOps(JPanel p) {

		ActionListener operationListener = e -> {
			if (!operation.equals("")) {
				computeBasicOperation(label.getText());
			}
			JButton brojGumb = (JButton) e.getSource();
			operation = brojGumb.getText();
			if (label.getText().isEmpty()) {
				System.err.println("Label string is empty!");
				return;
			}
			tempResult = Double.parseDouble(label.getText());
			counter = 0;
		};

		ActionListener equalsListener = e -> {
			if (!operation.equals("")) {
				computeBasicOperation(label.getText());
			}

			if (tempResult == 0) {
				label.setText(Integer.toString(0));
			}
			tempResult = 0;
			counter = 0;
		};

		ActionListener stackListener = e -> {
			JButton stackGumb = (JButton) e.getSource();
			if (stackGumb.getText().equals("pop")) {
				if (!stack.isEmpty()) {
					label.setText(stack.pop().toString());
				}
			} else {
				if (label.getText().isEmpty()) {
					return;
				}
				stack.push((Double) Double.parseDouble(label.getText()));
			}
		};

		ActionListener clearListener = e -> {
			label.setText(Integer.toString(0));
		};

		ActionListener resetListener = e -> {
			operation = "";
			tempResult = 0;
			label.setText(Integer.toString(0));
			counter = 0;
			inverse = 0;
		};

		ActionListener inverseListener = e -> {
			inverse = inverse == 0 ? 1 : 0;
			changeButtonNames();
		};

		JButton equals = new JButton("=");
		equals.addActionListener(equalsListener);
		JButton plus = new JButton("+");
		plus.addActionListener(operationListener);
		JButton minus = new JButton("-");
		minus.addActionListener(operationListener);
		JButton mul = new JButton("*");
		mul.addActionListener(operationListener);
		JButton div = new JButton("/");
		div.addActionListener(operationListener);
		JButton clear = new JButton("clr");
		clear.addActionListener(clearListener);
		JButton reset = new JButton("res");
		reset.addActionListener(resetListener);
		JButton push = new JButton("push");
		push.addActionListener(stackListener);
		JButton pop = new JButton("pop");
		pop.addActionListener(stackListener);
		JCheckBox inv = new JCheckBox("inv");
		inv.addActionListener(inverseListener);

		p.add(equals, new RCPosition(1, 6));
		p.add(clear, new RCPosition(1, 7));
		p.add(div, new RCPosition(2, 6));
		p.add(reset, new RCPosition(2, 7));
		p.add(mul, new RCPosition(3, 6));
		p.add(push, new RCPosition(3, 7));
		p.add(minus, new RCPosition(4, 6));
		p.add(pop, new RCPosition(4, 7));
		p.add(plus, new RCPosition(5, 6));
		p.add(inv, new RCPosition(5, 7));
	}

	/**
	 * Private method which creates number buttons.
	 * 
	 * @param p
	 *            Frame panel.
	 */
	private static void createNumberButtons(JPanel p) {
		ActionListener numberListener = e -> {
			if (!operation.equals("") && counter == 0) {
				label.setText("");
				counter++;
			}

			JButton brojGumb = (JButton) e.getSource();
			String naziv = brojGumb.getText();

			if (label.getText().length() == 1
					&& label.getText().charAt(0) == '0') {
				label.setText(naziv);
			} else {
				label.setText(label.getText() + naziv);
			}
		};

		ActionListener numberTypeListener = e -> {
			JButton brojGumb = (JButton) e.getSource();
			String naziv = brojGumb.getText();

			if (naziv.equals("+/-")) {
				if (label.getText().isEmpty()
						|| Double.parseDouble(label.getText()) == 0) {
					return;
				}
				if (label.getText().charAt(0) == '-') {
					label.setText(label.getText().substring(1));
				} else {
					label.setText("-" + label.getText());
				}
			} else {
				label.setText(label.getText() + ".");
			}
		};

		JButton N7 = new JButton("7");
		N7.addActionListener(numberListener);
		JButton N8 = new JButton("8");
		N8.addActionListener(numberListener);
		JButton N9 = new JButton("9");
		N9.addActionListener(numberListener);
		JButton N4 = new JButton("4");
		N4.addActionListener(numberListener);
		JButton N5 = new JButton("5");
		N5.addActionListener(numberListener);
		JButton N6 = new JButton("6");
		N6.addActionListener(numberListener);
		JButton N1 = new JButton("1");
		N1.addActionListener(numberListener);
		JButton N2 = new JButton("2");
		N2.addActionListener(numberListener);
		JButton N3 = new JButton("3");
		N3.addActionListener(numberListener);
		JButton N0 = new JButton("0");
		N0.addActionListener(numberListener);

		JButton type = new JButton("+/-");
		type.addActionListener(numberTypeListener);
		JButton dot = new JButton(".");
		dot.addActionListener(numberTypeListener);

		p.add(N7, new RCPosition(2, 3));
		p.add(N8, new RCPosition(2, 4));
		p.add(N9, new RCPosition(2, 5));
		p.add(N4, new RCPosition(3, 3));
		p.add(N5, new RCPosition(3, 4));
		p.add(N6, new RCPosition(3, 5));
		p.add(N1, new RCPosition(4, 3));
		p.add(N2, new RCPosition(4, 4));
		p.add(N3, new RCPosition(4, 5));
		p.add(N0, new RCPosition(5, 3));
		p.add(type, new RCPosition(5, 4));
		p.add(dot, new RCPosition(5, 5));
	}

	/**
	 * Private method which puts all the operations in their respective list.
	 */
	private static void putOperations() {
		trigonometricOperations.put("sin", new Sin());
		trigonometricOperations.put("cos", new Cos());
		trigonometricOperations.put("tan", new Tan());
		trigonometricOperations.put("ctg", new Ctg());
		trigonometricOperations.put("asin", new Asin());
		trigonometricOperations.put("acos", new Acos());
		trigonometricOperations.put("atan", new Atan());
		trigonometricOperations.put("actg", new Actg());

		basicOperations.put("+", new Add());
		basicOperations.put("-", new Sub());
		basicOperations.put("*", new Mul());
		basicOperations.put("/", new Div());
		basicOperations.put("r", new Root());
		basicOperations.put("p", new Pow());

		otherOperations.put("1/x", new X());
		otherOperations.put("log", new Log());
		otherOperations.put("ln", new Ln());
		otherOperations.put("10^", new Ten());
		otherOperations.put("e^", new E());
	}

	/**
	 * Private method which changes the button names when the calculator state
	 * switches to inverse.
	 */
	private static void changeButtonNames() {
		if (inverse == 1) {
			for (int i = 0; i < 4; i++) {
				changableButtons.get(i).setText(
						"a" + changableButtons.get(i).getText());
			}
			changableButtons.get(4).setText("10^");
			changableButtons.get(5).setText("e^");
			changableButtons.get(6).setText("n\u221A");

		} else {
			for (int i = 0; i < 4; i++) {
				changableButtons.get(i).setText(
						changableButtons.get(i).getText().substring(1));
			}
			changableButtons.get(4).setText("log");
			changableButtons.get(5).setText("ln");
			changableButtons.get(6).setText("x^n");
		}
	}

	/**
	 * Private method which computes other operations.
	 * 
	 * @param name
	 *            Operation name.
	 */
	private static void computeOthers(String name) {
		if (label.getText().isEmpty()) {
			System.err.println("Label string is empty!");
			return;
		}
		OtherOperation op = otherOperations.get(name);
		double result = op.executeOperation(label);

		label.setText(Double.toString(result));

	}

	/**
	 * Private method which computes trigonometric operations.
	 * 
	 * @param name
	 *            Operation name.
	 */
	private static void computeTrigonometric(String name) {
		TrigonometricOperation op = trigonometricOperations.get(name);
		if (label.getText().isEmpty()) {
			System.err.println("Label string is empty!");
			return;
		}
		op.executeOperation(label);
	}

	/**
	 * Private method which computes basic operations.
	 * 
	 * @param text
	 *            Number to be processed.
	 */
	private static void computeBasicOperation(String text) {
		if (label.getText().isEmpty()) {
			System.err.println("Label string is empty!");
			return;
		}

		double broj = Double.parseDouble(text);

		BasicOperation op = basicOperations.get(operation);
		tempResult = op.executeOperation(tempResult, broj);

		operation = "";
		label.setText(Double.toString(tempResult));
	}
}
