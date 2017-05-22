package hr.fer.zemris.java.custom.collections.demo;

import java.util.ArrayList;
import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Evaluates given expressions using previously defined collections.
 * 
 * @author Goran Brlas
 *
 */
public class StackDemo {
	public static ObjectStack stack = new ObjectStack();
	
	/**
	 * Evaluates the given expression.
	 * @param args Single command-line argument - an expression to be evaluated in postfix representation
	 */
	public static void main (String[] args) {
		int num1, num2;
		
		if (args.length != 1) {
			System.err.println("There must be one argument!");
			System.exit(-1);
		}
		
		ArrayList<String> ulaz = new ArrayList<String>(Arrays.asList(args[0].split("\\s+")));
		
		for (String znak : ulaz) {
			
			//if the operator is valid
			if (znak.equals("+")) {
				num1 = (Integer) stack.pop();
				num2 = (Integer) stack.pop();
				stack.push(num1+num2);
			} else if (znak.equals("-")) {
				num1 = (Integer) stack.pop();
				num2 = (Integer) stack.pop();
				stack.push(num2-num1);
			} else if (znak.equals("*")) {
				num1 = (Integer) stack.pop();
				num2 = (Integer) stack.pop();
				stack.push(num2 * num1);
			} else if (znak.equals("/")) {
				num1 = (Integer) stack.pop();
				num2 = (Integer) stack.pop();
				stack.push(num2 / num1);
			} else if (znak.equals("%")) {
				num1 = (Integer) stack.pop();
				num2 = (Integer) stack.pop();
				stack.push(num2 % num1);
			}
			
			//if the argument is a number
			else {
				try {
					num1 = Integer.parseInt(znak);
					stack.push(num1);
				} catch (NumberFormatException exception) {
					System.err.println("That is not a valid number or operator.");
					System.exit(-1);
				}
				
			}
		}
		
		if (stack.size() != 1) {
			System.err.println("There was an error!");
			System.exit(1);
		} else 
			System.out.println("Expression evaluates to " + stack.pop() + ".");
	}
}
